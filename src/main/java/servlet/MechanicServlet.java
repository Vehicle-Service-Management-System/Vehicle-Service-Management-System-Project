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
import manager.MechanicManager;
import model.Mechanic;

@WebServlet("/mechanics")
public class MechanicServlet extends HttpServlet {
    private MechanicManager mechanicManager;

    @Override
    public void init() throws ServletException {
        this.mechanicManager = new MechanicManager();
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
                listMechanics(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addMechanic(request, response);
                break;
            case "update":
                updateMechanic(request, response);
                break;
            case "delete":
                deleteMechanic(request, response);
                break;
            default:
                listMechanics(request, response);
                break;
        }
    }

    private void listMechanics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Mechanic> mechanicList = new ArrayList<>(mechanicManager.getAllMechanics());
        request.setAttribute("mechanicList", mechanicList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mechanic-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("mechanic-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Mechanic existingMechanic = mechanicManager.getMechanicById(id);
        request.setAttribute("mechanic", existingMechanic);
        RequestDispatcher dispatcher = request.getRequestDispatcher("mechanic-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addMechanic(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String name = request.getParameter("name");
        mechanicManager.addMechanic(name);
        response.sendRedirect("mechanics");
    }

    private void updateMechanic(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        boolean isAvailable = "on".equals(request.getParameter("isAvailable"));
        mechanicManager.updateMechanic(id, name, isAvailable);
        response.sendRedirect("mechanics");
    }

    private void deleteMechanic(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String id = request.getParameter("id");
        mechanicManager.deleteMechanic(id);
        response.sendRedirect("mechanics");
    }
}