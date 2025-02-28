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

public class SalariesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String JDBC_URL = "jdbc:mysql://localhost:3306/PROJECT";  // Update with your database name
        String JDBC_USER = "paul";  // Update with your DB username
        String JDBC_PASSWORD = "sunny@123";  // Update with your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (PrintWriter out = response.getWriter()) {
                // HTML structure with CSS for styling
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Delivery Person Salaries</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; margin: 0; padding: 20px; text-align: center; color: #333; }");
                out.println("h2 { color: #28a745; }");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
                out.println("th, td { padding: 12px 20px; border: 1px solid #ddd; text-align: left; }");
                out.println("th { background-color: #28a745; color: #fff; }");
                out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
                out.println("tr:hover { background-color: #f1f1f1; }");
                out.println("a { display: inline-block; margin-top: 20px; text-decoration: none; color: #fff; background-color: #007bff; padding: 10px 20px; border-radius: 5px; transition: background-color 0.3s; }");
                out.println("a:hover { background-color: #0056b3; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Monthly Salaries for Delivery Persons</h2>");
                out.println("<table>");
                out.println("<tr><th>Delivery Person ID</th><th>Name</th><th>Monthly Salary</th></tr>");

                // SQL query to calculate salaries
                String sql = "SELECT dp.id AS delivery_person_id, dp.name AS delivery_person_name, "
                           + "SUM(d.value) * 0.025 AS salary "
                           + "FROM delivery_persons dp "
                           + "JOIN deliveries d ON dp.id = d.delivery_person_id "
                           + "GROUP BY dp.id, dp.name";

                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {

                    if (!rs.isBeforeFirst()) {  // Check if ResultSet is empty
                        out.println("<tr><td colspan='3'>No data available for the current month.</td></tr>");
                    } else {
                        while (rs.next()) {
                            int deliveryPersonId = rs.getInt("delivery_person_id");
                            String deliveryPersonName = rs.getString("delivery_person_name");
                            double salary = rs.getDouble("salary");

                            out.printf("<tr><td>%d</td><td>%s</td><td>$%.2f</td></tr>", deliveryPersonId, deliveryPersonName, salary);
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<tr><td colspan='3'>Error calculating salaries: " + e.getMessage() + "</td></tr>");
                }

                out.println("</table>");
                out.println("<a href='cashier.jsp'>Back to Dashboard</a>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("<p>Error: JDBC Driver not found. Please check your configuration.</p>");
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
        return "Displays monthly salaries for delivery persons.";
    }
}
