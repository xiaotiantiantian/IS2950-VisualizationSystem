/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tian Zhirun
 */
public class DecisionTimeBean {
    int ExpertLevel;
    int LogID;
    int SequenceNum;
    int DecisionTimeDelta;

    public DecisionTimeBean() {
    }

    public DecisionTimeBean(int ExpertLevel, int LogID, int SequenceNum, int DecisionTimeDelta) {
        this.ExpertLevel = ExpertLevel;
        this.LogID = LogID;
        this.SequenceNum = SequenceNum;
        this.DecisionTimeDelta = DecisionTimeDelta;
    }
    

    public int getExpertLevel() {
        return ExpertLevel;
    }

    public void setExpertLevel(int ExpertLevel) {
        this.ExpertLevel = ExpertLevel;
    }

    public int getLogID() {
        return LogID;
    }

    public void setLogID(int LogID) {
        this.LogID = LogID;
    }

    public int getSequenceNum() {
        return SequenceNum;
    }

    public void setSequenceNum(int SequenceNum) {
        this.SequenceNum = SequenceNum;
    }

    public int getDecisionTimeDelta() {
        return DecisionTimeDelta;
    }

    public void setDecisionTimeDelta(int DecisionTimeDelta) {
        this.DecisionTimeDelta = DecisionTimeDelta;
    }


    
   
}
