package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Mechanic;
import util.DatabaseConnector;
import util.IdGenerator;

public class MechanicManager {
    private final Map<String, Mechanic> mechanicMap;

    public MechanicManager() {
        this.mechanicMap = new HashMap<>();
        loadMechanics();
    }

    private void loadMechanics() {
        String sql = "SELECT * FROM mechanics";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                boolean isAvailable = rs.getBoolean("is_available");
                Mechanic mechanic = new Mechanic(id, name, isAvailable);
                mechanicMap.put(id, mechanic);
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not load mechanics: " + e.getMessage());
        }
    }

    public void addMechanic(String name) {
        String id = IdGenerator.generateShortId();
        String sql = "INSERT INTO mechanics (id, name, is_available) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setBoolean(3, true); // New mechanics are available by default
            pstmt.executeUpdate();
            
            Mechanic mechanic = new Mechanic(id, name, true);
            mechanicMap.put(id, mechanic);
        } catch (SQLException e) {
            System.err.println("Error adding mechanic: " + e.getMessage());
        }
    }

    public void updateMechanic(String id, String name, boolean isAvailable) {
        String sql = "UPDATE mechanics SET name = ?, is_available = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setBoolean(2, isAvailable);
            pstmt.setString(3, id);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                Mechanic mechanic = mechanicMap.get(id);
                if (mechanic != null) {
                    mechanic.setName(name);
                    mechanic.setAvailable(isAvailable);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating mechanic: " + e.getMessage());
        }
    }

    public void deleteMechanic(String id) {
        String sql = "DELETE FROM mechanics WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                mechanicMap.remove(id);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting mechanic: " + e.getMessage());
        }
    }

    public List<Mechanic> searchMechanics(String searchTerm) {
    List<Mechanic> foundMechanics = new ArrayList<>();
    // Use ILIKE for case-insensitive search on the name.
    String sql = "SELECT * FROM mechanics WHERE name ILIKE ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, "%" + searchTerm + "%"); // Wrap in wildcards
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            boolean isAvailable = rs.getBoolean("is_available");
            foundMechanics.add(new Mechanic(id, name, isAvailable));
        }
    } catch (SQLException e) {
        System.err.println("Error searching mechanics: " + e.getMessage());
    }
    return foundMechanics;
}
    
    public Mechanic getMechanicById(String id) {
        return mechanicMap.get(id);
    }

    public Collection<Mechanic> getAllMechanics() {
        return mechanicMap.values();
    }
}