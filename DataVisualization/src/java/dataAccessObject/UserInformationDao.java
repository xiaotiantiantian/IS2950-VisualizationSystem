/*zm 
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
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;
    
    /**
     *
     * @throws SQLException
     */
    public UserInformationDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==userInformationDao connection==");
    }
    
    //if the user not exist, insert a new user id with the user name

    /**
     *<p>if the user not exist, insert a new user id with the user name<br>
     * @param name input user name
     * @return
     */
    
    public int insertUser(String name){
       try {
            String sql = "INSERT INTO simmandebrief.userinformation (userName) values (?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                return autoKey=rs.getInt(1);
            else
                return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage()) ;
            return -1;
        }  
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
