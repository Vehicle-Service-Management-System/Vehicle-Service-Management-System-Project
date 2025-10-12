package manager;

import model.Vehicle;
import util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleManager {

    private final Map<String, Vehicle> vehicles;
    private final String filePath;

    public VehicleManager(String filePath) {
        this.vehicles = new HashMap<>();
        this.filePath = filePath;
        loadVehicles();
    }

    private void loadVehicles() {
        try {
            List<String> lines = FileHandler.loadFromFile(filePath);
            for (String line : lines) {
                Vehicle vehicle = Vehicle.fromCSV(line);
                if (vehicle != null) {
                    vehicles.put(vehicle.getRegistrationNumber(), vehicle);
                }
            }
        } catch (IOException e) {
            System.err.println("CRITICAL: Failed to load vehicle data. " + e.getMessage());
        }
    }

    public void addVehicle(String registrationNumber, String make, String model, int year, int ownerId) {
        if (vehicles.containsKey(registrationNumber)) {
            System.out.println("Error: Vehicle with registration number " + registrationNumber + " already exists.");
            return;
        }
        Vehicle vehicle = new Vehicle(registrationNumber, make, model, year, ownerId);
        vehicles.put(registrationNumber, vehicle);

        try {
            FileHandler.appendToFile(filePath, vehicle.toCSV());
        } catch (IOException e) {
            System.err.println("Error saving new vehicle: " + e.getMessage());
        }
    }

    public void updateVehicle(String registrationNumber, String make, String model, int year, int ownerId) {
        Vehicle vehicle = vehicles.get(registrationNumber);
        if (vehicle != null) {
            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setYear(year);
            saveAllVehiclesToFile();
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }

    public void deleteVehicle(String registrationNumber) {
        if (vehicles.remove(registrationNumber) != null) {
            saveAllVehiclesToFile();
        } else {
            System.out.println("Vehicle with registration number " + registrationNumber + " not found.");
        }
    }

    private void saveAllVehiclesToFile() {
        List<String> lines = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            lines.add(vehicle.toCSV());
        }
        try {
            FileHandler.saveToFile(filePath, lines);
        } catch (IOException e) {
            System.err.println("Error saving all vehicles to file: " + e.getMessage());
        }
    }

    public Vehicle getVehicle(String registrationNumber) {
        return vehicles.get(registrationNumber);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    public List<Vehicle> getVehiclesByOwner(int ownerId) {
        return vehicles.values().stream()
                .filter(vehicle -> vehicle.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }

    public void listVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toString());
                System.out.println("--------------------");
            }
        }
    }
}