<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="schedule.Schedule" %>
<%@ page import="schedule.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Schedules</title>
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
        <h2>Schedule List</h2>
        <table>
            <thead>
                <tr>
                    <th>Schedule ID</th>
                    <th>Plan Campaign ID</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Quantity</th>
                    <th>Employees</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Schedule> schedules = (List<Schedule>) request.getAttribute("schedules");
                    if (schedules != null) {
                        for (Schedule schedule : schedules) {
                %>
                            <tr>
                                <td><%= schedule.getScID() %></td>
                                <td><%= schedule.getPlanCampnID() %></td>
                                <td><%= schedule.getDate() %></td>
                                <td><%= schedule.getShift() %></td>
                                <td><%= schedule.getQuantity() %></td>
                                <td>
                                    <%
                                        List<Employee> employees = schedule.getEmployees();
                                        if (employees != null) {
                                            for (Employee employee : employees) {
                                                out.print(employee.getEmployeeName() + "<br>");
                                            }
                                        }
                                    %>
                                </td>
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