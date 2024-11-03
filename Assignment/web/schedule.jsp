<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Schedule</title>
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
        <h2>Create Schedule Campaign</h2>
        <form action="<%= request.getContextPath() %>/schedule" method="post">
            <input type="hidden" name="action" value="addCampaign">
            <label for="planCampnID">Plan Campaign ID:</label>
            <input type="number" id="planCampnID" name="planCampnID" required>
            
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>
            
            <label for="shift">Shift:</label>
            <input type="number" id="shift" name="shift" required>
            
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required>
            
            <button type="submit">Create Schedule Campaign</button>
        </form>

        <h2>Add Employee to Schedule</h2>
        <form action="<%= request.getContextPath() %>/schedule" method="post">
            <input type="hidden" name="action" value="addEmployee">
            <label for="scID">Schedule Campaign ID:</label>
            <input type="number" id="scID" name="scID" required>
            
            <label for="employeeID">Employee ID:</label>
            <input type="number" id="employeeID" name="employeeID" required>
            
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required>
            
            <button type="submit">Add Employee to Schedule</button>
        </form>
    </div>
</body>
</html>