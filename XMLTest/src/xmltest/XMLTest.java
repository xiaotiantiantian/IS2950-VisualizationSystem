/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmltest;

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

/**
 *
 * @author Tian Zhirun
 */
public class XMLTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        String XMLFilePath = "C:\\Users\\Tian Zhirun\\Desktop\\SimMan XML Files\\Healthyadult_Enter your initials_wiser-met-03_0.xml";
        Document document = null;
        List<patientBean> patientList = new ArrayList<patientBean>();

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
        Element root = document.getDocumentElement();
        NodeList list = root.getElementsByTagName("logElement");
        for (int i = 0; i < list.getLength(); i++) {
            System.out.println("\nit is the " + i + " th element\n");
            patientBean pB = new patientBean();

            //通过item()方法找到集合中的节点，并向下转型为Element对象
            Element n = (Element) list.item(i);
            //获取对象中的属性map，用for循环提取并打印
            NamedNodeMap node = n.getAttributes();
            for (int x = 0; x < node.getLength(); x++) {
                Node nn = node.item(x);
                System.out.println(nn.getNodeName() + ": " + nn.getNodeValue());

                if (nn.getNodeName() == "hours") {
                    pB.setHours(nn.getNodeValue());
                }
                if (nn.getNodeName() == "minutes") {
                    pB.setMinutes(nn.getNodeValue());
                }
                if (nn.getNodeName() == "seconds") {
                    pB.setSeconds(nn.getNodeValue());
                }
                if (nn.getNodeName() == "priority") {
                    pB.setPriority(Integer.valueOf(nn.getNodeValue()));
                }
                if (nn.getNodeName() == "timestamp") {
                    pB.setTimestamp(nn.getNodeValue());
                }

            }
            NodeList childList = n.getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                // 若子节点的名称不为#text，则输出，#text为反／标签
                if (childList.item(j).getNodeName().equals("SpO2")) {
                    // 输出节点名称、节点值
                    System.out.println("it is child item :" + j);
                    System.out.println(childList.item(j).getNodeName() + ":"
                            + childList.item(j).getTextContent());
                    pB.setSpO2(Integer.valueOf(childList.item(j).getTextContent()));
                }
                if (childList.item(j).getNodeName().equals("HR")) {
                    // 输出节点名称、节点值
                    System.out.println("it is child item :" + j);
                    System.out.println(childList.item(j).getNodeName() + ":"
                            + childList.item(j).getTextContent());
                    pB.setHR(Integer.valueOf(childList.item(j).getTextContent()));
                }

                if (childList.item(j).getNodeName().equals("BP")) {
//                    NodeList grandchildlist = n.getElementsByTagName("logElement");

////                    
////                    
//                    NodeList grandchildList = childList.item(j).getChildNodes();
////                    
////                    for (int k = 0; k < grandchildList.getLength(); k++) {
//                    if (childList.item(j).getFirstChild().equals("Systolic")) {
//                        System.out.println("it is grandchild item :" + k);
//                        System.out.println(childList.item(k).getNodeName() + ":"
//                                + childList.item(k).getTextContent());
//                        pB.setBPSystolic(Integer.valueOf(childList.item(k).getTextContent()));
//                    }
//                    if (childList.item(j).equals("Diastolic")) {
//                        System.out.println("it is grandchild item :" + k);
//                        System.out.println(childList.item(k).getNodeName() + ":"
//                                + childList.item(k).getTextContent());
//                        pB.setBPSystolic(Integer.valueOf(childList.item(k).getTextContent()));
//                    }
                }

//                    // 输出节点名称、节点值
//                    System.out.println("it is child item :" + j);
//                    System.out.println(childList.item(j).getNodeName() + ":"
//                            + childList.item(j).getTextContent());
//                    pB.setHR(Integer.valueOf(childList.item(j).getTextContent()));
            }
            patientList.add(pB);

        }
        PatientDataDao patientDao = new PatientDataDao();
        patientDao.deleteUserEvent(1);
        for (int i = 0; i < patientList.size(); i++) {
            patientDao.insertTimestampToDB(1, patientList.get(i));
        }
        for (int i = 0; i < patientList.size(); i++) {
            patientDao.updateValuesToDB(1, patientList.get(i));

        }

//            //打印元素内容，代码很纠结，差不多是个固定格式
//            if(n.getElementsByTagName("com")!=null){
//                
////                 System.out.println("com: " +n.getElementsByTagName("com").item(0).getFirstChild().getNodeValue());
//            }
//            System.out.println("author: " + n.getElementsByTagName("author").item(0).getFirstChild().getNodeValue());
        System.out.println();
    }

}

}
