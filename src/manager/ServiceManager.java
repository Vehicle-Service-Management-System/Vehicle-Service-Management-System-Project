package manager;
import model.Service;
import util.FileHandler;

import java.io.IOException;
import java.nio.file.Files;  
import java.nio.file.Paths;
import java.util.*;

public class ServiceManager {
    private HashMap<Integer, Service> services;
    private String filePath;
    private FileHandler fileHandler;
    private int nextServiceId;

    public ServiceManager(String filePath) {
        this.services = new HashMap<>();
        this.filePath = filePath;
        this.fileHandler = new FileHandler();
        this.nextServiceId = 1; // Initialize nextServiceId to 1
        loadServicesFromFile();
    }

    private void loadServicesFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int serviceId = Integer.parseInt(parts[0]);
                    String vehicleReg = parts[1];
                    String serviceType = parts[2];
                    String mechanic = parts[3];
                    String serviceDate = parts[4];
                    double cost = Double.parseDouble(parts[5]);
                    Service service = new Service(serviceId, vehicleReg, serviceType, mechanic, serviceDate, cost);
                    services.put(serviceId, service);
                    if (serviceId >= nextServiceId) {
                        nextServiceId = serviceId + 1; // Update nextServiceId to be one more than the highest existing ID
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading services from file: " + e.getMessage());
        }
    }

    public void addService(String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        Service service = new Service(nextServiceId, vehicleReg, serviceType, mechanic, serviceDate, cost);
        services.put(nextServiceId, service);
        nextServiceId++; // Increment nextServiceId for the next service
        saveServicesToFile();
    }

    private void saveServicesToFile() {
        List<String> lines = new ArrayList<>();
        for (Service service : services.values()) {
            lines.add(service.toFileString());
        }
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.out.println("Error saving services to file: " + e.getMessage());
        }
    }
    public Service getService(int serviceId) {
        return services.get(serviceId);
    }
    public Collection<Service> getAllServices() {
        return services.values();
    }
    public void updateService(int serviceId, String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        Service service = services.get(serviceId);
        if (service != null) {
            service.setVehicleReg(vehicleReg);
            service.setServiceType(serviceType);
            service.setMechanic(mechanic);
            service.setServiceDate(serviceDate);
            service.setCost(cost);
            saveServicesToFile();
        }
    }
    public void deleteService(int serviceId) {
        if (services.remove(serviceId) != null) {
            saveServicesToFile();
        } else {
            System.out.println("Service with ID " + serviceId + " not found.");
        }
    }
    public void listServices() {
        if (services.isEmpty()) {
            System.out.println("No services found.");
        } else {
            for (Service service : services.values()) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
            }
        }
    }
    public void searchServicesByVehicleReg(String vehicleReg) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getVehicleReg().toLowerCase().contains(vehicleReg.toLowerCase())) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for vehicle registration: " + vehicleReg);
        }
    }
    public void searchServicesByMechanic(String mechanic) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getMechanic().toLowerCase().contains(mechanic.toLowerCase())) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for mechanic: " + mechanic);
        }
    }
    public void searchServicesByDate(String serviceDate) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getServiceDate().equals(serviceDate)) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for date: " + serviceDate);
        }
    }
    public void searchServicesByCostRange(double minCost, double maxCost) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getCost() >= minCost && service.getCost() <= maxCost) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found in cost range: " + minCost + " - " + maxCost);
        }
    }
    public void searchServicesByType(String serviceType) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getServiceType().toLowerCase().contains(serviceType.toLowerCase())) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for type: " + serviceType);
        }
    }   
    public void searchServicesById(int serviceId) {
        Service service = services.get(serviceId);
        if (service != null) {
            System.out.println(service.toDisplayString());
            System.out.println("--------------------");
        } else {
            System.out.println("No service found with ID: " + serviceId);
        }
    }
    public void displayServiceSummary(int serviceId) {
        Service service = services.get(serviceId);
        if (service != null) {
            System.out.println(service.toSummaryString());
        } else {
            System.out.println("Service with ID " + serviceId + " not found.");
        }
    }
    public void displayAllServiceSummaries() {
        if (services.isEmpty()) {
            System.out.println("No services found.");
        } else {
            for (Service service : services.values()) {
                System.out.println(service.toSummaryString());
                System.out.println("--------------------");
            }
        }
    }
    public void displayAllServicesCompact() {
        if (services.isEmpty()) {
            System.out.println("No services found.");
        } else {
            for (Service service : services.values()) {
                System.out.println(service.toCompactString());
            }
        }
    }
    public void displayAllServicesShort() {
        if (services.isEmpty()) {
            System.out.println("No services found.");
        } else {
            for (Service service : services.values()) {
                System.out.println(service.toShortString());
            }
        }
    }
    public void searchServicesByMechanicAndDate(String mechanic, String serviceDate) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getMechanic().toLowerCase().contains(mechanic.toLowerCase()) && service.getServiceDate().equals(serviceDate)) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for mechanic: " + mechanic + " on date: " + serviceDate);
        }
    }
    public void searchServicesByTypeAndCostRange(String serviceType, double minCost, double maxCost) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getServiceType().toLowerCase().contains(serviceType.toLowerCase()) && service.getCost() >= minCost && service.getCost() <= maxCost) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for type: " + serviceType + " in cost range: " + minCost + " - " + maxCost);
        }
    }
    public void searchServicesByVehicleRegAndType(String vehicleReg, String serviceType) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getVehicleReg().toLowerCase().contains(vehicleReg.toLowerCase()) && service.getServiceType().toLowerCase().contains(serviceType.toLowerCase())) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for vehicle registration: " + vehicleReg + " and type: " + serviceType);
        }
    }
    public void searchServicesByVehicleRegAndDate(String vehicleReg, String serviceDate) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getVehicleReg().toLowerCase().contains(vehicleReg.toLowerCase()) && service.getServiceDate().equals(serviceDate)) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for vehicle registration: " + vehicleReg + " on date: " + serviceDate);
        }
    }
    public void searchServicesByMechanicAndCostRange(String mechanic, double minCost, double maxCost) {
        boolean found = false;
        for (Service service : services.values()) {
            if (service.getMechanic().toLowerCase().contains(mechanic.toLowerCase()) && service.getCost() >= minCost && service.getCost() <= maxCost) {
                System.out.println(service.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found for mechanic: " + mechanic + " in cost range: " + minCost + " - " + maxCost);
        }
    }
    public void saveServicesToFileAs(String newFilePath) {
        List<String> lines = new ArrayList<>();
        for (Service service : services.values()) {
            lines.add(service.toFileString());
        }
        try {
            Files.write(Paths.get(newFilePath), lines);
            System.out.println("Services saved to " + newFilePath);
        } catch (IOException e) {
            System.out.println("Error saving services to file: " + e.getMessage());
        }
    }
}    
