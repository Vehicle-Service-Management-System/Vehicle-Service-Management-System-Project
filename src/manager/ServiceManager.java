package manager;

import model.Service;
import util.FileHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceManager {

    private final Map<String, List<Service>> serviceMap;
    private final String filePath;
    private int nextServiceId;

    public ServiceManager(String filePath) {
        this.serviceMap = new TreeMap<>();
        this.filePath = filePath;
        this.nextServiceId = 1;
        loadServices();
    }

    private void loadServices() {
        try {
            List<String> lines = FileHandler.loadFromFile(filePath);
            int maxId = 0;

            for (String line : lines) {
                Service service = Service.fromCSV(line);
                if (service != null) {
                    String date = service.getServiceDate();
                    serviceMap.computeIfAbsent(date, k -> new ArrayList<>()).add(service);
                    if (service.getServiceId() > maxId) {
                        maxId = service.getServiceId();
                    }
                }
            }
            nextServiceId = maxId + 1;

        } catch (IOException e) {
            System.err.println("CRITICAL: Failed to load service data. " + e.getMessage());
        }
    }

    public void addService(String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        int id = nextServiceId++;
        Service service = new Service(id, vehicleReg, serviceType, mechanic, serviceDate, cost);
        serviceMap.computeIfAbsent(serviceDate, k -> new ArrayList<>()).add(service);

        try {
            FileHandler.appendToFile(filePath, service.toFileString());
        } catch (IOException e) {
            System.err.println("Error saving new service: " + e.getMessage());
        }
    }

    public void updateService(int serviceId, String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        for (List<Service> dailyServices : serviceMap.values()) {
            for (Service service : dailyServices) {
                if (service.getServiceId() == serviceId) {
                    service.setVehicleReg(vehicleReg);
                    service.setServiceType(serviceType);
                    service.setMechanic(mechanic);
                    service.setServiceDate(serviceDate);
                    service.setCost(cost);
                    saveAllServicesToFile();
                    return;
                }
            }
        }
        System.out.println("Service with ID " + serviceId + " not found for update.");
    }

    public void deleteService(int serviceId) {
        for (Map.Entry<String, List<Service>> entry : serviceMap.entrySet()) {
            List<Service> dailyServices = entry.getValue();
            Iterator<Service> iterator = dailyServices.iterator();
            while (iterator.hasNext()) {
                Service service = iterator.next();
                if (service.getServiceId() == serviceId) {
                    iterator.remove();
                    if (dailyServices.isEmpty()) {
                        serviceMap.remove(entry.getKey());
                    }
                    saveAllServicesToFile();
                    return;
                }
            }
        }
        System.out.println("Service with ID " + serviceId + " not found for deletion.");
    }

    private void saveAllServicesToFile() {
        List<String> lines = new ArrayList<>();
        for (List<Service> dailyServices : serviceMap.values()) {
            for (Service service : dailyServices) {
                lines.add(service.toFileString());
            }
        }
        try {
            FileHandler.saveToFile(filePath, lines);
        } catch (IOException e) {
            System.err.println("Error saving all services to file: " + e.getMessage());
        }
    }

    public List<Service> generateReportByDate(String date) {
        return serviceMap.getOrDefault(date, Collections.emptyList());
    }

   public List<Service> viewHistoryForVehicle(String vehicleReg) {
    return serviceMap.values().stream()
        .flatMap(Collection::stream)
        .filter(service -> service.getVehicleReg().equalsIgnoreCase(vehicleReg))
        .collect(Collectors.toList());
}
}