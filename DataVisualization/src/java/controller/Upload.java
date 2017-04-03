/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccessObject.DecisionTimeDao;
import dataAccessObject.UserEventDao;
import dataAccessObject.UserInfoDao;
import dataAccessObject.UserLogDao;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DecisionTimeBean;
import model.UserEventBean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Zhirun Tian
 */
//@WebServlet(name = "Upload", urlPatterns = {"/Upload"})
public class Upload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
    private static final String DATA_DIRECTORY = "XMLFiles";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 20;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 20;

    /**
     * Servlet implementation class UploadServlet
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String filePathAll = "";
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        //if the folder to store xml files not exist, we should build a new dictionary;
        String path = getServletContext().getRealPath("/");
        System.out.println(path);
        File XMLDirectory = new File(path + DATA_DIRECTORY);
        //if there already have the directory, mkdir would do nothing
        XMLDirectory.mkdir();

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);
        String fileName = "", newname = "";

        int expertiseLevel = 1;
        int simulationID = 1;
        int grade = -1;
        int xmlVersion = -1;
        int userid = -1;

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    //fileName = (String)session.getId() + new File(item.getName()).getName();
                    //String filePath = uploadFolder + File.separator + fileName;
                    fileName = new File(item.getName()).getName();
                    newname = (String) session.getId() + fileName.substring(fileName.lastIndexOf("."));
                    String filePath = uploadFolder + File.separator + newname;
                    filePathAll = filePath;
                    File uploadedFile = new File(filePath);
                    System.out.println(filePath);
// saves the file to upload directory
                    item.write(uploadedFile);
                } else {
                    String name = item.getFieldName();
                    //make chinese input correctly shown
                    String value = item.getString("UTF-8");
                    String value1 = new String(name.getBytes("iso8859-1"), "UTF-8");
                    System.out.println(name + "  " + value);
                    System.out.println(name + "  " + value1);
                    if (name.equals("roleChoice")) {
                        expertiseLevel = Integer.parseInt(value);
                    }
                    if (name.equals("simulationChoice")) {
                        simulationID = Integer.parseInt(value);
                    }

                    if (name.equals("userChoice")) {
                        userid = Integer.parseInt(value);
                        UserInfoDao userInfoDao = new UserInfoDao();
                        String userName = userInfoDao.getUserByID(userid);

                        session.setAttribute("userName", userName);
                        session.setAttribute("userID", userid);

                    }
                    if (name.equals("grade")) {
                        grade = Integer.parseInt(value);
                    }
                    if (name.equals("XMLVersion")) {
                        xmlVersion = Integer.parseInt(value);
                    }

                }
            }
        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        //add a function to read xml file to database
        //read the old version xml file
        try {

            List<UserEventBean> userEventList;
//            if (xmlVersion == 1) {

//                XMLReader xmlreader = new XMLReader(filePathAll);
//
//                //write it into log
//                UserLogDao userLogDao = new UserLogDao();
//                userLogDao.insertLog(userid, simulationID, newname, expertiseLevel, grade);
//
//                //!!**if the folder not exist ,there would have error ( could not find the file)
//                 patientList = xmlreader.readXMLToPatientBeans();
//            } else /*if(xmlVersion ==2)*/{
            XMLReaderNew xmlreaderNew = new XMLReaderNew(filePathAll);
            //write it into log
            UserLogDao userLogDao = new UserLogDao();
            int logID = userLogDao.insertLog(userid, simulationID, newname, expertiseLevel, grade);

            session.setAttribute("logID", logID);
            xmlreaderNew.setLogID(logID);
            //!!**if the folder not exist ,there would have error ( could not find the file)
            userEventList = xmlreaderNew.readXMLToUserEventBeans();

//            }
            UserEventDao userEventDao = new UserEventDao();
            int sequenceNum = 0;
            int msec = 0;
            int decisionTimeDelta = 0;

            DecisionTimeDao decisionTimeDao = new DecisionTimeDao();

            for (int i = 0; i < userEventList.size(); i++) {

                //if ParamType == EventType, it is a event, a node of the tree
                if (userEventList.get(i).getParamType().equals("EventType")) {

                    if (sequenceNum == 0 || (userEventList.get(i).getMsec() - msec) != 0) {
                        sequenceNum++;
                        decisionTimeDelta = userEventList.get(i).getMsec() - msec;
                        DecisionTimeBean decisionTimeBean = new DecisionTimeBean(expertiseLevel, logID, sequenceNum, decisionTimeDelta);
                        decisionTimeDao.InserDecisionTimeIntoDB(decisionTimeBean);
                    }
                    msec = userEventList.get(i).getMsec();

                    System.out.println("InsertEvent: Event " + i + " EventName:" + userEventList.get(i).getParamID() + " Sequence: " + sequenceNum + " DecisionTimeDelta:" + decisionTimeDelta);

                }
                userEventDao.InsertEventIntoDB(userEventList.get(i), sequenceNum, decisionTimeDelta);
            }
        } catch (SQLException e) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (xmlVersion == 1) {
            getServletContext().getRequestDispatcher("/showData.jsp").forward(
                    request, response);
        } else {
            getServletContext().getRequestDispatcher("/showData2.jsp").forward(
                    request, response);
            
//              getServletContext().getRequestDispatcher("/done.jsp").forward(
//                    request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
