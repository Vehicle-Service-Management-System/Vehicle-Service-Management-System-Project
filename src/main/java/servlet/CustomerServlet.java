package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.CustomerManager;
import model.Customer;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerManager customerManager;

    @Override
    public void init() throws ServletException {
        this.customerManager = new CustomerManager();
    }

// In CustomerServlet.java's doGet method
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");
    if (action == null) {
        action = "list";
    }

    switch (action) {
        case "new":
            // This case is no longer used but can be left for now
            showNewForm(request, response);
            break;
        case "edit":
            showEditForm(request, response);
            break;
        case "search": // NEW: Handle the search request
            searchCustomers(request, response);
            break;
        default: // "list"
            listCustomers(request, response);
            break;
    }
}


@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    // No need for a default, as POST requests are for specific actions.
    if (action != null) {
        switch (action) {
            case "update":
                updateCustomer(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            default:
                // If an unknown action is posted, just show the list.
                listCustomers(request, response);
                break;
        }
    } else {
        listCustomers(request, response);
    }
}

    // Add this new private method to CustomerServlet.java
    private void searchCustomers(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String query = request.getParameter("query");
    List<Customer> customerList = customerManager.searchCustomers(query); // Call the new manager method

    request.setAttribute("customerList", customerList); // Put the results in the request
    RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
    dispatcher.forward(request, response); // Forward back to the same list page
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> customerList = new ArrayList<>(customerManager.getAllCustomers());
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id"); // Get ID as String
        Customer existingCustomer = customerManager.getCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        customerManager.addCustomer(name, email, phone, address);
        response.sendRedirect("customers?action=list");
    }

     private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id"); // Get ID as String
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        customerManager.updateCustomer(id, name, email, phone, address);
        response.sendRedirect("customers?action=list");
    }

     private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id"); // Get ID as String
        customerManager.deleteCustomer(id);
        response.sendRedirect("customers?action=list");
    }
}