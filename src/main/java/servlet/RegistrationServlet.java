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
import manager.*;
import model.*;

@WebServlet("/register-service")
public class RegistrationServlet extends HttpServlet {

    private CustomerManager customerManager;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private MechanicManager mechanicManager;

    @Override
    public void init() throws ServletException {
        this.customerManager = new CustomerManager();
        this.vehicleManager = new VehicleManager();
        this.serviceManager = new ServiceManager();
        this.mechanicManager = new MechanicManager();
    }

    // This method prepares the form by fetching existing customers and mechanics
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Customer> customerList = new ArrayList<>(customerManager.getAllCustomers());
        List<Mechanic> mechanicList = new ArrayList<>(mechanicManager.getAllMechanics());

        request.setAttribute("customerList", customerList);
        request.setAttribute("mechanicList", mechanicList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("register-service.jsp");
        dispatcher.forward(request, response);
    }

    // This method processes the submitted form data
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // --- 1. Handle the Customer ---
        String customerId = request.getParameter("customerId");
        
        // If no existing customer was selected, create a new one
        if (customerId == null || customerId.isEmpty()) {
            String name = request.getParameter("newCustomerName");
            String email = request.getParameter("newCustomerEmail");
            String phone = request.getParameter("newCustomerPhone");
            String address = request.getParameter("newCustomerAddress");
            
            // Call the updated method that returns the new ID
            customerId = customerManager.addCustomer(name, email, phone, address);
        }

        // --- 2. Handle the Vehicle ---
        String regNo = request.getParameter("registrationNumber");
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        
        // Add the vehicle, linking it to the customerId we just determined
        vehicleManager.addVehicle(regNo, make, model, year, customerId);

        // --- 3. Handle the Service ---
        String serviceType = request.getParameter("serviceType");
        String mechanicId = request.getParameter("mechanicId");
        String serviceDate = request.getParameter("serviceDate");
        double cost = Double.parseDouble(request.getParameter("cost"));
        
        serviceManager.addService(regNo, serviceType, mechanicId, serviceDate, cost);

        // --- 4. Redirect to a success page (e.g., the service list) ---
        response.sendRedirect("services");
    }
}