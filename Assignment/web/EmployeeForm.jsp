<%-- 
    Document   : EmployeeForm
    Created on : Nov 4, 2024, 1:16:34 AM
    Author     : huyho
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Form</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 600px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        form { display: flex; flex-direction: column; }
        label { margin-bottom: 5px; }
        input, select { margin-bottom: 10px; padding: 10px; font-size: 16px; }
        button { padding: 10px; font-size: 16px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Employee Form</h2>
        <form action="create" method="post">
            <label for="employeeName">Name:</label>
            <input type="text" id="employeeName" name="employeeName" required>
            <label for="gender">Gender:</label>
            <select id="gender" name="gender">
                <option value="true">Male</option>
                <option value="false">Female</option>
            </select>
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required>
            <label for="dob">Date of Birth:</label>
            <input type="date" id="dob" name="dob" required>
            <label for="roleID">Role:</label>
            <select id="roleID" name="roleID">
                <!-- Loop through the list of roles and display them -->
                <%
                    List<Role> roles = (List<Role>) request.getAttribute("roles");
                    if (roles != null) {
                        for (Role role : roles) {
                %>
                            <option value="<%= role.getRoleID() %>"><%= role.getRoleName() %></option>
                <%
                        }
                    }
                %>
            </select>
            <label for="departmentID">Department:</label>
            <select id="departmentID" name="departmentID">
                <!-- Loop through the list of departments and display them -->
                <%
                    List<Department> departments = (List<Department>) request.getAttribute("departments");
                    if (departments != null) {
                        for (Department department : departments) {
                %>
                            <option value="<%= department.getDepartmentID() %>"><%= department.getDepartmentName() %></option>
                <%
                        }
                    }
                %>
            </select>
            <label for="salary">Salary:</label>
            <input type="number" id="salary" name="salary" required>
            <button type="submit">Save</button>
        </form>
    </div>
</body>
</html>