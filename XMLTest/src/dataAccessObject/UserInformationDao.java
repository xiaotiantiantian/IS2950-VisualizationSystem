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

/**
 *
 * @author Tian Zhirun
 */
public class UserInformationDao {

    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";

    public UserInformationDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==userInformationDao connection==");
    }

    //retrieval anyonmous user
    public int retrieveUser(String name) {
        try {
            //sql = "SELECT * from is2560.users WHERE username = ? ";               
            sql = "select  userID from simmandebrief.userinformation  where  userName = '" + name + "'";
            System.out.println("username: " + name);

            PreparedStatement ps = connection.prepareStatement(sql);
            //ps.setString(1, name);     
            //System.out.println(ps);

            rs = ps.executeQuery(sql);
//            String username = "";
            int flag = -3, userid = 0;
            while (rs.next()) {
//                    Retrieve by column name                    
//                pwd = rs.getString("Password");
                userid = rs.getInt("userID");
                System.out.println("userID:" + userid);
                flag = -1;
            }
          
            if (flag < -1) {
                return flag;
            } else {
                return userid;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int changeUserXML(int userid, String filename)throws SQLException{
        sql = "update simmandebrief.userinformation set userXMLPath = '" + filename + "' where UserID = '"+ userid + "'";
        PreparedStatement ps1 = connection.prepareStatement(sql);
        ps1.executeUpdate();
        return 0;
    }

}
