package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.ServiceManager;
import manager.VehicleManager;
import model.Service;
import model.Vehicle;

@WebServlet("/services")
public class ServiceServlet extends HttpServlet {
    private ServiceManager serviceManager;
    private VehicleManager vehicleManager;

    @Override
    public void init() throws ServletException {
        this.serviceManager = new ServiceManager();
        this.vehicleManager = new VehicleManager();
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
            case "history":
                viewVehicleHistory(request, response);
                break;
            default:
                listServices(request, response);
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
                addService(request, response);
                break;
            case "update":
                updateService(request, response);
                break;
            case "delete":
                deleteService(request, response);
                break;
            default:
                listServices(request, response);
                break;
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Service> serviceList = serviceManager.getAllServices();
        request.setAttribute("serviceList", serviceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> vehicleList = vehicleManager.getAllVehicles();
        request.setAttribute("vehicleList", vehicleList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id"); // Get ID as String
        Service existingService = serviceManager.getServiceById(id);
        List<Vehicle> vehicleList = vehicleManager.getAllVehicles();
        request.setAttribute("service", existingService);
        request.setAttribute("vehicleList", vehicleList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void viewVehicleHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vehicleReg = request.getParameter("reg");
        List<Service> history = serviceManager.viewHistoryForVehicle(vehicleReg);
        request.setAttribute("vehicleReg", vehicleReg);
        request.setAttribute("historyList", history);
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-history.jsp");
        dispatcher.forward(request, response);
    }

    private void addService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vehicleReg = request.getParameter("vehicleReg");
        String serviceType = request.getParameter("serviceType");
        String mechanic = request.getParameter("mechanic");
        String serviceDate = request.getParameter("serviceDate");
        double cost = Double.parseDouble(request.getParameter("cost"));
        serviceManager.addService(vehicleReg, serviceType, mechanic, serviceDate, cost);
        response.sendRedirect("services?action=list");
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String vehicleReg = request.getParameter("vehicleReg");
        String serviceType = request.getParameter("serviceType");
        String mechanic = request.getParameter("mechanic");
        String serviceDate = request.getParameter("serviceDate");
        double cost = Double.parseDouble(request.getParameter("cost"));
        serviceManager.updateService(id, vehicleReg, serviceType, mechanic, serviceDate, cost);
        response.sendRedirect("services?action=list");
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        serviceManager.deleteService(id);
        response.sendRedirect("services?action=list");
    }
}