<%-- 
    Document   : WebDashboard
    Created on : Nov 3, 2024, 11:00:17 PM
    Author     : huyho
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { text-align: center; }
        ul { list-style-type: none; padding: 0; }
        li { padding: 8px; background-color: #4CAF50; color: white; margin-bottom: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= session.getAttribute("username") %></h2>
        <h3>Your Accessible Screens:</h3>
        <ul>
            <%
                List<String> accessibleScreens = (List<String>) session.getAttribute("accessibleScreens");
                if (accessibleScreens != null) {
                    for (String screen : accessibleScreens) {
            %>
                        <li><%= screen %></li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</body>
</html>