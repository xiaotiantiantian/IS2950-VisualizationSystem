
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
//    public PatientDataDao(String XMLFilePath) {
//        this.XMLFilePath = XMLFilePath;
//    }
//    
//    
//
//    public Document parse(String filePath) {
//        Document document = null;
//        try {
//            //DOM parser instance 
//            DocumentBuilder builder = builderFactory.newDocumentBuilder();
//            //parse an XML file into a DOM tree 
//            document = builder.parse(new File(filePath));
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }
//
//    public void ReadXML(String filePath) {
//        DOMParser parser = new DOMParser();
//        Document document = parse(filePath);
//        //get root element 
//        Element rootElement = document.getDocumentElement();
//
//        //traverse child elements 
//        NodeList nodes = rootElement.getChildNodes();
//        for (int i = 0; i < nodes.getLength(); i++) {
//            Node node = nodes.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element child = (Element) node;
//                //process child element 
//            }
//        }
//
//        NodeList nodeList = rootElement.getElementsByTagName("com");
//        if (nodeList != null) {
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Element element = (Element) nodeList.item(i);
//                String id = element.getAttribute("Heart Rate");
//            }
//        }
//    }

    public int insertTimestampToDB(int userid, patientBean pB) throws SQLException {
        try {
            String sql = "INSERT INTO simmandebrief.userevent (userid, timestamp, hours, minutes, seconds,priority ) values (?,?,?,?,?,?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userid);
            ps.setString(2, pB.getTimestamp());
            ps.setString(3, pB.getHours());
            ps.setString(4, pB.getMinutes());
            ps.setString(5, pB.getSeconds());
            ps.setInt(6, pB.getPriority());
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

    public int updateValuesToDB(int userid, patientBean pB) throws SQLException {
        sql = "update simmandebrief.userevent set ";
//
//        if (pB.getTimestamp()!="")
//            sql+= " timestamp = '" + pB.getTimestamp()+"' ";
//        if (pB.getHours() != "") {
//            sql += " hours = '" + pB.getHours() + "' ";
//        }
//        if (pB.getMinutes() != "") {
//            sql += " minutes = '" + pB.getMinutes() + "' ";
//        }
//        if (pB.getSeconds() != "") {
//            sql += " seconds = '" + pB.getSeconds() + "' ";
//        }

        if (pB.getBPDiastolic() != -1 && pB.getBPSystolic() != -1) {
            sql += " BPDiastolic = '" + pB.getBPDiastolic() + "' ";

            sql += " BPSystolic = '" + pB.getBPSystolic() + "' ";
        } else if (pB.getHR() != -1) {
            sql += "HR = '" + pB.getHR() + "' ";
        } else if (pB.getSpO2() != -1) {
            sql += " SpO2 = '" + pB.getSpO2() + "' ";
        } else {
            return -1;
        }
        sql += " where UserID = '" + userid + "' and timestamp = '" + pB.getTimestamp() + "' ";

        PreparedStatement ps1 = connection.prepareStatement(sql);
        ps1.executeUpdate();
        return 0;
//        return -1;
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
    
    public List<patientBean> getUserHRwithTime(int userid){
        List<patientBean> patientList= new ArrayList<patientBean>();
        try {
            
            //this sql statement could only used at mysql!!!
//            String sql = "SELECT *, count(distinct SUBQUERY.timestamp) from( SELECT hours, minutes, seconds, HR, timestamp  FROM simmandebrief.userevent where userID = '"+userid+"' and HR is not null) AS SUBQUERY group by SUBQUERY.timestamp";
            String sql = "SELECT *, count(distinct SUBQUERY.timestamp) from( SELECT hours, minutes, seconds, HR, timestamp  FROM simmandebrief.userevent where userID = ? and HR is not null) AS SUBQUERY group by SUBQUERY.timestamp";

//            ps = connection.prepareStatement(sql);        
//            ps.executeQuery();
//            ResultSet rs = st.executeQuery(sql);
//                    ps.getGeneratedKeys();
             PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();
            while(rs.next()){
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

}

//public class PatientDataDao {
//
//    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//    //Load and parse XML file into DOM 
//    String XMLFilePath;
//
//    public PatientDataDao(String XMLFilePath) {
//        this.XMLFilePath = XMLFilePath;
//    }
//    
//    
//
//    public Document parse(String filePath) {
//        Document document = null;
//        try {
//            //DOM parser instance 
//            DocumentBuilder builder = builderFactory.newDocumentBuilder();
//            //parse an XML file into a DOM tree 
//            document = builder.parse(new File(filePath));
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }
//
//    public void ReadXML(String filePath) {
//        DOMParser parser = new DOMParser();
//        Document document = parse(filePath);
//        //get root element 
//        Element rootElement = document.getDocumentElement();
//
//        //traverse child elements 
//        NodeList nodes = rootElement.getChildNodes();
//        for (int i = 0; i < nodes.getLength(); i++) {
//            Node node = nodes.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element child = (Element) node;
//                //process child element 
//            }
//        }
//
//        NodeList nodeList = rootElement.getElementsByTagName("com");
//        if (nodeList != null) {
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Element element = (Element) nodeList.item(i);
//                String id = element.getAttribute("Heart Rate");
//            }
//        }
//    }
//
//}
