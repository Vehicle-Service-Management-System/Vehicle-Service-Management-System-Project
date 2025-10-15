package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.CustomerManager;
import manager.VehicleManager;
import model.Customer;
import model.Vehicle;

@WebServlet("/vehicles")
public class VehicleServlet extends HttpServlet {
    private VehicleManager vehicleManager;
    private CustomerManager customerManager;

    @Override
    public void init() throws ServletException {
        this.vehicleManager = new VehicleManager();
        this.customerManager = new CustomerManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listVehicles(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            return;
        }

        switch (action) {
            case "add":
                addVehicle(request, response);
                break;
            case "update":
                updateVehicle(request, response);
                break;
            case "delete":
                deleteVehicle(request, response);
                break;
            default:
                listVehicles(request, response);
                break;
        }
    }

    private void listVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> vehicleList = vehicleManager.getAllVehicles();
        request.setAttribute("vehicleList", vehicleList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> customerList = (List<Customer>) customerManager.getAllCustomers();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String regNo = request.getParameter("reg");
        Vehicle existingVehicle = vehicleManager.getVehicle(regNo);
        List<Customer> customerList = (List<Customer>) customerManager.getAllCustomers();
        request.setAttribute("vehicle", existingVehicle);
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String registrationNumber = request.getParameter("registrationNumber");
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String ownerId = request.getParameter("ownerId"); // Get ID as String
        vehicleManager.addVehicle(registrationNumber, make, model, year, ownerId);
        response.sendRedirect("vehicles?action=list");
    }

     private void updateVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String registrationNumber = request.getParameter("registrationNumber");
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String ownerId = request.getParameter("ownerId"); // Get ID as String
        vehicleManager.updateVehicle(registrationNumber, make, model, year, ownerId);
        response.sendRedirect("vehicles?action=list");
    }
    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String registrationNumber = request.getParameter("registrationNumber");
        vehicleManager.deleteVehicle(registrationNumber);
        response.sendRedirect("vehicles?action=list");
    }
}
