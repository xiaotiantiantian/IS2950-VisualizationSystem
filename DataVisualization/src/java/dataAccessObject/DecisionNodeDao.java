/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessObject;

import DbConnect.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import model.DecisionNode;
import model.UserEventBean;

/**
 *
 * @author Tian Zhirun
 */
public class DecisionNodeDao {

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    //Load and parse XML file into DOM 
    String XMLFilePath;
    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public DecisionNodeDao() throws SQLException {
        try {
            //connect to database and select the record
            connection = DbConnection.getConnection();
            if (connection.isClosed()) {
                System.out.println("==connection closed exception==");
            }
            System.out.println("==DecisionNodeDao connection==");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //logID for calculate yourTime, so we must have it
    public int InsertEventIntoDecisionNodeDB(int logID) throws SQLException {
        //first delete all the old data(all entities in decision_node table)
        this.DeleteAllNodes();
        //then put all the decisions without parents into the decision_node table
        List<DecisionNode> decisionList = new ArrayList<DecisionNode>(); //store all event with parent information

        List<Integer> logIDs = new ArrayList<Integer>();
        logIDs.add(logID);
        //put the root node into decision_node table
        DecisionNode decisionNode = new DecisionNode(0, "root", 0, -1, 0, 0, 0, 0, logIDs);
        this.InsertDecisionNodeIntoDB(decisionNode);

        //put all the decisions into db
        //construct the decision from event first
        UserEventDao userEventDao = new UserEventDao();
        List<UserEventBean> eventListOrig = userEventDao.GetAllEvents();
        for (UserEventBean userEventBean : eventListOrig) {
            List<Integer> userIDs = new ArrayList<Integer>();
            userIDs.add(userEventBean.getLogID());
            int yourTime;
            if (logID == userEventBean.getLogID()) {
                yourTime = userEventBean.getDecisionTimeDelta();
            } else {
                yourTime = -1;  //-1 means that element has no "your time"
            }

            DecisionTimeDao decisionTimeDao = new DecisionTimeDao();
            DecisionNode decisionNodeTmp = new DecisionNode(
                    userEventBean,
                    -2,
                    decisionTimeDao.getAvgTime(userEventBean.getSequenceNum()),
                    decisionTimeDao.getExpertAvgTime(userEventBean.getSequenceNum()),
                    decisionTimeDao.getCohortAvgTime(userEventBean.getSequenceNum(), logID),
                    yourTime,
                    userIDs
            );
            
            //if the seq=1, it sould assign the parent id 0(the root node in seq0 has event id 0)
            if(decisionNodeTmp.getSequenceNum()==1)
                decisionNodeTmp.setParentID(0);
            this.InsertDecisionNodeIntoDB(decisionNodeTmp);
        }

        return 0;
    }

    public List<DecisionNode> GetDecisionBySequenceNum(int sequenceNum) {
        List<DecisionNode> decisionList = new ArrayList<DecisionNode>();
        try {
            String sql = "SELECT eventID, decisionName, sequenceNum, parentID, avgTime, expertTime, cohortAvgTime, yourTime, userID from simmandebrief.decision_nodes where sequenceNum = ? ";
              PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            rs = ps.executeQuery();
//rs = ps.executeUpdate();
            while (rs.next()) {
                List<Integer> userIDs = new ArrayList<Integer>();
                userIDs.add(rs.getInt("userID"));

                DecisionNode decisionNode = new DecisionNode(
                        rs.getInt("eventID"),
                        rs.getString("decisionName"),
                        rs.getInt("sequenceNum"),
                        rs.getInt("parentID"),
                        rs.getInt("avgTime"),
                        rs.getInt("expertTime"),
                        rs.getInt("cohortAvgTime"),
                        rs.getInt("yourTime"),
                        userIDs
                );
                decisionList.add(decisionNode);

            }
            return decisionList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

    }

    public List<DecisionNode> CombineDecisionNodeWithSameDecisionNameAndSameParentID(List<DecisionNode> decisionList) {
        //if two decisionNode have same decisionName and parentID, combine it. change the eventID to the smaller one
        List<DecisionNode> decisionListNew = new ArrayList<DecisionNode>();
        for (DecisionNode decisionNode : decisionList) {
            //if the old list has decision item in new list with same name and parentid, just combine it
            //if not, put it into new list
            int labelHas = 0;   //label means new list already has the decision(1) or not (0)
            for (DecisionNode decisionNodeNew : decisionListNew) {
                if (decisionNodeNew.getDecisionName().equals(decisionNode.getDecisionName()) && decisionNodeNew.getParentID() == decisionNode.getParentID()) {
                    labelHas = 1;
                    List<Integer> userIDs = decisionNode.getUserIDs();
                    userIDs.addAll(decisionNodeNew.getUserIDs());
                    decisionNodeNew.setUserIDs(userIDs);
                    break;
                }
            }
            if (labelHas == 0) {
                decisionListNew.add(decisionNode);

            }

        }
        return decisionListNew;
    }

    public List<DecisionNode> GetDecisionWithAssignedParents(int logID) throws SQLException {
        List<DecisionNode> resultList = new ArrayList<DecisionNode>();
        //0. put the root node into resultList
        resultList.addAll(this.GetDecisionBySequenceNum(0));
        //0.1 assign the parents to each event in the decision_node table
        UserEventDao userEventDao = new UserEventDao();
        //then iterate from sequence 1 to final
        int maxSequence = userEventDao.GetMaxSequenceNumByLogID(logID);

        for (int i = 1; i < maxSequence; i++) {  //seq start from 1,seq 0 is root node, seq maxSequence should be exacted individually
            //1 get the parent list
            List<DecisionNode> decisionList = this.GetDecisionBySequenceNum(i);
            //2 combine parent list
            decisionList = this.CombineDecisionNodeWithSameDecisionNameAndSameParentID(decisionList);
            //2.1 insert the combined parent list into resultList
            resultList.addAll(decisionList);

            //3 give seq i+1 a parentID
            List<DecisionNode> decisionListNext = this.GetDecisionBySequenceNum(i + 1);
            //iterate all decision in seq i+1
            for (DecisionNode decisionNodeNext : decisionListNext) {
                //iterate all decision in seq i
                for (DecisionNode decisionNode : decisionList) {
                    //iterate all userID in decisionNode(of seq i)
                    List<Integer> userIDs = decisionNode.getUserIDs();
                    for (Integer userID : userIDs) {
                        //if one decision of seq i+1 has a userid in the userID list one decision of seq i
                        if (decisionNodeNext.getUserIDs().contains(userID)) {
                            decisionNodeNext.setParentID(decisionNode.getEventID());
                        }
                    }
                }

            }
            //4 update the seq i+1 decisions back to database with assigned parentID
            //it seems this situation the decision only need to update the parentID column
            this.UpdateDecisionNodeBySequenceNum(i + 1, decisionListNext);
        }
        //for the loop, we do not combine the last sequence
        List<DecisionNode> decisionList = this.GetDecisionBySequenceNum(maxSequence);
        decisionList = this.CombineDecisionNodeWithSameDecisionNameAndSameParentID(decisionList);
        resultList.addAll(decisionList);

        return resultList;
    }

    public int UpdateDecisionNodeBySequenceNum(int sequenceNum, List<DecisionNode> decisionNodeList) {
        //I delete first then insert because there may has some situation the number of rows would change.
        try {
            String sql0 = "DELETE FROM simmandebrief.decision_nodes WHERE sequenceNum = ? ";
            ps = connection.prepareStatement(sql0);
            ps.setInt(1, sequenceNum);
            ps.executeUpdate();
            for (DecisionNode decisionNode : decisionNodeList) {
                sql = "INSERT INTO simmandebrief.decision_nodes(eventID, decisionName, sequenceNum, parentID, avgTime, expertTime, cohortAvgTime, yourTime, userID ) values (?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, decisionNode.getEventID());
                ps.setString(2, decisionNode.getDecisionName());
                ps.setInt(3, decisionNode.getSequenceNum());
                ps.setInt(4, decisionNode.getParentID());
                ps.setInt(5, decisionNode.getAvgTime());
                ps.setInt(6, decisionNode.getExpertTime());
                ps.setInt(7, decisionNode.getCohortAvgTime());
                ps.setInt(8, decisionNode.getYourTime());
                ps.setInt(9, decisionNode.getUserIDs().get(0));
                ps.executeUpdate();
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

    public int InsertDecisionNodeIntoDB(DecisionNode decisionNode) {
        try {
            String sql = "INSERT INTO simmandebrief.decision_nodes(eventID, decisionName, sequenceNum, parentID, avgTime, expertTime, cohortAvgTime, yourTime, userID ) values (?,?,?,?,?,?,?,?,?)";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, decisionNode.getEventID());
            ps.setString(2, decisionNode.getDecisionName());
            ps.setInt(3, decisionNode.getSequenceNum());
            ps.setInt(4, decisionNode.getParentID());
            ps.setInt(5, decisionNode.getAvgTime());
            ps.setInt(6, decisionNode.getExpertTime());
            ps.setInt(7, decisionNode.getCohortAvgTime());
            ps.setInt(8, decisionNode.getYourTime());
            ps.setInt(9, decisionNode.getUserIDs().get(0));
            ps.executeUpdate();

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

    public int DeleteNodeBySequenceNum(int sequenceNum) {
        try {
            String sql = "DELETE FROM simmandebrief.decision_nodes WHERE sequenceNum =?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);

            ps.executeUpdate();

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

    public int DeleteAllNodes() {
        try {
            String sql = "DELETE FROM simmandebrief.decision_nodes";

            ps = connection.prepareStatement(sql);
            ps.executeUpdate();

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

}
