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
    // Clear the existing cache to ensure we get fresh data
    customers.clear();
    
    String sql = "SELECT * FROM customers";

    try (Connection conn = DatabaseConnector.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
            int count =0;
        while (rs.next()) {
            count++;

            System.out.println(count);
            String id = rs.getString("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phone_number");
            String address = rs.getString("address");

            Customer customer = new Customer(id, name, email, phone, address);
            customers.put(id, customer);
        }
    } catch (SQLException e) {
        System.err.println("CRITICAL ERROR: Could not load customers from database: " + e.getMessage());
    }
}
    public String addCustomer(String name, String email, String phoneNumber, String address) {
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
            
            Customer newCustomer = new Customer(id, name, email, phoneNumber, address);
            customers.put(id, newCustomer);

            return id;
        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Could not add customer to database: " + e.getMessage());
            return null;
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

    // Add this method to CustomerManager.java

public List<Customer> searchCustomers(String searchTerm) {
    List<Customer> foundCustomers = new ArrayList<>();
    // The ILIKE operator is for case-insensitive searching. The '%' is a wildcard.
    String sql = "SELECT * FROM customers WHERE name ILIKE ? OR email ILIKE ? OR phone_number ILIKE ?";

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        String query = "%" + searchTerm + "%"; // Wrap the search term in wildcards
        pstmt.setString(1, query);
        pstmt.setString(2, query);
        pstmt.setString(3, query);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phone_number");
            String address = rs.getString("address");
            foundCustomers.add(new Customer(id, name, email, phone, address));
        }
    } catch (SQLException e) {
        System.err.println("Error searching customers: " + e.getMessage());
    }
    return foundCustomers;
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
    // Reload the cache from the database every time the full list is requested.
    loadCustomers();
    return customers.values();
}
    public Customer getCustomerById(String id) {
        return customers.get(id);
    }
}
