
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

import javax.xml.parsers.DocumentBuilderFactory;
import model.patientBean;

/**
 *
 * @author Tian Zhirun
 */
public class PatientDataDao {

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    //Load and parse XML file into DOM 
    String XMLFilePath;
    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public PatientDataDao() throws SQLException {
        try {
            //connect to database and select the record
            connection = DbConnection.getConnection();
            if (connection.isClosed()) {
                System.out.println("==connection closed exception==");
            }
            System.out.println("==patientDataDao connection==");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public int insertBasicInfoToDB(int userid, patientBean pB, int simulationID, int expertiseLevel) throws SQLException {
        try {

            //if there is a same entry, just update, if not, inset, use userid+timestamp as unique (primary key)
            String sql = "INSERT INTO simmandebrief.userevent(userid, simulationID, expertiseLevel, timestamp, hours, minutes, seconds,priority ) "
                    + "select ?,?,?,?,?,?,?,? where not exists (select * from simmandebrief.userevent where userid = ? and timestamp =?)";

//            Select values...
//WHERE NOT EXISTS
//   (SELECT *
//    FROM myTable
//    WHERE pk_part1 = value1,
//        AND pk_part2 = value2)
            //  WHRE NOT EXISTS (SELECT * FROM simmandebrief.userevent WHERE userid =? and timestamp=? "
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userid);
            ps.setInt(2, simulationID);
            ps.setInt(3, expertiseLevel);
            ps.setString(4, pB.getTimestamp());
            ps.setString(5, pB.getHours());
            ps.setString(6, pB.getMinutes());
            ps.setString(7, pB.getSeconds());
            ps.setInt(8, pB.getPriority());
            ps.setInt(9, userid);
            ps.setString(10, pB.getTimestamp());
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

    public int updateValuesToDB(int userid, patientBean pB, int simulationID, int expertiseLevel) throws SQLException {
        sql = "update simmandebrief.userevent set ";
        if (pB.getBPDiastolic() != -1 && pB.getBPSystolic() != -1) {
            sql += " BPDiastolic = '" + pB.getBPDiastolic() + "' ";
            sql += ", BPSystolic = '" + pB.getBPSystolic() + "' ";
        } else if (pB.getHR() != -1) {
            sql += "HR = '" + pB.getHR() + "' ";
        } else if (pB.getSpO2() != -1) {
            sql += " SpO2 = '" + pB.getSpO2() + "' ";
        } else {
            return -1;
        }
        sql += " where UserID = '" + userid + "' and timestamp = '" + pB.getTimestamp() + "' " + " and priority = '" + pB.getPriority() + "'"
                + "and simulationID = '" + simulationID + "' and expertiseLevel = '" + expertiseLevel + "'";

        PreparedStatement ps1 = connection.prepareStatement(sql);
        ps1.executeUpdate();
        return 0;
    }

    public int deleteUserEvent(int userid) {
        try {
            String sql = "DELETE FROM simmandebrief.userevent WHERE userID = '" + userid + " ' ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public List<patientBean> getUserHRwithTime(int userid) {
        List<patientBean> patientList = new ArrayList<patientBean>();
        try {
            String sql = "SELECT *, count(distinct SUBQUERY.timestamp) from( SELECT hours, minutes, seconds, HR, timestamp  FROM simmandebrief.userevent where userID = ? and HR is not null) AS SUBQUERY group by SUBQUERY.timestamp";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();
            while (rs.next()) {
                patientBean pB = new patientBean();
                pB.setHours(rs.getString("hours"));
                pB.setMinutes(rs.getString("minutes"));
                pB.setSeconds(rs.getString("seconds"));
                pB.setHR(rs.getInt("HR"));
                patientList.add(pB);
            }
            return patientList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<patientBean> getAllEventFromDB() {
        List<patientBean> patientList = new ArrayList<patientBean>();
        try {
            String sql = "SELECT * FROM simmandebrief.userevent";
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                patientBean pB = new patientBean();

                pB.setUserID(rs.getInt("userID"));
                pB.setHours(rs.getString("hours"));
                pB.setMinutes(rs.getString("minutes"));
                pB.setSeconds(rs.getString("seconds"));
                pB.setHR(rs.getInt("HR"));
                pB.setBPDiastolic(rs.getInt("BPDiastolic"));
                pB.setBPSystolic(rs.getInt("BPSystolic"));
                pB.setSpO2(rs.getInt("SpO2"));

                patientList.add(pB);
            }
            return patientList;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
