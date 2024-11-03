<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Production Plan</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        form { display: flex; flex-direction: column; }
        label { margin-bottom: 10px; }
        input, select { padding: 10px; margin-bottom: 20px; border: 1px solid #ddd; border-radius: 5px; }
        button { padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Production Plan</h2>
        <form action="<%= request.getContextPath() %>/productionplan/create" method="post">
            <label for="planName">Plan Name:</label>
            <input type="text" id="planName" name="planName" required>
            
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" required>
            
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" required>
            
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required>
            
            <label for="departmentID">Department ID:</label>
            <input type="number" id="departmentID" name="departmentID" required>
            
            <button type="submit">Create Plan</button>
        </form>
    </div>
</body>
</html>