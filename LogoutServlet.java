package com.NAAS;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Handles user logout by invalidating the session and redirecting to the login page.
 */
public class LogoutServlet extends HttpServlet {

    /**
     * Processes both HTTP GET and POST requests.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Fetch session if it exists, otherwise return null
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Invalidate the session to log out the user
            session.invalidate();
        }

        // Output a message and redirect after a short delay
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Logging out, have a great day!</h2>");
        response.getWriter().println("<p>You will be redirected shortly...</p>");
        response.getWriter().println("<meta http-equiv='refresh' content='3;url=index.jsp'>"); // Redirect after 3 seconds
        response.getWriter().println("</body></html>");
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
        return "Servlet that handles user logout";
    }
}
