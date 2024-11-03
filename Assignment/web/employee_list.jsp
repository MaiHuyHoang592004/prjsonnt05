<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
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
        <h2>Employee List</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Address</th>
                    <th>Date of Birth</th>
                    <th>Role ID</th>
                    <th>Department ID</th>
                    <th>Salary</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                    if (employees != null) {
                        for (Employee employee : employees) {
                %>
                    <tr>
                        <td><%= employee.getEmployeeID() %></td>
                        <td><%= employee.getEmployeeName() %></td>
                        <td><%= employee.isGender() ? "Male" : "Female" %></td>
                        <td><%= employee.getAddress() %></td>
                        <td><%= employee.getDob() %></td>
                        <td><%= employee.getRoleID() %></td>
                        <td><%= employee.getDepartmentID() %></td>
                        <td><%= employee.getSalary() %></td>
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