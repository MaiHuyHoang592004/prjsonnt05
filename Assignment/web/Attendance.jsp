<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        form { display: flex; flex-direction: column; }
        label { margin-bottom: 10px; }
        input, select { padding: 10px; margin-bottom: 20px; border: 1px solid #ddd; border-radius: 5px; }
        button { padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #45a049; }
        .message { color: green; text-align: center; margin-bottom: 20px; }
        .error { color: red; text-align: center; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Record Attendance</h2>
        <%
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            if (message != null) {
        %>
            <div class="message"><%= message %></div>
        <%
            }
            if (error != null) {
        %>
            <div class="error"><%= error %></div>
        <%
            }
        %>
        <form action="<%= request.getContextPath() %>/attendance" method="post">
            <label for="schEmpID">Schedule Employee ID:</label>
            <input type="number" id="schEmpID" name="schEmpID" required>
            
            <label for="quantity">Quantity:</label>
            <input type="number" step="0.01" id="quantity" name="quantity" required>
            
            <label for="alpha">Alpha:</label>
            <input type="number" step="0.01" id="alpha" name="alpha" required>
            
            <button type="submit">Record Attendance</button>
        </form>
    </div>
</body>
</html>