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
public class UserLogDao {

    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public UserLogDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==userLogDao connection==");
    }

    public int insertLog(int userID, int simulationID, String XMLPath, int expertLevel, int grade) {
        try {
            String sql = "INSERT INTO simmandebrief.userlog (userID, simulationID, XMLPath, expertLevel, grade) values (?, ? , ?, ?,?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userID);
            ps.setInt(2, simulationID);
            ps.setString(3, XMLPath);
            ps.setInt(4, expertLevel);
            ps.setInt(5, grade);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return autoKey = rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
