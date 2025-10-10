package manager;
import model.Vehicle;
import util.FileHandler;    
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class VehicleManager {
    private HashMap<String, Vehicle> vehicles;
    private String filePath;
    private FileHandler fileHandler;

    public VehicleManager(String filePath) {
        this.vehicles = new HashMap<>();
        this.filePath = filePath;
        this.fileHandler = new FileHandler();
        loadVehiclesFromFile();
    }

    private void loadVehiclesFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String registrationNumber = parts[0];
                    String make = parts[1];
                    String model = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    String ownerName = parts[4];
                    String ownerContact = parts[5];
                    Vehicle vehicle = new Vehicle(registrationNumber, make, model, year, ownerName, ownerContact);
                    vehicles.put(registrationNumber, vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles from file: " + e.getMessage());
        }
    }

    public void addVehicle(String registrationNumber, String make, String model, int year, String ownerName, String ownerContact) {
        if (vehicles.containsKey(registrationNumber)) {
            System.out.println("Vehicle with registration number " + registrationNumber + " already exists.");
            return;
        }
        Vehicle vehicle = new Vehicle(registrationNumber, make, model, year, ownerName, ownerContact);
        vehicles.put(registrationNumber, vehicle);
        saveVehiclesToFile();
    }

    private void saveVehiclesToFile() {
        List<String> lines = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            lines.add(vehicle.toFileString());
        }
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.out.println("Error saving vehicles to file: " + e.getMessage());
        }
    }

    public Vehicle getVehicle(String registrationNumber) {
        return vehicles.get(registrationNumber);
    }

    public void updateVehicle(String registrationNumber, String make, String model, int year, String ownerName, String ownerContact) {
        Vehicle vehicle = vehicles.get(registrationNumber);
        if (vehicle != null) {
            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setYear(year);
            vehicle.setOwnerName(ownerName);
            vehicle.setOwnerContact(ownerContact);  
            saveVehiclesToFile();
        } else {    
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }
    public void deleteVehicle(String registrationNumber) {
        if (vehicles.remove(registrationNumber) != null) {
            saveVehiclesToFile();
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }
    public void listVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
            }
        }
    }
    public void searchVehiclesByMake(String make) {
        boolean found = false;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getMake().toLowerCase().contains(make.toLowerCase())) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found with make: " + make);
        }
    }
    public void searchVehiclesByModel(String model) {
        boolean found = false;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getModel().toLowerCase().contains(model.toLowerCase())) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found with model: " + model);
        }
    }
    public void searchVehiclesByYear(int year) {
        boolean found = false;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getYear() == year) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found from year: " + year);
        }
    }
    public void searchVehiclesByOwnerName(String ownerName) {
        boolean found = false;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getOwnerName().toLowerCase().contains(ownerName.toLowerCase())) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found with owner name containing: " + ownerName);
        }
    }
    public void searchVehiclesByOwnerContact(String ownerContact) {
        boolean found = false;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.getOwnerContact().toLowerCase().contains(ownerContact.toLowerCase())) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles found with owner contact containing: " + ownerContact);
        }
    }
    public void displayVehicleSummary(String registrationNumber) {
        Vehicle vehicle = vehicles.get(registrationNumber);
        if (vehicle != null) {
            System.out.println(vehicle.toDisplayString());
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }
    public void displayVehicleShort(String registrationNumber) {
        Vehicle vehicle = vehicles.get(registrationNumber);
        if (vehicle != null) {
            System.out.println(vehicle.toShortString());
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }
    public void displayVehicleCompact(String registrationNumber) {
        Vehicle vehicle = vehicles.get(registrationNumber);
        if (vehicle != null) {
            System.out.println(vehicle.toCompactString());
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }
    public void displayAllVehiclesCompact() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toCompactString());
            }
        }
    }
    public void displayAllVehiclesShort() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toShortString());
            }
        }
    }
    public void displayAllVehiclesSummary() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toDisplayString());
                System.out.println("--------------------");
            }
        }
    }
    public int getTotalVehicles() {
        return vehicles.size();
    }
    public boolean vehicleExists(String registrationNumber) {
        return vehicles.containsKey(registrationNumber);
    }
}    