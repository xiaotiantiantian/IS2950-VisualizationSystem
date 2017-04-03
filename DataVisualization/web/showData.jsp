<%-- 
    Document   : showData
    Created on : Jan 29, 2017, 2:29:59 PM
    Author     : Tian Zhirun
--%>

<%@page import="java.lang.Integer"%>
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

            //deal with HR first
//            patientList = pDataDao.getUserHRwithTime(userid);
            JSONArray jsonArrayHR = new JSONArray();
            patientList = pDataDao.getAllEventFromDB();
            if (patientList != null) {
                for (int i = 0; i < patientList.size(); i++) {
                    if (patientList.get(i).getHR() != -1) {
                        JSONObject jsonObject = new JSONObject();
                        //HR means heart rate
                        jsonObject.put("HR", patientList.get(i).getHR());
//                        jsonObject.put("time", patientList.get(i).getHours()
//                                //                            + ":"
//                                + patientList.get(i).getMinutes()
//                                //                            + ":"
//                                + patientList.get(i).getSeconds());
//change time to timestamp by seconds
//                        jsonObject.put("time", (Integer.parseInt(patientList.get(i).getHours()) * 24 + Integer.parseInt(patientList.get(i).getMinutes())) * 60 + Integer.parseInt(patientList.get(i).getSeconds()));
                       jsonObject.put("time", patientList.get(i).getMsec()/1000);
                        jsonObject.put("userid", patientList.get(i).getUserID());

                        jsonArrayHR.put(jsonObject);
                    }
                }
            }
            //get spo2 array
            JSONArray jsonArraySpO2 = new JSONArray();
            patientList = pDataDao.getAllEventFromDB();
            if (patientList != null) {
                for (int i = 0; i < patientList.size(); i++) {
                    if (patientList.get(i).getSpO2() != -1) {
                        JSONObject jsonObject = new JSONObject();
                        //HR means heart rate
                        jsonObject.put("SpO2", patientList.get(i).getSpO2());
//                        jsonObject.put("time", patientList.get(i).getHours()
//                                //                            + ":"
//                                + patientList.get(i).getMinutes()
//                                //                            + ":"
//                                + patientList.get(i).getSeconds());
                        jsonObject.put("time", (Integer.parseInt(patientList.get(i).getHours()) * 24 + Integer.parseInt(patientList.get(i).getMinutes())) * 60 + Integer.parseInt(patientList.get(i).getSeconds()));
                        jsonObject.put("userid", patientList.get(i).getUserID());

                        jsonArraySpO2.put(jsonObject);
                    }
                }
            }
            //get BPSystolic array
            JSONArray jsonArrayBPSystolic = new JSONArray();
            patientList = pDataDao.getAllEventFromDB();
            if (patientList != null) {
                for (int i = 0; i < patientList.size(); i++) {
                    if (patientList.get(i).getBPSystolic() != -1) {
                        JSONObject jsonObject = new JSONObject();
                        //HR means heart rate
                        jsonObject.put("BPSystolic", patientList.get(i).getBPSystolic());
//                        jsonObject.put("time", patientList.get(i).getHours()
//                                //                            + ":"
//                                + patientList.get(i).getMinutes()
//                                //                            + ":"
//                                + patientList.get(i).getSeconds());
                        jsonObject.put("time", (Integer.parseInt(patientList.get(i).getHours()) * 24 + Integer.parseInt(patientList.get(i).getMinutes())) * 60 + Integer.parseInt(patientList.get(i).getSeconds()));
                        jsonObject.put("userid", patientList.get(i).getUserID());

                        jsonArrayBPSystolic.put(jsonObject);
                    }
                }
            }
            //get bpdiastolic
            JSONArray jsonArrayBPDiastolic = new JSONArray();
            patientList = pDataDao.getAllEventFromDB();
            if (patientList != null) {
                for (int i = 0; i < patientList.size(); i++) {
                    if (patientList.get(i).getBPDiastolic() != -1) {
                        JSONObject jsonObject = new JSONObject();
                        //HR means heart rate
                        jsonObject.put("BPDiastolic", patientList.get(i).getBPDiastolic());
//                        jsonObject.put("time", patientList.get(i).getHours()
//                                //                            + ":"
//                                + patientList.get(i).getMinutes()
//                                //                            + ":"
//                                + patientList.get(i).getSeconds());
//var timestamp = 60*(24*Integer.parseInt(patientList.get(i).getHours())+ patientList.get(i).getMinutes())+ patientList.get(i).getSeconds();
                        jsonObject.put("time", (Integer.parseInt(patientList.get(i).getHours()) * 24 + Integer.parseInt(patientList.get(i).getMinutes())) * 60 + Integer.parseInt(patientList.get(i).getSeconds()));
                        jsonObject.put("userid", patientList.get(i).getUserID());
                        jsonArrayBPDiastolic.put(jsonObject);
                    }
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

            <!--<div class="container">-->
                <!--<h1>Show Heart Rate Data</h1>-->
                <!--<div class="jumbotron">-->

                    <svg id="visualisationHR" class="pos_HR" width="1000" height="200"></svg>
                    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>


                    <script>

                        var color_i = 1;    //a variable to make different line has different color

                        var dataHR = <%out.print(jsonArrayHR.toString());%>;
                        var dataSpO2 = <%out.print(jsonArraySpO2.toString());%>;
                        var dataBPDiastolic = <%out.print(jsonArrayBPDiastolic.toString());%>;
                        var dataBPSystolic = <%out.print(jsonArrayBPSystolic.toString());%>;
                        function InitChartHR(data) {
//data of heart rate
                            var dataGroup = d3.nest()
                                    .key(function (d) {
                                        return d.userid;
                                    })
                                    .entries(data);

                            console.log(JSON.stringify(dataGroup));

                            var color = d3.scale.category10();

                            var vis = d3.select("#visualisationHR"),
                                    WIDTH = 1000,
                                    HEIGHT = 200,
                                    MARGINS = {
                                           top: 5,
                                        right:20 ,
                                        bottom: 5,
                                        left: 30
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
                                    .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                                    .attr("class", "x axis")
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
                                var id_color = function (d) {
                                    return d.userid;
                                };
                                vis.append('svg:path')
                                        .attr('d', lineGen(d.values))
                                        .attr('stroke', function (d, j) {
                                            return "hsl(" + (++color_i) * 100 % 360 + ",100%,50%)";
                                        })
                                        .attr('stroke-width', 2)
                                        .attr('id', 'line_' + d.key)
                                        .attr('fill', 'none');
//
//                                vis.append("text")
//                                        .attr("x", (lSpace / 2) + i * lSpace)
//                                        .attr("y", HEIGHT)
//                                        .style("fill", "black")
//                                        .attr("class", "legend")
//                                        .on('click', function () {
//                                            var active = d.active ? false : true;
//                                            var opacity = active ? 0 : 1;
//
//                                            d3.select("#line_" + d.key).style("opacity", opacity);
//
//                                            d.active = active;
//                                        })
//                                        .text(d.key);
                            });

                        }
                        InitChartHR(dataHR);
                    </script>
                <!--</div>-->
            <!--</div>-->
            <!--<div class="container">-->
<!--                <h1>Show SpO2 Data</h1>-->
                <!--<div class="jumbotron">-->
                    <svg id="visualisationSpO2" class="pos_SpO2" width="1000" height="200"></svg>
                    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>

                    <script>
                        var dataSpO2 = <%out.print(jsonArraySpO2.toString());%>;
                        color_i = 1;
                        function InitChartSpO2(data) {
//data of heart rate
                            var dataGroup = d3.nest()
                                    .key(function (d) {
                                        return d.userid;
                                    })
                                    .entries(data);

                            console.log(JSON.stringify(dataGroup));

                            var color = d3.scale.category10();

                            var vis = d3.select("#visualisationSpO2"),
                                    WIDTH = 1000,
                                    HEIGHT = 200,
                                    MARGINS = {
//                                        top: 50,
//                                        right: 20,
//                                        bottom: 50,
//                                        left: 50
                                           top: 5,
                                        right:20 ,
                                        bottom: 5,
                                        left: 30
                                    },
                                    lSpace = WIDTH / dataGroup.length;

                            xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(data, function (d) {
                                    return d.time;
                                }), d3.max(data, function (d) {
                                    return d.time;
                                })]),
                                    yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(data, function (d) {
                                    return d.SpO2;
                                }), d3.max(data, function (d) {
                                    return d.SpO2;
                                })]),
                                    xAxis = d3.svg.axis()
                                    .scale(xScale),
                                    yAxis = d3.svg.axis()
                                    .scale(yScale)
                                    .orient("left");

                            vis.append("svg:g")
                                    .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                                    .attr("class", "x axis")
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
                                        return yScale(d.SpO2);
                                    })
                                    .interpolate("basis");

                            dataGroup.forEach(function (d, i) {
                                vis.append('svg:path')
                                        .attr('d', lineGen(d.values))
                                        .attr('stroke', function (d, j) {
                                            return "hsl(" + (++color_i) * 100 % 360 + ",100%,50%)";
                                        })
                                        .attr('stroke-width', 2)
                                        .attr('id', 'line_' + d.key)
                                        .attr('fill', 'none');

//                                vis.append("text")
//                                        .attr("x", (lSpace / 2) + i * lSpace)
//                                        .attr("y", HEIGHT)
//                                        .style("fill", "black")
//                                        .attr("class", "legend")
//                                        .on('click', function () {
//                                            var active = d.active ? false : true;
//                                            var opacity = active ? 0 : 1;
//
//                                            d3.select("#line_" + d.key).style("opacity", opacity);
//
//                                            d.active = active;
//                                        })
//                                        .text(d.key);
                            });

                        }
                        InitChartSpO2(dataSpO2);
                    </script>
                <!--</div>-->

            <!--</div>-->
            <!--<div class="container">-->
                <!--<h1>Show BPDiastolic Data</h1>-->
                <!--<div class="jumbotron">-->
                    <svg id="visualisationBPDiastolic" class="pos_BPDiastolic" width="1000" height="200"></svg>
                    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>

                    <script>
                        var dataBPDiastolic = <%out.print(jsonArrayBPDiastolic.toString());%>;
                        color_i = 1;
                        function InitChartBPDiastolic(data) {
//data of heart rate
                            var dataGroup = d3.nest()
                                    .key(function (d) {
                                        return d.userid;
                                    })
                                    .entries(data);

                            console.log(JSON.stringify(dataGroup));

                            var color = d3.scale.category10();

                            var vis = d3.select("#visualisationBPDiastolic"),
                                    WIDTH = 1000,
                                    HEIGHT = 200,
                                    MARGINS = {
                                          top: 5,
                                        right:20 ,
                                        bottom: 5,
                                        left: 30
                                    },
                                    lSpace = WIDTH / dataGroup.length;

                            xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(data, function (d) {
                                    return d.time;
                                }), d3.max(data, function (d) {
                                    return d.time;
                                })]),
                                    yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(data, function (d) {
                                    return d.BPDiastolic;
                                }), d3.max(data, function (d) {
                                    return d.BPDiastolic;
                                })]),
                                    xAxis = d3.svg.axis()
                                    .scale(xScale),
                                    yAxis = d3.svg.axis()
                                    .scale(yScale)
                                    .orient("left");

                            vis.append("svg:g")
                                    .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                                    .attr("class", "x axis")
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
                                        return yScale(d.BPDiastolic);
                                    })
                                    .interpolate("basis");

                            dataGroup.forEach(function (d, i) {
                                vis.append('svg:path')
                                        .attr('d', lineGen(d.values))
                                        .attr('stroke', function (d, j) {
                                            return "hsl(" + (++color_i) * 100 % 360 + ",100%,50%)";
                                        })
                                        .attr('stroke-width', 2)
                                        .attr('id', 'line_' + d.key)
                                        .attr('fill', 'none');

//                                vis.append("text")
//                                        .attr("x", (lSpace / 2) + i * lSpace)
//                                        .attr("y", HEIGHT)
//                                        .style("fill", "black")
//                                        .attr("class", "legend")
//                                        .on('click', function () {
//                                            var active = d.active ? false : true;
//                                            var opacity = active ? 0 : 1;
//
//                                            d3.select("#line_" + d.key).style("opacity", opacity);
//
//                                            d.active = active;
//                                        })
//                                        .text(d.key);
                            });

                        }
                        InitChartBPDiastolic(dataBPDiastolic);
                    </script>
                <!--</div>-->

            <!--</div>-->
            <!--<div class="container">-->
                <!--<h1>Show BPSystolic Data</h1>-->
                <!--<div class="jumbotron">-->
                    <svg id="visualisationBPSystolic" class="pos_BPSystolic" width="1000" height="200"></svg>
                    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>

                    <script>
                        var dataBPSystolic = <%out.print(jsonArrayBPSystolic.toString());%>;
                        color_i = 1;

                        function InitChartBPSystolic(data) {
//data of heart rate
                            var dataGroup = d3.nest()
                                    .key(function (d) {
                                        return d.userid;
                                    })
                                    .entries(data);

                            console.log(JSON.stringify(dataGroup));

                            var color = d3.scale.category10();

                            var vis = d3.select("#visualisationBPSystolic"),
                                    WIDTH = 1000,
                                    HEIGHT = 200,
                                    MARGINS = {
                                        top: 5,
                                        right:20 ,
                                        bottom: 5,
                                        left: 30
                                    },
                                    lSpace = WIDTH / dataGroup.length;

                            xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(data, function (d) {
                                    return d.time;
                                }), d3.max(data, function (d) {
                                    return d.time;
                                })]),
                                    yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(data, function (d) {
                                    return d.BPSystolic;
                                }), d3.max(data, function (d) {
                                    return d.BPSystolic;
                                })]),
                                    xAxis = d3.svg.axis()
                                    .scale(xScale),
                                    yAxis = d3.svg.axis()
                                    .scale(yScale)
                                    .orient("left");

                            vis.append("svg:g")
                                    .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                                    .attr("class", "x axis")
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
                                        return yScale(d.BPSystolic);
                                    })
                                    .interpolate("basis");

                            dataGroup.forEach(function (d, i) {
                                vis.append('svg:path')
                                        .attr('d', lineGen(d.values))
                                        .attr('stroke', function (d, j) {
                                            return "hsl(" + (++color_i) * 100 % 360 + ",100%,50%)";
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
                        InitChartBPSystolic(dataBPSystolic);
                    </script>
                <!--</div>-->

            <!--</div>-->
<!-- <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy; 2017 Independent Study &middot; <a href="#">Privacy</a>
                    &middot; <a href="#">Terms</a></p>
            </div>
        </footer>-->

        </div><!--container-->

        <p> </p>
<!--        <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy; 2017 Independent Study &middot; <a href="#">Privacy</a>
                    &middot; <a href="#">Terms</a></p>
            </div>
        </footer>-->


        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
<!--        <script src="js/formCheck.js" type="text/javascript"></script>-->
        <!--<script src="js/formQuestionnaireEditAdd.js?ver=2" type="text/javascript"></script>-->
    </body>


</html>
