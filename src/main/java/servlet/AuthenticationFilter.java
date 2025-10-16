package servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// This filter intercepts EVERY request to the application
//@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false); // 'false' means don't create a new session

        String loginURI = request.getContextPath() + "/login.jsp";
        String authURI = request.getContextPath() + "/auth";
        String regURI = request.getContextPath() + "/reg.jsp";

        boolean loggedIn = session != null && session.getAttribute("username") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean authRequest = request.getRequestURI().equals(authURI);
        boolean regRequest = request.getRequestURI().equals(regURI);

        // If the user is logged in OR they are on the login/auth/reg page, let them through.
        if (loggedIn || loginRequest || authRequest || regRequest) {
            chain.doFilter(request, response);
        } else {
            // Otherwise, redirect them to the login page.
            response.sendRedirect(loginURI);
        }
    }
    
    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}