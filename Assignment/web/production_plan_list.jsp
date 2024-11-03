<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Plan" %>
<!DOCTYPE html>
<html>
<head>
    <title>Production Plan List</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .add-button { text-align: center; margin-bottom: 20px; }
        .add-button a { padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; }
        .add-button a:hover { background-color: #45a049; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 12px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .delete-button { padding: 5px 10px; background-color: #f44336; color: white; border: none; border-radius: 5px; cursor: pointer; }
        .delete-button:hover { background-color: #e53935; }
    </style>
    <script>
        function confirmDelete(planID) {
            if (confirm("Are you sure you want to delete this plan?")) {
                document.getElementById('deleteForm-' + planID).submit();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Production Plan List</h2>
        <div class="add-button">
            <a href="<%= request.getContextPath() %>/productionplan/create">Create New Plan</a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Plan ID</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Quantity</th>
                    <th>Department ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Plan> plans = (List<Plan>) request.getAttribute("plans");
                    if (plans != null) {
                        for (Plan plan : plans) {
                %>
                            <tr>
                                <td><%= plan.getPlanID() %></td>
                                <td><%= plan.getPlanName() %></td>
                                <td><%= plan.getStartDate() %></td>
                                <td><%= plan.getEndDate() %></td>
                                <td><%= plan.getQuantity() %></td>
                                <td><%= plan.getDepartmentID() %></td>
                                <td>
                                    <form id="deleteForm-<%= plan.getPlanID() %>" action="<%= request.getContextPath() %>/productionplan/delete" method="post" style="display:inline;">
                                        <input type="hidden" name="planID" value="<%= plan.getPlanID() %>">
                                        <button type="button" class="delete-button" onclick="confirmDelete(<%= plan.getPlanID() %>)">Delete</button>
                                    </form>
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