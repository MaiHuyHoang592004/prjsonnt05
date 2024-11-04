<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="attendance.Attendance" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Attendance</title>
    <style>
        :root {
            --primary-color: #4CAF50;
            --primary-color-hover: #45a049;
            --background-gradient: linear-gradient(45deg, #f3ec78, #af4261);
            --container-bg: rgba(255, 255, 255, 0.9);
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
            width: 90%;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: var(--container-bg);
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            animation: fadeIn 1s ease-in-out;
            text-align: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: var(--primary-color);
            color: white;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        .fade-in {
            animation: fadeIn 1s ease-in-out;
        }
        @media (max-width: 768px) {
            .container {
                width: 95%;
                padding: 10px;
            }
            th, td {
                padding: 8px;
            }
        }
    </style>
</head>
<body>
    <div class="container fade-in">
        <h2>Attendance Records</h2>
        <table>
            <thead>
                <tr>
                    <th>Schedule Employee ID</th>
                    <th>Quantity</th>
                    <th>Alpha</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Attendance> attendances = (List<Attendance>) request.getAttribute("attendances");
                    if (attendances != null) {
                        for (Attendance attendance : attendances) {
                %>
                            <tr class="fade-in">
                                <td><%= attendance.getSchEmpID() %></td>
                                <td><%= attendance.getQuantity() %></td>
                                <td><%= attendance.getAlpha() %></td>
                            </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
