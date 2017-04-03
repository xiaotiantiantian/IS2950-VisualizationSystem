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
import java.util.ArrayList;
import java.util.List;
import model.UserBean;
import model.DecisionTimeBean;
import model.UserEventBean;

/**
 *
 * @author Tian Zhirun
 */
public class DecisionTimeDao {

    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public DecisionTimeDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==Decision Time Dao connection==");
    }
      public int InserDecisionTimeIntoDB( DecisionTimeBean decisionTimeBean) throws SQLException{
        try{
             String sql = "INSERT INTO simmandebrief.decision_time(expertLevel, logID, sequenceNum, decisionTimeDelta) values (?,?,?,?)";
             
             ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             ps.setInt(1, decisionTimeBean.getExpertLevel());
             ps.setInt(2, decisionTimeBean.getLogID());
             ps.setInt(3, decisionTimeBean.getSequenceNum());
             ps.setInt(4, decisionTimeBean.getDecisionTimeDelta());
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
      //get average time of all people in certain sequence
      public int getAvgTime(int sequenceNum){
           try{
             String sql = "SELECT AVG(decisionTimeDelta) AS averageTime FROM simmandebrief.decision_time where sequenceNum = ?";
             
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            rs = ps.executeQuery();
            int resultAvgTime = -1;
            if (rs.next()) {
               
              resultAvgTime = rs.getInt("averageTime");
            }
            return resultAvgTime;
            
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
          
      }
      //get average expert time
            public int getExpertAvgTime(int sequenceNum){
           try{
             String sql = "SELECT AVG(decisionTimeDelta) AS averageTime FROM simmandebrief.decision_time where sequenceNum = ? and expertLevel = ?";
             
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            ps.setInt(2, 3);    //clinician is expert? I assume so, if not, just change it. If resident means expert, change 3 to 2
            rs = ps.executeQuery();
            int resultExpertAvgTime = -1;
            if (rs.next()) {
               
              resultExpertAvgTime = rs.getInt("averageTime");
            }
            return resultExpertAvgTime;
            
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
          
      }
 //get average cohort/student time
            public int getCohortAvgTime(int sequenceNum){
           try{
             String sql = "SELECT AVG(decisionTimeDelta) AS averageTime FROM simmandebrief.decision_time where sequenceNum = ? and expertLevel = ?";
             
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sequenceNum);
            ps.setInt(2, 1);    //student means cohort?
            rs = ps.executeQuery();
            int resultCohortAvgTime = -1;
            if (rs.next()) {
               
              resultCohortAvgTime = rs.getInt("averageTime");
            }
            return resultCohortAvgTime;
            
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
          
      }


}
