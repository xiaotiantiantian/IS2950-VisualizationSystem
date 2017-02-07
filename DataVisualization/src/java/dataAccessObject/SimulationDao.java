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
import model.SimulationBean;

/**
 *
 * @author Tian Zhirun
 */
public class SimulationDao {

    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    private String nonceval = "";
    PreparedStatement ps = null;
    int autoKey = 0;

    public SimulationDao() throws SQLException {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        if (connection.isClosed()) {
            System.out.println("==connection closed exception==");
        }
        System.out.println("==userSimulation Dao connection==");
    }

    public List<SimulationBean> getAllSimulations() {
        List<SimulationBean> simulationBeanList = new ArrayList<SimulationBean>();
        try {
            sql = "select  SimID, Title from simmandebrief.simulation";
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);   
            while (rs.next()) {
                SimulationBean simualtionBean = new SimulationBean(rs.getInt("SimID"),rs.getString("Title"));
                simulationBeanList.add(simualtionBean);
               
            } 
            return simulationBeanList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
