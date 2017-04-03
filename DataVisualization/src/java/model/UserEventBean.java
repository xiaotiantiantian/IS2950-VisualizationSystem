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
//work for database table userevent2
public class UserEventBean {
    private int EventID;
    private int LogID;
    private int Msec;
    private int Level;
    //there are 3 kinds of param: paramvalues/instructor feedback/event reporter
    //paramid<- paramid/eventid/feedback tag
    private String ParamID;
    //paramtype<-it could be param/event/feedback
    private String ParamType;
    //paramvalue<- paramvalue/feedback value/ 
    private String ParamValue;
    
    private int sequenceNum;
    private int decisionTimeDelta;
    
    

    public UserEventBean() {
    }

    public UserEventBean(int EventID, int LogID, int Msec, int Level, String ParamID, String ParamType, String ParamValue) {
        this.EventID = EventID;
        this.LogID = LogID;
        this.Msec = Msec;
        this.Level = Level;
        this.ParamID = ParamID;
        this.ParamType = ParamType;
        this.ParamValue = ParamValue;
    }

    public UserEventBean(int EventID, int LogID, int Msec, int Level, String ParamID, String ParamType, String ParamValue, int sequenceNum, int decisionTimeDelta) {
        this.EventID = EventID;
        this.LogID = LogID;
        this.Msec = Msec;
        this.Level = Level;
        this.ParamID = ParamID;
        this.ParamType = ParamType;
        this.ParamValue = ParamValue;
        this.sequenceNum = sequenceNum;
        this.decisionTimeDelta = decisionTimeDelta;
    }

    
    
    
    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int EventID) {
        this.EventID = EventID;
    }

    public int getLogID() {
        return LogID;
    }

    public void setLogID(int LogID) {
        this.LogID = LogID;
    }

    public int getMsec() {
        return Msec;
    }

    public void setMsec(int Msec) {
        this.Msec = Msec;
    }

    public String getParamID() {
        return ParamID;
    }

    public void setParamID(String ParamID) {
        this.ParamID = ParamID;
    }

    public String getParamType() {
        return ParamType;
    }

    public void setParamType(String ParamType) {
        this.ParamType = ParamType;
    }

    public String getParamValue() {
        return ParamValue;
    }

    public void setParamValue(String ParamValue) {
        this.ParamValue = ParamValue;
    }

    public int getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(int sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public int getDecisionTimeDelta() {
        return decisionTimeDelta;
    }

    public void setDecisionTimeDelta(int decisionTimeDelta) {
        this.decisionTimeDelta = decisionTimeDelta;
    }

    
    
    
    
}
