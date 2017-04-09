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
import model.UserEventBean;

/**
 *
 * @author Tian Zhirun
 */
public class UserEventDao {

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

    public UserEventDao() throws SQLException {
        try {

            //connect to database and select the record
            connection = DbConnection.getConnection();
            if (connection.isClosed()) {
                System.out.println("==connection closed exception==");
            }
            System.out.println("==UserEventDao connection==");
            
            //combine all the eventtype data in same time and made by same logID
            this.CombineEventInSameTime();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public int InsertEventIntoDB(UserEventBean userEventBean, int sequenceNum, int decisionTimeDelta) throws SQLException {
        try {
            String sql = "INSERT INTO simmandebrief.userevent2(logID, msec, level, paramID, paramType, paramValue, sequenceNum, decisionTimeDelta ) values (?,?,?,?,?,?,?,?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userEventBean.getLogID());
            ps.setInt(2, userEventBean.getMsec());
            ps.setInt(3, userEventBean.getLevel());
            ps.setString(4, userEventBean.getParamID());
            ps.setString(5, userEventBean.getParamType());
            ps.setString(6, userEventBean.getParamValue());
            ps.setInt(7, sequenceNum);
            ps.setInt(8, decisionTimeDelta);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return autoKey = rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int InsertEventIntoDB(UserEventBean userEventBean) throws SQLException {
        try {
            String sql = "INSERT INTO simmandebrief.userevent2(logID, msec, level, paramID, paramType, paramValue, sequenceNum, decisionTimeDelta ) values (?,?,?,?,?,?,?,?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userEventBean.getLogID());
            ps.setInt(2, userEventBean.getMsec());
            ps.setInt(3, userEventBean.getLevel());
            ps.setString(4, userEventBean.getParamID());
            ps.setString(5, userEventBean.getParamType());
            ps.setString(6, userEventBean.getParamValue());
            ps.setInt(7, userEventBean.getSequenceNum());
            ps.setInt(8, userEventBean.getDecisionTimeDelta());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return autoKey = rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public List<UserEventBean> ReadEventByLogID(int logID) {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta from simmandebrief.userevent2 where logID = ? and paramType = 'EventType'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logID);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(logID);
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<UserEventBean> ReadEventBySeqNum(int sequenceNum) {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, logID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta from simmandebrief.userevent2 where sequenceNum = ? and paramType = 'EventType'";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, sequenceNum);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(rs.getInt("logID"));
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<UserEventBean> ReadEventByLogIDAndSeqNum(int logID, int sequenceNum) {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta from simmandebrief.userevent2 where logID = ? and sequenceNum = ? and paramType = 'EventType'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logID);
            ps.setInt(2, sequenceNum);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(logID);
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    //get certain decision with same name and same sequence number and same paramID(decision is event type)
    //input: logID is the current user's log, sequenceNum is current sequence, ParamID is the  paramID of current event
    //output: decsion made by other user(not current logID) with certain EventID and SequenceNumber
    public List<UserEventBean> ReadEventByParamIDAndSequenceNum(int logID, int SequenceNum, String ParamID) {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, logID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta FROM simmandebrief.userevent2 "
                    + "WHERE logID <> ? AND SequenceNum = ? AND paramID = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logID);
            ps.setInt(2, SequenceNum);
            ps.setString(3, ParamID);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(logID);
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    //get all the decision with same name and same sequence number(decision is event type)
    //input: logID is the current user's log, sequenceNum is current sequence;
    //output: decsion made by other user(not current logID) with the same EventID and SequenceNumber
    public List<UserEventBean> ReadEventByParamIDAndSequenceNumALL(int logID, int SequenceNum) {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, logID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta FROM simmandebrief.userevent2 "
                    + "WHERE paramID IN "
                    + "(SELECT SELECT DISTINCT paramID FROM simmandebrief.userevent2 WHERE logID = ? AND SequenceNum = ?) "
                    + "AND logID <> ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logID);
            ps.setInt(2, SequenceNum);
            ps.setInt(3, logID);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(logID);
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    //get all events but only paramType is events should be return
    public List<UserEventBean> GetAllEvents() {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            String sql = "SELECT eventID, logID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta FROM simmandebrief.userevent2 WHERE paramType = \"EventType\" ";

            PreparedStatement ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(rs.getInt("logID"));
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("paramID"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            return userEventList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    //get maxmium sequenceNumber by logID
    public int GetMaxSequenceNumByLogID(int logID) {

        try {
            String sql = "SELECT MAX(sequenceNum) AS maxSequenceNum FROM simmandebrief.userevent2 "
                    + "WHERE  logID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, logID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maxSequenceNum");

            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

    /**
     * <p>
     * Get eventID in certain sequence and user(logID)<br>
     *
     * @param sequenceNum
     * @param logID
     * @return eventID
     */
    public int GetEventIDBySequenceNumAndLogID(int sequenceNum, int logID) {

        try {
            String sql = "SELECT eventID FROM simmandebrief.userevent2 "
                    + "WHERE  sequenceNum = ? AND logID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            ps.setInt(2, logID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("eventID");

            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }

    }

    public String GetParamIDBySequenceNumAndLogID(int sequenceNum, int logID) {

        try {
            String sql = "SELECT paramID FROM simmandebrief.userevent2 "
                    + "WHERE  sequenceNum = ? AND logID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            ps.setInt(2, logID);
            rs = ps.executeQuery();
            String result = "";
            if (rs.next()) {
                result += rs.getInt("paramID") + "\t";

            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

    }

    public int CombineEventInSameTime() {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        try {
            //1.get all combined event data list 
            String sql = "SELECT eventID, logID, msec, level, paramID, paramType, paramValue,sequenceNum, decisionTimeDelta , GROUP_CONCAT(paramID) AS decisionName "
                    + "FROM simmandebrief.userevent2 WHERE paramType = \"EventType\"  GROUP BY logID, msec ";

            PreparedStatement ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                UserEventBean userEventBean = new UserEventBean();
                userEventBean.setEventID(rs.getInt("eventID"));
                userEventBean.setLogID(rs.getInt("logID"));
                userEventBean.setLevel(rs.getInt("level"));
                userEventBean.setMsec(rs.getInt("msec"));
                userEventBean.setParamID(rs.getString("decisionName"));
                userEventBean.setParamType(rs.getString("paramType"));
                userEventBean.setParamValue(rs.getString("paramValue"));
                userEventBean.setSequenceNum(rs.getInt("sequenceNum"));
                userEventBean.setDecisionTimeDelta(rs.getInt("decisionTimeDelta"));
                userEventList.add(userEventBean);

            }
            //2. delete all event type row in the table
            sql = "DELETE FROM simmandebrief.userevent2 WHERE paramType = \"EventType\"";
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            //3. insert all the new event data in userEventList back to table
            for (UserEventBean userEventBean : userEventList) {
                 sql = "INSERT INTO simmandebrief.userevent2(logID, msec, level, paramID, paramType, paramValue, sequenceNum, decisionTimeDelta ) values (?,?,?,?,?,?,?,?)";

                ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userEventBean.getLogID());
                ps.setInt(2, userEventBean.getMsec());
                ps.setInt(3, userEventBean.getLevel());
                ps.setString(4, userEventBean.getParamID());
                ps.setString(5, userEventBean.getParamType());
                ps.setString(6, userEventBean.getParamValue());
                ps.setInt(7, userEventBean.getSequenceNum());
                ps.setInt(8, userEventBean.getDecisionTimeDelta());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                } else {
                    result= 0;
                }
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
