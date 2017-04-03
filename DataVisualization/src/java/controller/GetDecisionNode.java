/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccessObject.DecisionTimeDao;
import dataAccessObject.UserEventDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DecisionNode;
import model.UserEventBean;

/**
 *
 * @author Tian Zhirun
 *
 * get decision nodes in different sequence each sequence has a decision node
 * list find the parent node of each node
 */
public class GetDecisionNode {

    UserEventDao userEventDao;
    List<DecisionNode> decisionNodeList;

    public GetDecisionNode() throws SQLException {
        this.userEventDao = new UserEventDao();
    }

    //get all the event with parent id
    //we assume each logID as a UserID(each log means a individual user)
    public List<DecisionNode> GetEventsWithParentInfo(int logID) throws SQLException {

        List<DecisionNode> decisionList = new ArrayList<DecisionNode>(); //store all event with parent information

        List<DecisionNode> decisionListTmp = new ArrayList<DecisionNode>();   //store event of last sequence 
        List<Integer> logIDs = new ArrayList<Integer>();
        logIDs.add(logID);
        DecisionNode decisionNode = new DecisionNode(0, "root", 0, -1, 0, 0, 0, 0, logIDs);
        decisionList.add(decisionNode);
        //put the 0st sequence elements into decisionListTmp
        //with the loop, change it to next sequence elements.
        decisionListTmp.add(decisionNode);
        //if userid=0, the node is parent of all other node in sequence(level) 1

        //then iterate from sequence 1 to final
        int maxSequence = userEventDao.GetMaxSequenceNumByLogID(logID);

        for (int i = 1; i <= maxSequence; i++) {  //seq start from 1
            //firstly cluster event in same sequence and have same name to 1 element
            List<UserEventBean> eventListOrig = userEventDao.ReadEventBySeqNum(i);
            List<DecisionNode> decisionListInside = new ArrayList<DecisionNode>();
            for (int j = 0; j < eventListOrig.size(); j++) {
                this.cluserEvent(decisionListInside, eventListOrig.get(j), i, logID);
            }
            //secondly, find parent node of the clusetered nodes
            //compare sequence i-1 with i, if the element in sequence i has parents, 
            //write it back to that element(from '-1' to eventID of its parent
            for (int k = 0; k < decisionListInside.size(); k++) {
                //compare each decisionListInside node with decisionListTmp node list;
                decisionNode = decisionListInside.get(k);
                for (int m = 0; m < decisionListTmp.size(); m++) {
                    //for one decision has many userIDs, there is another loop for userIDs
                    for (int n = 0; n < decisionNode.getUserIDs().size(); n++) {
                        int tmpUserID = decisionNode.getUserIDs().get(n);
                        if (decisionListTmp.get(m).getUserIDs().contains(tmpUserID)) {
                            //write parents ID (eventID) into child's "parentID" variable
                            decisionNode.setParentID(decisionListTmp.get(m).getEventID());
                        }
                    }
//                    if(decisionNode.getUserIDs().contains(m))
                }
                //push element(has already checked the parent id) to "decisionList"
                decisionList.add(decisionNode);
            }
            decisionListTmp = decisionListInside;

        }

        return decisionList;
    }

    //if eventList contains certain , just put the userID into the field of this entry,else return false
    public boolean cluserEvent(List<DecisionNode> eventList, UserEventBean userEventBean, int sequenceNum, int logID) throws SQLException {
        //if eventList has the element, just input the userID into that entity, and return ture
        String paramID = userEventBean.getParamID();
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getDecisionName().equals(paramID)) {
                List<Integer> userIDs = eventList.get(i).getUserIDs();
                userIDs.add(userEventBean.getLogID());
                eventList.get(i).setUserIDs(userIDs);
                if (logID == userEventBean.getLogID()) {
                   eventList.get(i).setYourTime(userEventBean.getDecisionTimeDelta());
                } 
                return true;
            }

        }

        //if not have that element, just insert it into eventList, and return false
        int yourTime;
        if (logID == userEventBean.getLogID()) {
            yourTime = userEventBean.getDecisionTimeDelta();
        } else {
            yourTime = -1;  //-1 means that element has no "your time"
        }
        List<Integer> userIDs = new ArrayList<Integer>();
        userIDs.add(userEventBean.getLogID());
        DecisionTimeDao decisionTimeDao = new DecisionTimeDao();
        DecisionNode decisionNode = new DecisionNode(
                userEventBean,
                -2,
                decisionTimeDao.getAvgTime(sequenceNum),
                decisionTimeDao.getExpertAvgTime(sequenceNum),
                decisionTimeDao.getCohortAvgTime(sequenceNum),
                yourTime,
                userIDs
        );
        eventList.add(decisionNode);
        return false;
    }

    public List<Map<String, String>> convertNodeListToMapList() {
        return convertNodeListToMapList(this.decisionNodeList);
    }

    public List<Map<String, String>> convertNodeListToMapList(List<DecisionNode> decisionNodeList) {
        //it seems Map<String, String>
        List<Map<String, String>> decisionMapList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < decisionNodeList.size(); i++) {
            DecisionNode dN = decisionNodeList.get(i);
            Map<String, String> decisionMap = new HashMap<String, String>();
            decisionMap.put("id", Integer.toString(dN.getEventID()));
            decisionMap.put("name", dN.getDecisionName());
            if (dN.getParentID() >= 0) {
                decisionMap.put("parent_id", Integer.toString(dN.getParentID()));
            }
            decisionMap.put("avgTime", Integer.toString(dN.getAvgTime()));
            decisionMap.put("expertTime", Integer.toString(dN.getExpertTime()));
            decisionMap.put("cohortAvgTime", Integer.toString(dN.getCohortAvgTime()));
            decisionMap.put("yourTime", Integer.toString(dN.getYourTime()));
            StringBuilder sb = new StringBuilder();
            for (Integer num : dN.getUserIDs()) {
                sb.append(num.toString());
                sb.append("\t");
            }
            decisionMap.put("userIDs", sb.toString());
            System.out.println(sb.toString());
            decisionMapList.add(decisionMap);

        }

        return decisionMapList;
    }

    public String getJsonByMapList(List<Map<String, String>> decisionMapList) {
        String jsonStr = TreeHelper.getAllChildrenJSONTrees(decisionMapList, "0");
        return "[" + jsonStr + "]";
    }

    public String getJsonByNodeList(List<DecisionNode> decisionNodeList) {
        List<Map<String, String>> decisionMapList = convertNodeListToMapList(decisionNodeList);
        return getJsonByMapList(decisionMapList);
    }

}
