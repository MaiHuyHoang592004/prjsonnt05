<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="attendance.Attendance" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Attendance</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 12px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <div class="container">
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
                            <tr>
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