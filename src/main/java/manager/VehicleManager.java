package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Vehicle;
import util.DatabaseConnector;

public class VehicleManager {
    private final Map<String, Vehicle> vehicles;

    public VehicleManager() {
        this.vehicles = new HashMap<>();
        loadVehicles();
    }

    private void loadVehicles() {
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String regNo = rs.getString("registration_number");
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String ownerId = rs.getString("owner_id");
                Vehicle vehicle = new Vehicle(regNo, make, model, year, ownerId);
                vehicles.put(regNo, vehicle);
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not load vehicles: " + e.getMessage());
        }
    }

    public void addVehicle(String registrationNumber, String make, String model, int year, String ownerId) {
        String sql = "INSERT INTO vehicles (registration_number, make, model, year, owner_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, registrationNumber);
            pstmt.setString(2, make);
            pstmt.setString(3, model);
            pstmt.setInt(4, year);
            pstmt.setString(5, ownerId);
            pstmt.executeUpdate();
            
            Vehicle vehicle = new Vehicle(registrationNumber, make, model, year, ownerId);
            vehicles.put(registrationNumber, vehicle);
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }

    public void updateVehicle(String registrationNumber, String make, String model, int year, String ownerId) {
        String sql = "UPDATE vehicles SET make = ?, model = ?, year = ?, owner_id = ? WHERE registration_number = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, make);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.setString(4, ownerId);
            pstmt.setString(5, registrationNumber);
            
            if (pstmt.executeUpdate() > 0) {
                Vehicle vehicle = vehicles.get(registrationNumber);
                if (vehicle != null) {
                    vehicle.setMake(make);
                    vehicle.setModel(model);
                    vehicle.setYear(year);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
        }
    }

    public void deleteVehicle(String registrationNumber) {
        String sql = "DELETE FROM vehicles WHERE registration_number = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, registrationNumber);
            if (pstmt.executeUpdate() > 0) {
                vehicles.remove(registrationNumber);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
        }
    }

    public Vehicle getVehicle(String registrationNumber) {
        return vehicles.get(registrationNumber);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    public List<Vehicle> getVehiclesByOwner(String ownerId) {
        List<Vehicle> ownerVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getOwnerId() != null && vehicle.getOwnerId().equals(ownerId)) {
                ownerVehicles.add(vehicle);
            }
        }
        return ownerVehicles;
    }
}