<%-- 
    Document   : showData2
    Created on : Mar 12, 2017, 6:57:01 PM
    Author     : Tian Zhirun
--%>

<%@page import="dataAccessObject.DecisionNodeDao"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="model.DecisionNode"%>
<%@page import="dataAccessObject.DecisionTimeDao"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="model.UserEventBean"%>
<%@page import="dataAccessObject.UserEventDao"%>
<%@page import="model.SimulationBean"%>
<%@page import="model.UserBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dataAccessObject.SimulationDao"%>
<%@page import="dataAccessObject.UserInfoDao"%>
<%@page import="controller.GetDecisionNode"%>
<%@page import="controller.TreeHelper"%>
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
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>-->
<script src="js/jquery-3.2.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script src="js/json2.js"></script>
<script src="js/decisionTree.js?ver<%=LocalDateTime.now()%>"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" href="css/showData2_d3.css" />

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Data</title>
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
                    <li class="active"><a href="showData2.jsp">Show Data</a></li>

                    <li><a href="upload.jsp">Upload New Data</a></li>


                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <!--put the name on the navigation bar-->
                    <br>
                    <%
                        if (request.getSession().getAttribute("userName") != null) {
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
    <%
        //create json here:
//        int logID = (Integer) request.getSession().getAttribute("logID");
        int logID = 102;//for test only
        GetDecisionNode getNode = new GetDecisionNode();
        DecisionNodeDao dnDao = new DecisionNodeDao();
        dnDao.InsertEventIntoDecisionNodeDB(logID);
        List<DecisionNode> decisionList =dnDao.GetDecisionWithAssignedParents(logID);
        
//        List<DecisionNode> decisionList = getNode.GetEventsWithParentInfo(logID);
        for (int i = 0; i < decisionList.size(); i++) {
            DecisionNode dN = decisionList.get(i);
            System.out.println("EventID:" + dN.getEventID() + " sequence:" + dN.getSequenceNum() + " eventName:" + dN.getDecisionName() + " parentID:" + dN.getParentID());
        }
        String Json = getNode.getJsonByNodeList(decisionList);
        System.out.println(Json);
    %>

    <script>
           var treeData = <%=Json.toString()%>;
           initialData(treeData);
    </script>
    <!-- Begin page content -->
    <div class="container" style="margin-top:100px;margin-bottom:250px;">

        <div class="page-header">
            <h1>Show Data</h1>
        </div>
        <!--<svg id="visualisation" class="pos_HR" width="800" height="200"></svg>-->
        <div id="visualisation" class="treeGraph" align="center"></div>

    </div><!--container-->

    <footer class="footer">
        <div class="container">
            <p class="text-muted">&copy; 2017 Independent Study &middot; <a href="#">Privacy</a>
                &middot; <a href="#">Terms</a></p>
        </div>
    </footer>
    <style>

    </style>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>-->
    <!-- Latest compiled and minified JavaScript -->

</body>
</html>