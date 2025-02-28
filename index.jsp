<%-- 
    Document   : login
    Created on : 30-Oct-2024, 9:21:13 pm
    Author     : paul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">
     <%
    String existMessage = (String) session.getAttribute("exist");
    if (existMessage != null && !existMessage.isEmpty()) {
%>
    <script>
        alert("<%= existMessage.replace("\"", "\\\"") %>");
    </script>
<%
        session.removeAttribute("exist");
    }
%>

    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md border-2 border-green-500">
        <h2 class="text-2xl font-bold text-center text-gray-800 mb-6">Login to your account</h2>
        
        <form action="LoginServlet"   method="POST" class="space-y-4">
            <!-- User Type Dropdown -->
            <div>
                <label for="user_type" class="block text-sm font-medium text-gray-600">User Type</label>
                <select id="user_type" name="usertype" class="w-full mt-1 p-3 border border-green-500 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500" required>
                    <option value="" disabled selected>Select your role</option>
                    <option value="customer">customer</option>
                    <option value="manager">manager</option>
                    <option value="deliveryPerson">deliveryPerson</option>
                    <option value="cashier">cashier</option>
                    <option value="newspaper_agent">newspaperAgent</option>
                </select>
            </div>

            <!-- Email Input -->
            <div>
                <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
                <input type="email" id="email" name="email" class="w-full mt-1 p-3 border border-green-500 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500" required>
            </div>
            
            <!-- Password Input -->
            <div>
                <label for="password" class="block text-sm font-medium text-gray-600">Password</label>
                <input type="password" id="password" name="password" class="w-full mt-1 p-3 border border-green-500 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500" required>
            </div>
            
            <!-- Submit Button -->
            <div>
                <button type="submit" class="w-full p-3 bg-green-500 text-white font-bold rounded-lg hover:bg-green-600 focus:ring-4 focus:ring-green-400 transition duration-200">Login</button>
            </div>
        </form>
        <!--
        <p class="text-sm text-gray-600 mt-6 text-center">Don't have an account? <a href="registration.jsp" class="text-green-500 hover:underline">Sign up</a></p>
        -->
    </div>
</body>
</html>

