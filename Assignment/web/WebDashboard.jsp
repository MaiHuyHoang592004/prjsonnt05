<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <title>Web Dashboard</title>
    <style>
        :root {
            --primary-color: #4CAF50;
            --primary-color-hover: #45a049;
            --text-color: #333;
            --background-gradient: linear-gradient(45deg, #f3ec78, #af4261);
            --container-bg: rgba(255, 255, 255, 0.9);
        }
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 0; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            min-height: 100vh; 
            background: var(--background-gradient);
            animation: gradient 15s ease infinite;
            background-size: 400% 400%;
        }
        @keyframes gradient {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
        .container { 
            max-width: 800px; 
            padding: 20px; 
            background-color: var(--container-bg); 
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); 
            border-radius: 10px; 
            text-align: center; 
            animation: fadeIn 1s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        h2, h3 { 
            color: var(--text-color); 
            margin-bottom: 20px; 
        }
        ul { 
            list-style-type: none; 
            padding: 0; 
        }
        li { 
            padding: 10px; 
            background-color: var(--primary-color); 
            color: white; 
            margin-bottom: 10px; 
            border-radius: 5px; 
            transition: background-color 0.3s ease, transform 0.3s ease;
            cursor: pointer;
        }
        li:hover { 
            background-color: var(--primary-color-hover); 
            transform: scale(1.05);
        }
        a { 
            color: white; 
            text-decoration: none; 
            display: block; 
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
            <li class="<%= (roleID == 1 || roleID == 2) ? "" : "hidden" %>">
                <a href="<%= request.getContextPath() %>/schedule/list">Schedule List</a>
            </li>
        </ul>
        <ul>
            <li><a href="<%= request.getContextPath() %>/viewAttendance">View Attendance</a></li>
            <li><a href="<%= request.getContextPath() %>/schedule/list">Schedule List</a></li>
            <!-- Add other quick links as needed -->
        </ul>
    </div>
</body>
</html>
