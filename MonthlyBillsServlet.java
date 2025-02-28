package com.NAAS;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthlyBillsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String JDBC_URL = "jdbc:mysql://localhost:3306/PROJECT";
        String JDBC_USER = "paul";
        String JDBC_PASSWORD = "sunny@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (PrintWriter out = response.getWriter()) {
                // Start of HTML with improved styling
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Monthly Bills</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; color: #333; margin: 0; padding: 20px; text-align: center; }");
                out.println("h2 { color: #007bff; }");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
                out.println("th, td { padding: 15px; text-align: left; border: 1px solid #ddd; }");
                out.println("th { background-color: #007bff; color: white; }");
                out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
                out.println("tr:hover { background-color: #f1f1f1; }");
                out.println("a { display: inline-block; margin: 20px 0; text-decoration: none; color: #fff; background-color: #007bff; padding: 10px 20px; border-radius: 5px; transition: background-color 0.3s ease; }");
                out.println("a:hover { background-color: #0056b3; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Monthly Bills for Customers</h2>");
                out.println("<table>");
                out.println("<tr><th>Customer ID</th><th>Customer Name</th><th>Total Bill</th></tr>");

                // Query and calculation logic
                String sql = "SELECT c.id AS customer_id, c.name AS customer_name, p.price, s.frequency "
                        + "FROM customers c "
                        + "JOIN subscriptions s ON c.id = s.customer_id "
                        + "JOIN publications p ON s.publication_id = p.id";

                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {

                    int currentCustomerId = -1;
                    double customerTotal = 0.0;
                    String customerName = "";

                    while (rs.next()) {
                        int customerId = rs.getInt("customer_id");
                        String frequency = rs.getString("frequency");
                        double price = rs.getDouble("price");

                        // Calculate monthly cost based on frequency
                        double monthlyCost = switch (frequency) {
                            case "daily" -> price * 30;
                            case "weekly" -> price * 4;
                            case "monthly" -> price;
                            default -> 0.0;
                        };

                        if (customerId != currentCustomerId) {
                            if (currentCustomerId != -1) {
                                out.printf("<tr><td>%d</td><td>%s</td><td>$%.2f</td></tr>", currentCustomerId, customerName, customerTotal);
                            }
                            currentCustomerId = customerId;
                            customerName = rs.getString("customer_name");
                            customerTotal = 0.0;
                        }

                        customerTotal += monthlyCost;
                    }

                    if (currentCustomerId != -1) {
                        out.printf("<tr><td>%d</td><td>%s</td><td>$%.2f</td></tr>", currentCustomerId, customerName, customerTotal);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<p style='color: red;'>Error calculating bills: " + e.getMessage() + "</p>");
                }

                out.println("</table>");
                out.println("<a href='cashier.jsp'>Back to Home</a>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("<p style='color: red;'>JDBC Driver not found. Please check your configuration.</p>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "MonthlyBillsServlet - calculates and displays customer bills";
    }
}
