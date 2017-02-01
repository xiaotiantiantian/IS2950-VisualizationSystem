/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmltest;

/**
 *
 * @author Tian Zhirun
 */
public class patientBean {
    private String timestamp;
    private String hours;
    private String minutes;
    private String seconds;
    private int priority;
    private int HR;
    private int BPSystolic;
    private int BPDiastolic;
    private int SpO2;

    public patientBean() {
         this.timestamp = "";
        this.hours = "";
        this.minutes = "";
        this.seconds = "";
        this.priority = -1;
        this.HR = -1;
        this.BPSystolic = -1;
        this.BPDiastolic = -1;
        this.SpO2 = -1;
    }
    
    

    public patientBean(String timestamp, String hours, String minutes, String seconds, int priority, int HR, int BPSystolic, int BPDiastolic, int SpO2) {
        this.timestamp = timestamp;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.priority = priority;
        this.HR = HR;
        this.BPSystolic = BPSystolic;
        this.BPDiastolic = BPDiastolic;
        this.SpO2 = SpO2;
    }

    public int getSpO2() {
        return SpO2;
    }

    public void setSpO2(int SpO2) {
        this.SpO2 = SpO2;
    }

    public int getBPDiastolic() {
        return BPDiastolic;
    }

    public void setBPDiastolic(int BPDiastolic) {
        this.BPDiastolic = BPDiastolic;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getHR() {
        return HR;
    }

    public void setHR(int HR) {
        this.HR = HR;
    }

    public int getBPSystolic() {
        return BPSystolic;
    }

    public void setBPSystolic(int BPSystolic) {
        this.BPSystolic = BPSystolic;
    }
    
    
            
            
    
}
