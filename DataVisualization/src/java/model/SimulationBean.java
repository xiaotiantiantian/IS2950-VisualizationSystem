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
public class SimulationBean {
    private int SimID;
    private String Title;

    public SimulationBean(int SimID, String Title) {
        this.SimID = SimID;
        this.Title = Title;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getSimID() {
        return SimID;
    }

    public void setSimID(int SimID) {
        this.SimID = SimID;
    }
    
}
