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
public class UserBean {
    private int UserID;
    private String UserName;

    public UserBean() {
    }

    public UserBean(int UserID, String UserName) {
        this.UserID = UserID;
        this.UserName = UserName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    
    
    
}
