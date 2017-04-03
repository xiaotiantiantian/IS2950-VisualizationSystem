/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Tian Zhirun
 */
public class DecisionNode {
//    UserEventBean userEventBean;
    int eventID;
    String decisionName;
    int sequenceNum;
    int parentID;
    int avgTime;
    int expertTime;
    int cohortAvgTime;
    int yourTime;
    List<Integer> userIDs;

    public DecisionNode() {
    }

    public DecisionNode(int eventID, String decisionName, int sequenceNum, int parentID, int avgTime, int expertTime, int cohortAvgTime, int yourTime, List<Integer> userIDs) {
        this.eventID = eventID;
        this.decisionName = decisionName;
        this.sequenceNum = sequenceNum;
        this.parentID = parentID;
        this.avgTime = avgTime;
        this.expertTime = expertTime;
        this.cohortAvgTime = cohortAvgTime;
        this.yourTime = yourTime;
        this.userIDs = userIDs;
    }

    public DecisionNode(UserEventBean userEventBean, int parentID, int avgTime, int expertTime, int cohortAvgTime, int yourTime, List<Integer> userIDs) {
        this.eventID = userEventBean.getEventID();
        this.decisionName = userEventBean.getParamID();
        this.sequenceNum = userEventBean.getSequenceNum();
        this.parentID = parentID;
        this.avgTime = avgTime;
        this.expertTime = expertTime;
        this.cohortAvgTime = cohortAvgTime;
        this.yourTime = yourTime;
        this.userIDs = userIDs;
    }


    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public int getExpertTime() {
        return expertTime;
    }

    public void setExpertTime(int expertTime) {
        this.expertTime = expertTime;
    }

    public int getCohortAvgTime() {
        return cohortAvgTime;
    }

    public void setCohortAvgTime(int cohortAvgTime) {
        this.cohortAvgTime = cohortAvgTime;
    }

    public int getYourTime() {
        return yourTime;
    }

    public void setYourTime(int yourTime) {
        this.yourTime = yourTime;
    }


    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public void setDecisionName(String decisionName) {
        this.decisionName = decisionName;
    }

    public int getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(int sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public List<Integer> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<Integer> userIDs) {
        this.userIDs = userIDs;
    }

    
}
