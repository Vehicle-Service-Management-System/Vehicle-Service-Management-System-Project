package manager;
import model.Customer; 
import util.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CustomerManager {
    private HashMap<Integer, Customer> customers;
    private String filePath;
    private FileHandler fileHandler;
    private int nextId;
    public CustomerManager(String filePath) {
        this.customers = new HashMap<>();
        this.filePath = filePath;
        this.fileHandler = new FileHandler();
        this.nextId = 1; // Initialize nextId to 1
        loadCustomersFromFile();
    }
    private void loadCustomersFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String phoneNumber = parts[3];
                    String address = parts[4];
                    Customer customer = new Customer(id, name, email, phoneNumber, address);
                    customers.put(id, customer);
                    if (id >= nextId) {
                        nextId = id + 1; // Update nextId to be one more than the highest existing ID
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers from file: " + e.getMessage());
        }
    }
    public void addCustomer(String name, String email, String phoneNumber, String address) {
        Customer customer = new Customer(nextId, name, email, phoneNumber, address);
        customers.put(nextId, customer);
        nextId++; // Increment nextId for the next customer
        saveCustomersToFile();
    }
    private void saveCustomersToFile() {
        List<String> lines = new ArrayList<>();
        for (Customer customer : customers.values()) {
            lines.add(customer.toFileString());
        }
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.out.println("Error saving customers to file: " + e.getMessage());
        }
    }
    public void updateCustomer(int id, String name, String email, String phoneNumber, String address) {
        Customer customer = customers.get(id);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            saveCustomersToFile();
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }
    public void deleteCustomer(int id) {
        if (customers.remove(id) != null) {
            saveCustomersToFile();
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }
    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers.values()) {
                System.out.println(customer.toDisplayString());
                System.out.println("--------------------");
            }
        }
    }
    public void searchCustomersByName(String name) {
        boolean found = false;
        for (Customer customer : customers.values()) {
            if (customer.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(customer.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No customers found with name containing: " + name);
        }
    }
    public void searchCustomersByEmail(String email) {
        boolean found = false;
        for (Customer customer : customers.values()) {
            if (customer.getEmail().toLowerCase().contains(email.toLowerCase())) {
                System.out.println(customer.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No customers found with email containing: " + email);
        }
    }
    public void searchCustomersByPhoneNumber(String phoneNumber) {
        boolean found = false;
        for (Customer customer : customers.values()) {
            if (customer.getPhoneNumber().toLowerCase().contains(phoneNumber.toLowerCase())) {
                System.out.println(customer.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No customers found with phone number containing: " + phoneNumber);
        }
    }
    public void searchCustomersByAddress(String address) {
        boolean found = false;
        for (Customer customer : customers.values()) {
            if (customer.getAddress().toLowerCase().contains(address.toLowerCase())) {
                System.out.println(customer.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No customers found with address containing: " + address);
        }
    }
    public void displayCustomerSummary(int id) {
        Customer customer = customers.get(id);
        if (customer != null) {
            System.out.println(customer.toSummaryString());
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }
    public void displayAllCustomersCompact() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers.values()) {
                System.out.println(customer.toCompactString());
            }
        }
    }
    public void displayAllCustomersShort() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers.values()) {
                System.out.println(customer.toShortString());
            }
        }
    }
    public void saveCustomersToFile(String customFilePath) {
        List<String> lines = new ArrayList<>();
        for (Customer customer : customers.values()) {
            lines.add(customer.toFileString());
        }
        try {
            Files.write(Paths.get(customFilePath), lines);
            System.out.println("Customers saved to " + customFilePath);
        } catch (IOException e) {
            System.out.println("Error saving customers to file: " + e.getMessage());
        }
    }
    
}    