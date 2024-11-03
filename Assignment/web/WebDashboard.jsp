<%-- 
    Document   : WebDashboard
    Created on : Nov 4, 2024, 12:58:18 AM
    Author     : huyho
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background-color: #f0f0f0; 
            margin: 0; 
            padding: 0; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            min-height: 100vh; 
        }
        .container { 
            max-width: 800px; 
            margin: 50px auto; 
            padding: 20px; 
            background-color: white; 
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); 
            border-radius: 10px; 
            text-align: center; 
        }
        h2 { 
            color: #333; 
            margin-bottom: 20px; 
        }
        h3 { 
            color: #555; 
            margin-bottom: 20px; 
        }
        ul { 
            list-style-type: none; 
            padding: 0; 
        }
        li { 
            padding: 10px; 
            background-color: #4CAF50; 
            color: white; 
            margin-bottom: 10px; 
            border-radius: 5px; 
            transition: background-color 0.3s ease; 
        }
        li:hover { 
            background-color: #45a049; 
        }
        a { 
            color: white; 
            text-decoration: none; 
        }
        .hidden { 
            display: none; 
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= session.getAttribute("username") %></h2>
        <%
            // Create a map to map roleID to role names
            Map<Integer, String> roleMap = new HashMap<>();
            roleMap.put(1, "Production Planner");
            roleMap.put(2, "Manager");
            roleMap.put(3, "Employee");
            roleMap.put(4, "HR Manager");

            // Get the roleID from the session
            Integer roleID = (Integer) session.getAttribute("roleID");
            if (roleID == null) {
                response.sendRedirect("login.jsp"); // Redirect to login page if roleID is null
                return;
            }
            String roleName = roleMap.get(roleID);
        %>
        <h3>Your Role: <%= roleName %></h3>
        <h3>Available Functions:</h3>
        <ul>
            <li class="<%= (roleID == 1 || roleID == 2) ? "" : "hidden" %>">
                <a href="<%= request.getContextPath() %>/productionplan/list">Production Plan List</a>
            </li>
            <li class="<%= (roleID == 2 || roleID == 4) ? "" : "hidden" %>">
                <a href="<%= request.getContextPath() %>/employee/list">Employee List</a>
            </li>
            <li class="<%= (roleID == 2 || roleID == 4) ? "" : "hidden" %>">
                <a href="<%= request.getContextPath() %>/attendance">Attendance</a>
            </li>
            <li class="<%= (roleID == 1 || roleID == 2) ? "" : "hidden" %>">
                <a href="<%= request.getContextPath() %>/schedule">Schedule</a>
            </li>
            <!-- Add more functions as needed -->
        </ul>
    </div>
</body>
</html>