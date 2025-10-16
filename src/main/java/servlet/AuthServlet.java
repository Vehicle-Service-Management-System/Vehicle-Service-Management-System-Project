package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.UserManager;
import model.User;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UserManager userManager;

    // Add this method to AuthServlet.java

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate(); // Invalidate the session (log the user out)
    }
    response.sendRedirect("login.jsp"); // Redirect back to the login page
}

    @Override
    public void init() throws ServletException {
        this.userManager = new UserManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // For now, all new users are given the "employee" role.
        userManager.addUser(username, password, "employee");
        // After registering, send them to the login page.
        response.sendRedirect("login.jsp");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userManager.validateUser(username, password);

        if (user != null) {
            // Login successful: create a session.
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            
            // Send user to the main dashboard.
            response.sendRedirect("index.html");
        } else {
            // Login failed: send back to login page with an error message.
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}