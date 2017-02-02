<%-- 
    Document   : showData
    Created on : Jan 29, 2017, 2:29:59 PM
    Author     : Tian Zhirun
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="dataAccessObject.UserInformationDao"%>
<%@page import="dataAccessObject.PatientDataDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.patientBean"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Data</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

        <script type="text/javascript" src="js/d3.js"></script>
        <link rel="stylesheet" type="text/css" href="css/styles.css"/>
        <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="http://getbootstrap.com/examples/justified-nav/justified-nav.css" rel="stylesheet">
        <style>
            .axis path {
                fill: none;
                stroke: #777;
                shape-rendering: crispEdges;
            }
            .axis text {
                font-family: Lato;
                font-size: 13px;
            }
            .legend {
                font-size: 14px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <%
            //get patient heart rate and time informations
            List<patientBean> patientList = new ArrayList<patientBean>();
            PatientDataDao pDataDao = new PatientDataDao();
            UserInformationDao uDao = new UserInformationDao();
            int userid = uDao.retrieveUser(request.getSession().getId());
            if (userid < 0) {
                out.println("<p>error : user not exist!</p>");
            }

            patientList = pDataDao.getUserHRwithTime(userid);
            JSONArray jsonArray = new JSONArray();
            if (patientList != null) {

                for (int i = 0; i < patientList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    //HR means heart rate
                    jsonObject.put("HR", patientList.get(i).getHR());
                    jsonObject.put("time", patientList.get(i).getHours()
                            //                            + ":"
                            + patientList.get(i).getMinutes()
                            //                            + ":"
                            + patientList.get(i).getSeconds());
                    jsonObject.put("userid", userid);

                    jsonArray.put(jsonObject);
                }
            }
            //add another user information
            //maybe an expert, but now we have no access control for expert/normal user, so just select one randomly on database
            patientList = pDataDao.getUserHRwithTime(userid - 1);
            if (patientList != null) {

                for (int i = 0; i < patientList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    //HR means heart rate
                    jsonObject.put("HR", patientList.get(i).getHR());
                    jsonObject.put("time", patientList.get(i).getHours()
                            //                            + ":"
                            + patientList.get(i).getMinutes()
                            //                            + ":"
                            + patientList.get(i).getSeconds());
                    jsonObject.put("userid", userid - 1);

                    jsonArray.put(jsonObject);
                }
            }

        %>

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
                        <li class="active"><a href="showData.jsp">Show Data</a></li>

                        <li><a href="upload.jsp">Upload New Data</a></li>


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
                <h1>Show Data</h1>
            </div>

            <div class="container">

                <div class="jumbotron">

                    <svg id="visualisation" width="1000" height="500"></svg>
                    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>

                    <script>

                        function InitChart() {
    //                        var data = [{"sale":140,"year":"000000","Client":"10"},{"sale":240,"year":"000100","Client":"10"}];

                            var data = <%out.print(jsonArray.toString());%>;



                            var dataGroup = d3.nest()
                                    .key(function (d) {
                                        return d.userid;
                                    })
                                    .entries(data);



                            console.log(JSON.stringify(dataGroup));

                            var color = d3.scale.category10();

                            var vis = d3.select("#visualisation"),
                                    WIDTH = 1000,
                                    HEIGHT = 500,
                                    MARGINS = {
                                        top: 50,
                                        right: 20,
                                        bottom: 50,
                                        left: 50
                                    },
                                    lSpace = WIDTH / dataGroup.length;

                            xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(data, function (d) {
                                    return d.time;
                                }), d3.max(data, function (d) {
                                    return d.time;
                                })]),
                                    yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(data, function (d) {
                                    return d.HR;
                                }), d3.max(data, function (d) {
                                    return d.HR;
                                })]),
                                    xAxis = d3.svg.axis()
                                    .scale(xScale),
                                    yAxis = d3.svg.axis()
                                    .scale(yScale)
                                    .orient("left");





                            vis.append("svg:g")
                                    .attr("class", "x axis")
                                    .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                                    .call(xAxis);

                            vis.append("svg:g")
                                    .attr("class", "y axis")
                                    .attr("transform", "translate(" + (MARGINS.left) + ",0)")
                                    .call(yAxis);

                            var lineGen = d3.svg.line()
                                    .x(function (d) {
                                        return xScale(d.time);
                                    })
                                    .y(function (d) {
                                        return yScale(d.HR);
                                    })
                                    .interpolate("basis");

                            dataGroup.forEach(function (d, i) {
                                vis.append('svg:path')
                                        .attr('d', lineGen(d.values))
                                        .attr('stroke', function (d, j) {
                                            return "hsl(" + Math.random() * 360 + ",100%,50%)";
                                        })
                                        .attr('stroke-width', 2)
                                        .attr('id', 'line_' + d.key)
                                        .attr('fill', 'none');


                                vis.append("text")
                                        .attr("x", (lSpace / 2) + i * lSpace)
                                        .attr("y", HEIGHT)
                                        .style("fill", "black")
                                        .attr("class", "legend")
                                        .on('click', function () {
                                            var active = d.active ? false : true;
                                            var opacity = active ? 0 : 1;

                                            d3.select("#line_" + d.key).style("opacity", opacity);

                                            d.active = active;
                                        })
                                        .text(d.key);
                            });



                        }



                        InitChart();
                    </script>
                </div>

            </div>




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
        <script src="js/formCheck.js" type="text/javascript"></script>
        <!--<script src="js/formQuestionnaireEditAdd.js?ver=2" type="text/javascript"></script>-->
    </body>


</html>
