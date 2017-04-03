<%-- 
    Document   : upload
    Created on : 2017-01-22, 19:56:50
    Author     : Zhirun Tian
--%>

<%@page import="model.SimulationBean"%>
<%@page import="model.UserBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dataAccessObject.SimulationDao"%>
<%@page import="dataAccessObject.UserInfoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    //check whether the role ID of the user has priviledge for current page
//    if (request.getSession().getAttribute("userName") == null) {
//        response.sendRedirect("login.jsp");
//       request.getSession().setAttribute("userName") request.getSession().getId()
//    }
%>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload New Data</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    </head>
    <!--<script src="js/formQuestionnaireEditAdd.js?ver=2" type="text/javascript"></script>-->
    <body>

        <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Independent Study</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="showData2.jsp">Show Data</a></li>

                    <li class="active"><a href="upload.jsp">Upload New Data</a></li>


                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <!--put the name on the navigation bar-->
                    <br>
                    <%                            if (request.getSession().getAttribute("userName") != null) {
                            String username = (String) session.getAttribute("userName");
                            out.print("<a href='ShowUserInfo.jsp'>" + username + "</a>");
                            out.print("&nbsp;&nbsp;|&nbsp;&nbsp;");
                            out.print("<a href='LogOut'>Log Out</a>");
                        } else {
                            out.print("Guest");
                        }
                    %>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>


    <!-- Begin page content -->
    <div class="container" style="margin-top:100px;margin-bottom:250px;">

        <div class="page-header">
            <h1>Upload New Data File</h1>
        </div>
        <form action="Upload" method="post" class="form-horizontal" enctype="multipart/form-data">
            <!--role="form"--> 
            <div class="form-group">
                <div style="position:relative;">
                    <a class='btn btn-default' href='javascript:;'>
                        Choose File
                        <input type="file" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' name="file_source" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
                    </a>
                    &nbsp;
                    <span class='label label-info' id="upload-file-info"></span>
                </div>
                <span class='label label-info' id="upload-file-info"></span>
            </div>
            <div class="form-group">

                <label for="Expertise Level">Expertise Level</label>
                <select name="roleChoice" class="form-control">
                    <option value="1">Student</option>
                    <option value ="2">Resident</option>
                    <option value ="3">Clinician</option>
                </select>
            </div>

            <div class="form-group">

                <label for="XML File Version">XML File Version</label>
                <select name="XMLVersion" class="form-control">
                    <!--there is some incompatibility of new and old version xml, now I change it to work with new version-->
                    <!--<option value ="1">Old Version</option>-->
                    <option value ="2">New Version</option>

                </select>
            </div>


            <div class="form-group">

                <label for="Simulation ID">Simulation ID</label>
                <select name ="simulationChoice" class ="form-control"> 

                    <%
                        SimulationDao simulationDao = new SimulationDao();
                        List<SimulationBean> simulationBeanList = new ArrayList<SimulationBean>();
                        simulationBeanList = simulationDao.getAllSimulations();
                        for (int i = 0; i < simulationBeanList.size(); i++) {%> 
                    <option value=<%=simulationBeanList.get(i).getSimID()%>><%=simulationBeanList.get(i).getTitle()%></option> 
                    <%}%> 
                </select>
            </div>

            <div class="form-group">

                <label for="User ID">User Name</label>
                <select name ="userChoice" class ="form-control"> 

                    <%
                        UserInfoDao userInfoDao = new UserInfoDao();
                        List<UserBean> userBeanList = new ArrayList<UserBean>();
                        userBeanList = userInfoDao.getAllUser();
                        for (int i = 0; i < userBeanList.size(); i++) {%> 
                    <option value=<%=userBeanList.get(i).getUserID()%>><%=userBeanList.get(i).getUserName()%></option> 
                    <%}%> 
                </select>
            </div>   

            <div class="form-group">
                <label for="grade">Grade:</label>
                <input name ='grade' type="text" class="form-control" id="grade">
            </div>
            <div class="form-group">
                <button type="submit" value="Upload" class="btn btn-default">Upload</button>
            </div>
        </form>


    </div><!--container-->

    <footer class="footer">
        <div class="container">
            <p class="text-muted">&copy; 2017 Independent Study &middot; <a href="#">Privacy</a>
                &middot; <a href="#">Terms</a></p>
        </div>
    </footer>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
<!--    <script src="js/formCheck.js" type="text/javascript"></script>-->
    <!--<script src="js/formQuestionnaireEditAdd.js?ver=2" type="text/javascript"></script>-->
</body>
</html>