package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Customer;
import util.DatabaseConnector;
import util.IdGenerator;

public class CustomerManager {
    private final Map<String, Customer> customers;

    public CustomerManager() {
        this.customers = new HashMap<>();
        loadCustomers();
    }

    private void loadCustomers() {
        String sql ="SELECT * FROM customers";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                Customer customer = new Customer(id, name, email, phoneNumber, address);
                customers.put(id, customer);
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not load customers from database: " + e.getMessage());
        }

    }

    public void addCustomer(String name, String email, String phoneNumber, String address) {
        String id = IdGenerator.generateShortId();
        String sql = "INSERT INTO customers (id, name, email, phone_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not add customer to database: " + e.getMessage());
        }
    }

    public void updateCustomer(String id, String name, String email, String phoneNumber, String address) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, address);
            pstmt.setString(5, id);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                Customer customer = customers.get(id);
                if (customer != null) {
                    customer.setName(name);
                    customer.setEmail(email);
                    customer.setPhoneNumber(phoneNumber);
                    customer.setAddress(address);
                }
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not update customer in database: " + e.getMessage());
        }
    }

    public void deleteCustomer(String id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                customers.remove(id);
            }
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not delete customer from database: " + e.getMessage());
        }
    }
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
    public Customer getCustomerById(String id) {
        return customers.get(id);
    }
}
