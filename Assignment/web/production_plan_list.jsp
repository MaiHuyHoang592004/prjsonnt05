<%-- 
    Document   : production_plan_list
    Created on : Nov 3, 2024, 10:10:27 PM
    Author     : huyho
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Plan" %>
<!DOCTYPE html>
<html>
<head>
    <title>Production Plan List</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Production Plan List</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Quantity</th>
                    <th>Department ID</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Plan> plans = (List<Plan>) request.getAttribute("plans");
                    if (plans != null && !plans.isEmpty()) {
                        for (Plan plan : plans) {
                %>
                    <tr>
                        <td><%= plan.getPlanID() %></td>
                        <td><%= plan.getPlanName() %></td>
                        <td><%= plan.getStartDate() %></td>
                        <td><%= plan.getEndDate() %></td>
                        <td><%= plan.getQuantity() %></td>
                        <td><%= plan.getDepartmentID() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6" style="text-align: center;">No production plans found.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>