<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <style>
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            background-color: #f0f0f0; 
            margin: 0; 
            padding: 0; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            min-height: 100vh; 
        }
        .container { 
            width: 90%; 
            max-width: 1200px; 
            margin: 50px auto; 
            padding: 20px; 
            background-color: white; 
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); 
            border-radius: 10px; 
            animation: fadeIn 1s ease-in-out; 
        }
        h2 { 
            text-align: center; 
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
            background-color: #4CAF50; 
            color: white; 
        }
        .add-button, .search-bar { 
            margin-bottom: 20px; 
            text-align: right; 
        }
        .add-button a, .search-bar button { 
            padding: 10px 20px; 
            background-color: #4CAF50; 
            color: white; 
            text-decoration: none; 
            border-radius: 5px; 
            border: none; 
            cursor: pointer; 
            transition: background-color 0.3s ease; 
        }
        .add-button a:hover, .search-bar button:hover { 
            background-color: #45a049; 
        }
        .search-bar input, .search-bar select { 
            padding: 10px; 
            font-size: 16px; 
            border: 1px solid #ddd; 
            border-radius: 5px; 
            margin-right: 10px; 
        }
        .delete-button { 
            background-color: #f44336; 
            color: white; 
            border: none; 
            padding: 5px 10px; 
            cursor: pointer; 
            border-radius: 5px; 
            transition: background-color 0.3s ease; 
        }
        .delete-button:hover { 
            background-color: #e53935; 
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
            .add-button, .search-bar {
                text-align: center;
            }
            .search-bar form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .search-bar input, .search-bar select, .search-bar button {
                margin-bottom: 10px;
                width: 100%;
            }
        }
    </style>
    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete this employee?");
        }
    </script>
</head>
<body>
    <div class="container fade-in">
        <h2>Employee List</h2>
        <div class="search-bar">
            <form action="<%= request.getContextPath() %>/employee/search" method="get">
                <input type="text" name="keyword" placeholder="Search by name" required>
                <select name="gender">
                    <option value="">All Genders</option>
                    <option value="true">Male</option>
                    <option value="false">Female</option>
                </select>
                <input type="text" name="address" placeholder="Search by address">
                <input type="date" name="dob" placeholder="Search by date of birth">
                <input type="number" name="departmentID" placeholder="Search by department ID">
                <input type="number" name="minSalary" placeholder="Min salary">
                <input type="number" name="maxSalary" placeholder="Max salary">
                <button type="submit">Search</button>
            </form>
        </div>
        <div class="add-button">
            <a href="<%= request.getContextPath() %>/employee/create">Add New Employee</a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Address</th>
                    <th>Date of Birth</th>
                    <!-- <th>Role ID</th> Hidden column -->
                    <th>Department ID</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                    if (employees != null) {
                        for (Employee employee : employees) {
                %>
                            <tr class="fade-in">
                                <td><%= employee.getEmployeeID() %></td>
                                <td><%= employee.getEmployeeName() %></td>
                                <td><%= employee.isGender() ? "Male" : "Female" %></td>
                                <td><%= employee.getAddress() %></td>
                                <td><%= employee.getDob() %></td>
                                <!-- <td><%= employee.getRoleID() %></td> Hidden column -->
                                <td><%= employee.getDepartmentID() %></td>
                                <td><%= employee.getSalary() %></td>
                                <td>
                                    <form action="<%= request.getContextPath() %>/employee/delete" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                                        <input type="hidden" name="employeeID" value="<%= employee.getEmployeeID() %>">
                                        <button type="submit" class="delete-button">Delete</button>
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
</html>>