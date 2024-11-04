<%-- 
    Document   : login
    Created on : Nov 4, 2024, 12:57:42 AM
    Author     : huyho
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        :root {
            --primary-color: #4CAF50;
            --primary-color-hover: #45a049;
            --background-gradient: linear-gradient(45deg, #f3ec78, #af4261);
            --container-bg: rgba(255, 255, 255, 0.9);
        }
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 0; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            min-height: 100vh; 
            background: var(--background-gradient);
            animation: gradient 15s ease infinite;
            background-size: 400% 400%;
        }
        @keyframes gradient {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
        .container { 
            max-width: 400px; 
            width: 100%; 
            padding: 20px; 
            background-color: var(--container-bg); 
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); 
            border-radius: 10px; 
            text-align: center;
            animation: fadeIn 1s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        h2 { 
            color: #333; 
            margin-bottom: 20px; 
        }
        form { 
            display: flex; 
            flex-direction: column; 
            align-items: center;
        }
        input { 
            width: 100%; 
            margin-bottom: 15px; 
            padding: 12px; 
            font-size: 16px; 
            border: 1px solid #ddd; 
            border-radius: 5px; 
            box-sizing: border-box; 
        }
        button { 
            width: 100%; 
            padding: 12px; 
            font-size: 16px; 
            background-color: var(--primary-color); 
            color: white; 
            border: none; 
            border-radius: 5px; 
            cursor: pointer; 
            transition: background-color 0.3s ease;
        }
        button:hover { 
            background-color: var(--primary-color-hover); 
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="login" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
