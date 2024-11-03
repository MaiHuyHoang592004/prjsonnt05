<%-- 
    Document   : WebDashboard
    Created on : Nov 4, 2024, 12:58:18 AM
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
        .hidden { display: none; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= session.getAttribute("username") %></h2>
        <h3>Your Role: <%= session.getAttribute("role") %></h3>
        <h3>Available Functions:</h3>
        <ul>
            <li class="<%= "Admin".equals(session.getAttribute("role")) ? "" : "hidden" %>">Admin Function</li>
            <li class="<%= "Manager".equals(session.getAttribute("role")) ? "" : "hidden" %>">Manager Function</li>
            <li class="<%= "Employee".equals(session.getAttribute("role")) ? "" : "hidden" %>">Employee Function</li>
            <!-- Add more functions as needed -->
        </ul>
    </div>
</body>
</html>