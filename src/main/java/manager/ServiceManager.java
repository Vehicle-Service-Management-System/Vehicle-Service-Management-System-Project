package manager;

import model.Service;
import util.DatabaseConnector;

import java.sql.*;
import util.IdGenerator;
import java.util.*;

public class ServiceManager {

    private final Map<String, List<Service>> serviceMap;

    public ServiceManager() {
        this.serviceMap = new TreeMap<>();
        loadServices();
    }

    private void loadServices() {
        String sql ="SELECT * FROM services";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String vehicleReg = rs.getString("vehicle_reg");
                String serviceType = rs.getString("service_type");
                String mechanic = rs.getString("mechanic");
                String serviceDate = rs.getString("service_date");
                double cost = rs.getDouble("cost");

                Service service = new Service(id, vehicleReg, serviceType, mechanic, serviceDate, cost);
                String date = service.getServiceDate();
                serviceMap.computeIfAbsent(date, k -> new ArrayList<>()).add(service);
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL: Failed to load service data from database. " + e.getMessage());
        }
    }

    public void addService(String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        String id = IdGenerator.generateShortId();
        String sql = "INSERT INTO services (id, vehicle_reg, service_type, mechanic, service_date, cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, id);
                pstmt.setString(2, vehicleReg);
                pstmt.setString(3, mechanic);
                pstmt.setString(4, serviceType);
                pstmt.setDate(5, java.sql.Date.valueOf(serviceDate));
                pstmt.setDouble(6, cost);
                pstmt.executeUpdate();

                Service service = new Service(id, vehicleReg, serviceType, mechanic, serviceDate, cost);
                serviceMap.computeIfAbsent(serviceDate, k -> new ArrayList<>()).add(service);
            } catch(SQLException e){
                System.err.println("CRITICAL: Failed to add service to database. " + e.getMessage());
            }
    }

   // Note: A full updateService method would be more complex due to the TreeMap key (date).
     // For simplicity, it can be implemented by deleting the old and adding the new record.
     // Add this method to your ServiceManager.java

public void updateService(String serviceId, String newVehicleReg, String newServiceType, String newMechanicId, String newServiceDate, double newCost) {
    // First, find the original service to get its original date
    Service originalService = getServiceById(serviceId);
    if (originalService == null) {
        System.out.println("Service with ID " + serviceId + " not found for update.");
        return;
    }
    String originalDate = originalService.getServiceDate();

    String sql = "UPDATE services SET vehicle_reg = ?, mechanic_id = ?, service_type = ?, service_date = ?, cost = ? WHERE id = ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, newVehicleReg);
        pstmt.setString(2, newMechanicId);
        pstmt.setString(3, newServiceType);
        pstmt.setDate(4, java.sql.Date.valueOf(newServiceDate));
        pstmt.setDouble(5, newCost);
        pstmt.setString(6, serviceId);
        
        int affectedRows = pstmt.executeUpdate();

        // If the database was updated successfully, update the in-memory cache
        if (affectedRows > 0) {
            // First, remove the old record from the cache
            List<Service> originalDayList = serviceMap.get(originalDate);
            if (originalDayList != null) {
                originalDayList.remove(originalService);
                if (originalDayList.isEmpty()) {
                    serviceMap.remove(originalDate);
                }
            }

            // Then, add the updated service record to the cache in its new correct location
            originalService.setVehicleReg(newVehicleReg);
            originalService.setServiceType(newServiceType);
            // Assuming you add a setMechanicId method to your Service model
            // originalService.setMechanicId(newMechanicId);
            originalService.setServiceDate(newServiceDate);
            originalService.setCost(newCost);
            
            serviceMap.computeIfAbsent(newServiceDate, k -> new ArrayList<>()).add(originalService);
        }
    } catch (SQLException e) {
        System.err.println("Error updating service in database: " + e.getMessage());
    }
}


    public void deleteService(String serviceId) {
        Service serviceToRemove = getServiceById(serviceId);
        if (serviceToRemove == null){
            System.out.println("Service With Id"+ serviceId +"not found");
            return;
        }
        String sql = "DELETE FROM services WHERE id = ?";
        try (Connection conn =DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, serviceId);
                int affectedRows =pstmt.executeUpdate();
                if (affectedRows > 0){
                    String date = serviceToRemove.getServiceDate();
                    List<Service> dailyServices = serviceMap.get(date);
                    if(dailyServices != null) {
                        dailyServices.remove(serviceToRemove);
                        if(dailyServices.isEmpty()){
                            serviceMap.remove(date);
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error deleting service from database: " + e.getMessage());
            }

    }
    public List<Service> getAllServices() {
    List<Service> allServices = new ArrayList<>();
    for (List<Service> dailyServices : serviceMap.values()) {
        allServices.addAll(dailyServices);
    }
    return allServices;
}

public Service getServiceById(String serviceId) {
    for (List<Service> dailyServices : serviceMap.values()) {
        for (Service service : dailyServices) {
            if (service.getServiceId().equals(serviceId)) {
                return service;
            }
        }
    }
    return null;
    }
    

    // Add these to ServiceManager.java when you are ready to upgrade.

/**
 * Generates a report for a specific date by querying the database directly.
 * @param date The date to search for (in "YYYY-MM-DD" format).
 * @return A list of services for that day.
 */
public List<Service> generateReportByDate(String date) {
    List<Service> report = new ArrayList<>();
    String sql = "SELECT * FROM services WHERE service_date = ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setDate(1, java.sql.Date.valueOf(date));
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String id = rs.getString("id");
            String vehicleReg = rs.getString("vehicle_reg");
            String mechanicId = rs.getString("mechanic_id");
            String serviceType = rs.getString("service_type");
            double cost = rs.getDouble("cost");
            
            report.add(new Service(id, vehicleReg, serviceType, mechanicId, date, cost));
        }
    } catch (SQLException e) {
        System.err.println("Error generating report by date: " + e.getMessage());
    }
    return report;
}

/**
 * Gets the complete service history for a vehicle by querying the database.
 * The results are automatically sorted by date in descending order.
 * @param vehicleReg The vehicle's registration number.
 * @return A sorted list of all services for that vehicle.
 */
public List<Service> viewHistoryForVehicle(String vehicleReg) {
    List<Service> history = new ArrayList<>();
    String sql = "SELECT * FROM services WHERE vehicle_reg = ? ORDER BY service_date DESC";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, vehicleReg);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String id = rs.getString("id");
            String mechanicId = rs.getString("mechanic_id");
            String serviceType = rs.getString("service_type");
            String serviceDate = rs.getDate("service_date").toString();
            double cost = rs.getDouble("cost");
            
            history.add(new Service(id, vehicleReg, serviceType, mechanicId, serviceDate, cost));
        }
    } catch (SQLException e) {
        System.err.println("Error viewing history for vehicle: " + e.getMessage());
    }
    return history;
}
    
}