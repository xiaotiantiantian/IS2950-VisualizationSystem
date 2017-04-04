/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Tian Zhirun
 */
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//database............................
import DbConnect.DbConnection;
import dataAccessObject.PatientDataDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.patientBean;
import model.UserEventBean;

/**
 *
 * @author Tian Zhirun
 *
 * This class is for parsing new version XML file XMLReader class is for parsing
 * old version XML file
 */
public class XMLReaderNew {

    DocumentBuilderFactory builderFactory;
    String XMLFilePath;
    Document document;
    int logID;

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }
    
    
    

    public XMLReaderNew() {
        this.XMLFilePath = "C:\\Users\\Tian Zhirun\\Desktop\\Study\\New XML Files\\20170222NARCOD2.xml";
        this.builderFactory = DocumentBuilderFactory.newInstance();
        this.document = null;

        try {
            //DOM parser instance 
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //parse an XML file into a DOM tree 
            document = builder.parse(new File(XMLFilePath));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XMLReaderNew(String XMLFilePath) {
        this.XMLFilePath = XMLFilePath;
        this.builderFactory = DocumentBuilderFactory.newInstance();
        this.document = null;

        try {
            //DOM parser instance 
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //parse an XML file into a DOM tree 
            document = builder.parse(new File(XMLFilePath));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public List<UserEventBean> readXMLToUserEventBeans() throws SQLException, NumberFormatException {
        List<UserEventBean> userEventList = new ArrayList<UserEventBean>();
        Element root = document.getDocumentElement();
        NodeList list = root.getElementsByTagName("LogElement");
        for (int i = 0; i < list.getLength(); i++) {
            System.out.println("\nit is the " + i + " th element\n");
            UserEventBean userEventBean = new UserEventBean();
            userEventBean.setLogID(logID);
            
//            int shouldPushFlag = 0;

            Element n = (Element) list.item(i);

            NamedNodeMap node = n.getAttributes();
            for (int x = 0; x < node.getLength(); x++) {
                Node nn = node.item(x);
                System.out.println(nn.getNodeName() + ": " + nn.getNodeValue());

                if (nn.getNodeName() == "When_msec") {
                    userEventBean.setMsec(Integer.valueOf(nn.getNodeValue()));

                }

                if (nn.getNodeName() == "Level") {
                    userEventBean.setLevel(Integer.valueOf(nn.getNodeValue()));
                }

            }
            //deal 3 type of parameter: parameterValue/Instructor Feedback/ Event reporter
            //work with type1: parameterValue
            NodeList childList = n.getElementsByTagName("ParameterValue");
            //to iterate child elements
            for (int j = 0; j < childList.getLength(); j++) {
                System.out.println("\nit is the " + j + " th child Parameter\n");
                Element childN = (Element) childList.item(j);
                NamedNodeMap childAttributeNode = childN.getAttributes();
                for (int k = 0; k < childAttributeNode.getLength(); k++) {
                    Node childNN = childAttributeNode.item(k);
                    System.out.println(childNN.getNodeName() + ": " + childNN.getNodeValue());

                    if (childNN.getNodeName().equals("ParamId")) {
                        userEventBean.setParamID(childNN.getNodeValue());
                    }
                    if (childNN.getNodeName().equals("ParamValue")) {
                            userEventBean.setParamValue(childNN.getNodeValue());
                    }
                    if (childNN.getNodeName().equals("AA:type")) {
                         userEventBean.setParamType(childNN.getNodeValue()); 
                    }                   

                }
                userEventList.add(userEventBean);
            }
            
             //work with type2: Instructor Feedback
            NodeList childList2 = n.getElementsByTagName("InstructorFeedback");
            //to iterate child elements
            for (int j = 0; j < childList2.getLength(); j++) {
                System.out.println("\nit is the " + j + " th child Feedback\n");
                Element childN = (Element) childList2.item(j);
                NamedNodeMap childAttributeNode = childN.getAttributes();
                for (int k = 0; k < childAttributeNode.getLength(); k++) {
                    Node childNN = childAttributeNode.item(k);
                    System.out.println(childNN.getNodeName() + ": " + childNN.getNodeValue());

                    if (childNN.getNodeName().equals("Tag")) {
                        userEventBean.setParamID(childNN.getNodeValue());
                    }
                    userEventBean.setParamType("Feedback");
   
                    NodeList childListL4 = childN.getElementsByTagName("Feedback");
                    if(childListL4!=null){
                        for(int jj = 0; jj<childListL4.getLength(); jj++){
                            System.out.println("the item "+jj+" found" );
                            System.out.println(childListL4.item(jj).getNodeName() + ": " + childListL4.item(jj).getNodeValue());
                              if(childListL4.item(jj).getNodeName().equals("Feedback"))
                                  //!! the value is not gotten, need to be fixed
                                  userEventBean.setParamValue(childListL4.item(jj).getNodeValue());
                        }
                      
//                        userEventBean.setParamValue(childListL4.item(0).getNodeValue());
                    }else break;
                }
                userEventList.add(userEventBean);
            }
            
            //work with type3: parameterValue
            NodeList childList3 = n.getElementsByTagName("Event");
            //to iterate child elements
            for (int j = 0; j < childList3.getLength(); j++) {
                System.out.println("\nit is the " + j + " th child Event\n");
                Element childN = (Element) childList3.item(j);
                NamedNodeMap childAttributeNode = childN.getAttributes();
                for (int k = 0; k < childAttributeNode.getLength(); k++) {
                    Node childNN = childAttributeNode.item(k);
                    System.out.println(childNN.getNodeName() + ": " + childNN.getNodeValue());

                    if (childNN.getNodeName().equals("EventId")) {
                        String []eventName = childNN.getNodeValue().split("\\.");
                        
                        
                        userEventBean.setParamID(eventName[eventName.length-1]);
                    }
                     if (childNN.getNodeName().equals("NotInScenario")) {
                            userEventBean.setParamValue(childNN.getNodeValue());
                    }
                    if (childNN.getNodeName().equals("AA:type")) {
                         userEventBean.setParamType(childNN.getNodeValue()); 
                    }                   

                }
                userEventList.add(userEventBean);
            }

        }

        return userEventList;
    }

}
