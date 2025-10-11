package manager;

import model.Customer;
import util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerManager {
    private final Map<Integer, Customer> customers;
    private final String filePath;
    private int nextId;

    public CustomerManager(String filePath) {
        this.customers = new HashMap<>();
        this.filePath = filePath;
        this.nextId = 1;
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<String> lines = FileHandler.loadFromFile(filePath);
            if (lines.isEmpty()) {
                return;
            }

            for (String line : lines) {
                Customer customer = Customer.fromCSV(line);
                if (customer != null) {
                    customers.put(customer.getId(), customer);
                    if (customer.getId() >= nextId) {
                        nextId = customer.getId() + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not load customers from file: " + e.getMessage());
        }
    }

    public void addCustomer(String name, String email, String phoneNumber, String address) {
        int id = nextId++;
        Customer customer = new Customer(id, name, email, phoneNumber, address);
        customers.put(id, customer);

        try {
            FileHandler.appendToFile(filePath, customer.toFileString());
        } catch (IOException e) {
            System.err.println("Error appending customer to file: " + e.getMessage());
        }
    }

    public void updateCustomer(int id, String name, String email, String phoneNumber, String address) {
        Customer customer = customers.get(id);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            saveAllCustomersToFile();
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }

    public void deleteCustomer(int id) {
        if (customers.remove(id) != null) {
            saveAllCustomersToFile();
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }

    private void saveAllCustomersToFile() {
        List<String> lines = new ArrayList<>();
        for (Customer customer : customers.values()) {
            lines.add(customer.toFileString());
        }
        try {
            FileHandler.saveToFile(filePath, lines);
        } catch (IOException e) {
            System.err.println("Error saving all customers to file: " + e.getMessage());
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
    public Collection<Customer> getAllCustomers() {
    return customers.values();
    }
    public Customer getCustomerById(int id) {
        return customers.get(id);
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
}