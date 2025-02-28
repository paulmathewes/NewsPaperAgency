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

public class MonthlySummaryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String JDBC_URL = "jdbc:mysql://localhost:3306/PROJECT";  // Update with your database name
        String JDBC_USER = "paul";  // Update with your DB username
        String JDBC_PASSWORD = "sunny@123";  // Update with your DB password

        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (PrintWriter out = response.getWriter()) {
                // HTML structure with styles
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Monthly Summary</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; text-align: center; color: #333; }");
                out.println("h2 { color: #007bff; margin-bottom: 20px; }");
                out.println("h3 { color: #555; margin-bottom: 10px; }");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
                out.println("th, td { padding: 12px 20px; border: 1px solid #ddd; text-align: left; }");
                out.println("th { background-color: #007bff; color: #fff; }");
                out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
                out.println("tr:hover { background-color: #f1f1f1; }");
                out.println("a { display: inline-block; margin: 20px 0; text-decoration: none; color: #fff; background-color: #007bff; padding: 10px 20px; border-radius: 5px; transition: background-color 0.3s ease; }");
                out.println("a:hover { background-color: #0056b3; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Monthly Summary</h2>");

                // Query to get copies sold by publication
                String copiesSoldQuery = "SELECT p.title AS publication_name, IFNULL(SUM(d.value / p.price), 0) AS copies_sold "
                                       + "FROM deliveries d "
                                       + "JOIN publications p ON d.delivery_id = p.id "
                                       + "GROUP BY p.title";

                // Query to calculate total salaries paid
                String salariesPaidQuery = "SELECT IFNULL(SUM(d.value) * 0.025, 0) AS total_salaries_paid FROM deliveries d";

                // Display total copies sold
                out.println("<h3>Copies Sold by Publication</h3>");
                out.println("<table>");
                out.println("<tr><th>Publication Name</th><th>Copies Sold</th></tr>");

                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                     PreparedStatement stmtCopies = conn.prepareStatement(copiesSoldQuery);
                     ResultSet rsCopies = stmtCopies.executeQuery()) {

                    if (!rsCopies.isBeforeFirst()) {  // Check if ResultSet is empty
                        out.println("<tr><td colspan='2'>No data available for this month.</td></tr>");
                    } else {
                        while (rsCopies.next()) {
                            String publicationName = rsCopies.getString("publication_name");
                            double copiesSold = rsCopies.getDouble("copies_sold");
                            out.printf("<tr><td>%s</td><td>%.0f</td></tr>", publicationName, copiesSold);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<tr><td colspan='2'>Error retrieving data: " + e.getMessage() + "</td></tr>");
                }
                out.println("</table>");

                // Display total salaries paid
                out.println("<h3>Total Salaries Paid</h3>");
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                     PreparedStatement stmtSalaries = conn.prepareStatement(salariesPaidQuery);
                     ResultSet rsSalaries = stmtSalaries.executeQuery()) {

                    if (rsSalaries.next()) {
                        double totalSalariesPaid = rsSalaries.getDouble("total_salaries_paid");
                        out.printf("<p>Total Salaries Paid This Month: <b>$%.2f</b></p>", totalSalariesPaid);
                    } else {
                        out.println("<p>Total Salaries Paid This Month: <b>$0.00</b></p>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<p>Error retrieving salary data: " + e.getMessage() + "</p>");
                }

                out.println("<a href='cashier.jsp'>Back to Dashboard</a>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("<p>JDBC Driver not found. Please check your configuration.</p>");
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
        return "Displays monthly summary including copies sold and total salaries paid.";
    }
}
