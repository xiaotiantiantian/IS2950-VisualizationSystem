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
public class PatientData {
    
    private String EventType;
    private String EventSubtype;
    private String EventName;
    private Integer Hours;
    private Integer Minutes;
    private Integer Seconds;
    private Integer Priority;
    private Integer NumVal;
    private String StringVal;
    private Integer PatientID;
    private Integer SimID;

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String EventType) {
        this.EventType = EventType;
    }

    public String getEventSubtype() {
        return EventSubtype;
    }

    public void setEventSubtype(String EventSubtype) {
        this.EventSubtype = EventSubtype;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public Integer getHours() {
        return Hours;
    }

    public void setHours(Integer Hours) {
        this.Hours = Hours;
    }

    public Integer getMinutes() {
        return Minutes;
    }

    public void setMinutes(Integer Minutes) {
        this.Minutes = Minutes;
    }

    public Integer getSeconds() {
        return Seconds;
    }

    public void setSeconds(Integer Seconds) {
        this.Seconds = Seconds;
    }

    public Integer getPriority() {
        return Priority;
    }

    public void setPriority(Integer Priority) {
        this.Priority = Priority;
    }

    public Integer getNumVal() {
        return NumVal;
    }

    public void setNumVal(Integer NumVal) {
        this.NumVal = NumVal;
    }

    public String getStringVal() {
        return StringVal;
    }

    public void setStringVal(String StringVal) {
        this.StringVal = StringVal;
    }

    public Integer getPatientID() {
        return PatientID;
    }

    public void setPatientID(Integer PatientID) {
        this.PatientID = PatientID;
    }

    public Integer getSimID() {
        return SimID;
    }

    public void setSimID(Integer SimID) {
        this.SimID = SimID;
    }
    
    
    
}
