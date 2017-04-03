
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tian Zhirun
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
import model.UserBean;

/**
 *
 * @author Tian Zhirun
 */
public class UserInfoDao {

    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public UserInfoDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==userInfo Dao connection==");
    }

    public List<UserBean> getAllUser() {
        List<UserBean> userBeanList = new ArrayList<UserBean>();
        try {
            sql = "select  userID, firstName from simmandebrief.users";
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);   
            while (rs.next()) {
                UserBean userBean = new UserBean(rs.getInt("userID"),rs.getString("firstName"));
                userBeanList.add(userBean);
               
            } 
            return userBeanList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public String getUserByID(int userid){
         try {
             String userName = "";
            sql = "select  firstName from simmandebrief.users where userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();   
            if (rs.next()) {
                userName = rs.getString("firstName");
                return userName;
            } else  return "";

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "";
        }
    }

}
