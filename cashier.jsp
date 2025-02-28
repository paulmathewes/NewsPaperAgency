<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cashier Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        .logout-button-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }

        .logout-button-container button {
            padding: 10px 20px;
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            background-color: #ff4d4d;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .logout-button-container button:hover {
            background-color: #e63946;
            transform: scale(1.05);
        }

        .card {
            background-color: #f9f9f9;
            border: 2px solid #f0f0f0;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 90%;
            max-width: 400px;
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
            color: #333;
        }

        p {
            font-size: 1rem;
            margin-bottom: 30px;
            color: #555;
        }

        .button-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        button {
            padding: 15px 20px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: transform 0.2s ease, box-shadow 0.3s ease;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
        }

        /* Solid bright colors for buttons */
        .button-container button:nth-child(1) {
            background-color: #007bff; /* Bright blue */
        }

        .button-container button:nth-child(1):hover {
            background-color: #0056b3;
        }

        .button-container button:nth-child(2) {
            background-color: #28a745; /* Bright green */
        }

        .button-container button:nth-child(2):hover {
            background-color: #218838;
        }

        .button-container button:nth-child(3) {
            background-color: #ffc107; /* Bright yellow */
            color: #333;
        }

        .button-container button:nth-child(3):hover {
            background-color: #e0a800;
        }

        .button-container button:nth-child(4) {
            background-color: #17a2b8; /* Bright cyan */
        }

        .button-container button:nth-child(4):hover {
            background-color: #117a8b;
        }

        footer {
            margin-top: 30px;
            font-size: 0.9rem;
            color: #777;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="logout-button-container">
        <form action="LogoutServlet" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>

    <div class="card">
        <h1><b>WELCOME CASHIER</b></h1>
        <p>Select the options to know the details</p>

        <div class="button-container">
            <!-- Button to calculate monthly bills for customers -->
            <form action="MonthlyBillsServlet" method="post">
                <button type="submit">Calculate Monthly Bills for Customers</button>
            </form>
            
            <!-- Button to calculate monthly salaries for delivery persons -->
            <form action="SalariesServlet" method="post">
                <button type="submit">Calculate Monthly Salaries for Delivery Persons</button>
            </form>
            
            <!-- Button to check overdue payments -->
            <form action="OverdueRemainderServlet" method="post">
                <button type="submit">Check Overdue Payments</button>
            </form>
            
            <!-- Button for monthly summary -->
            <form action="MonthlySummaryServlet" method="post">
                <button type="submit">Monthly Summary</button>
            </form>
        </div>
    </div>

    <footer>
        &copy; 2024 Cashier Dashboard. All rights reserved.
    </footer>
</body>
</html>
