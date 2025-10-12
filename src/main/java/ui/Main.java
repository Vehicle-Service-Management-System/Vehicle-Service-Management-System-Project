package ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import manager.CustomerManager;
import manager.ServiceManager;
import manager.VehicleManager;
import model.Customer;
import model.Service;
import model.Vehicle;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CustomerManager customerManager;
    private static VehicleManager vehicleManager;
    private static ServiceManager serviceManager;

    public static void main(String[] args) {
        customerManager = new CustomerManager("data/customers.csv");
        vehicleManager = new VehicleManager("data/vehicles.csv");
        serviceManager = new ServiceManager("data/services.csv");

        while (true) {
            displayMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleCustomerMenu();
                        break;
                    case 2:
                        handleVehicleMenu();
                        break;
                    case 3:
                        handleServiceMenu();
                        break;
                    case 4:
                        System.out.println("Exiting application. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Vehicle Service Management System ---");
        System.out.println("1. Manage Customers");
        System.out.println("2. Manage Vehicles");
        System.out.println("3. Manage Services");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleCustomerMenu() {
        while (true) {
            System.out.println("\n-- Customer Management --");
            System.out.println("1. Add New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Find Customer by ID");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 6) {
                    break;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();
                        customerManager.addCustomer(name, email, phone, address);
                        System.out.println("Customer added successfully!");
                        break;
                    case 2:
                        System.out.println("\n-- All Customers --");
                        for (Customer c : customerManager.getAllCustomers()) {
                            System.out.println(c);
                            System.out.println("--------------------");
                        }
                        break;
                    case 3:
                        System.out.print("Enter Customer ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        if (customerManager.getCustomerById(updateId) == null) {
                            System.out.println("Customer not found.");
                        } else {
                            System.out.print("Enter new Name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new Email: ");
                            String newEmail = scanner.nextLine();
                            System.out.print("Enter new Phone: ");
                            String newPhone = scanner.nextLine();
                            System.out.print("Enter new Address: ");
                            String newAddress = scanner.nextLine();
                            customerManager.updateCustomer(updateId, newName, newEmail, newPhone, newAddress);
                            System.out.println("Customer updated successfully!");
                        }
                        break;
                    case 4:
                        System.out.print("Enter Customer ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        customerManager.deleteCustomer(deleteId);
                        System.out.println("Delete request processed.");
                        break;
                    case 5:
                        System.out.print("Enter Customer ID to find: ");
                        int findId = scanner.nextInt();
                        scanner.nextLine();
                        Customer foundCustomer = customerManager.getCustomerById(findId);
                        if (foundCustomer != null) {
                            System.out.println(foundCustomer);
                        } else {
                            System.out.println("Customer not found.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void handleVehicleMenu() {
        while (true) {
            System.out.println("\n-- Vehicle Management --");
            System.out.println("1. Add New Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Update Vehicle");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. Find Vehicles by Owner ID");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 6) break;

                switch (choice) {
                    case 1:
                        System.out.print("Enter Registration Number: ");
                        String regNo = scanner.nextLine();
                        System.out.print("Enter Make: ");
                        String make = scanner.nextLine();
                        System.out.print("Enter Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter Year: ");
                        int year = scanner.nextInt();
                        System.out.print("Enter Owner's Customer ID: ");
                        int ownerId = scanner.nextInt();
                        scanner.nextLine();
                        if (customerManager.getCustomerById(ownerId) == null) {
                            System.out.println("Error: No customer exists with ID " + ownerId);
                        } else {
                            vehicleManager.addVehicle(regNo, make, model, year, ownerId);
                            System.out.println("Vehicle added successfully!");
                        }
                        break;
                    case 2:
                        System.out.println("\n-- All Vehicles --");
                        for (Vehicle v : vehicleManager.getAllVehicles()) {
                            System.out.println(v);
                            System.out.println("--------------------");
                        }
                        break;
                    case 3:
                        System.out.print("Enter Registration Number to update: ");
                        String regToUpdate = scanner.nextLine();
                        if (vehicleManager.getVehicle(regToUpdate) == null) {
                            System.out.println("Vehicle not found.");
                        } else {
                            System.out.print("Enter new Make: ");
                            String newMake = scanner.nextLine();
                            System.out.print("Enter new Model: ");
                            String newModel = scanner.nextLine();
                            System.out.print("Enter new Year: ");
                            int newYear = scanner.nextInt();
                            System.out.print("Enter new Owner's Customer ID: ");
                            int newOwnerId = scanner.nextInt();
                            scanner.nextLine();
                            vehicleManager.updateVehicle(regToUpdate, newMake, newModel, newYear, newOwnerId);
                            System.out.println("Vehicle updated successfully!");
                        }
                        break;
                    case 4:
                        System.out.print("Enter Registration Number to delete: ");
                        String regToDelete = scanner.nextLine();
                        vehicleManager.deleteVehicle(regToDelete);
                        System.out.println("Delete request processed.");
                        break;
                    case 5:
                        System.out.print("Enter Owner's Customer ID to find their vehicles: ");
                        int searchOwnerId = scanner.nextInt();
                        scanner.nextLine();
                        List<Vehicle> ownerVehicles = vehicleManager.getVehiclesByOwner(searchOwnerId);
                        if (ownerVehicles.isEmpty()) {
                            System.out.println("No vehicles found for this owner.");
                        } else {
                            for (Vehicle v : ownerVehicles) {
                                System.out.println(v);
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void handleServiceMenu() {
        while (true) {
            System.out.println("\n-- Service Management --");
            System.out.println("1. Book a New Service");
            System.out.println("2. View All Services (Chronological)");
            System.out.println("3. Update Service");
            System.out.println("4. Delete a Service");
            System.out.println("5. View Service History for a Vehicle");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 6) break;

                switch (choice) {
                    case 1:
                        System.out.print("Enter Vehicle Registration Number: ");
                        String regNo = scanner.nextLine();
                        if (vehicleManager.getVehicle(regNo) == null) {
                            System.out.println("Error: No vehicle exists with this registration.");
                            break;
                        }
                        System.out.print("Enter Service Type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Mechanic Name: ");
                        String mechanic = scanner.nextLine();
                        System.out.print("Enter Service Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Cost: ");
                        double cost = scanner.nextDouble();
                        scanner.nextLine();
                        serviceManager.addService(regNo, type, mechanic, date, cost);
                        System.out.println("Service booked successfully!");
                        break;
                    case 2:
                        System.out.println("\n-- All Service Records --");
                        for (Service s : serviceManager.getAllServices()) {
                            System.out.println(s);
                            System.out.println("--------------------");
                        }
                        break;
                    case 3:
                        System.out.print("Enter Service ID to update: ");
                        int serviceIdToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        if (serviceManager.getServiceById(serviceIdToUpdate) == null) {
                            System.out.println("Service not found.");
                        } else {
                            System.out.print("Enter new Vehicle Registration Number: ");
                            String newRegNo = scanner.nextLine();
                            System.out.print("Enter new Service Type: ");
                            String newType = scanner.nextLine();
                            System.out.print("Enter new Mechanic Name: ");
                            String newMechanic = scanner.nextLine();
                            System.out.print("Enter new Service Date (YYYY-MM-DD): ");
                            String newDate = scanner.nextLine();
                            System.out.print("Enter new Cost: ");
                            double newCost = scanner.nextDouble();
                            scanner.nextLine();
                            serviceManager.updateService(serviceIdToUpdate, newRegNo, newType, newMechanic, newDate, newCost);
                            System.out.println("Service updated successfully!");
                        }
                        break;
                    case 4:
                        System.out.print("Enter Service ID to delete: ");
                        int serviceIdToDelete = scanner.nextInt();
                        scanner.nextLine();
                        serviceManager.deleteService(serviceIdToDelete);
                        System.out.println("Delete request processed.");
                        break;
                    case 5:
                        System.out.print("Enter Vehicle Registration Number to view history: ");
                        String historyRegNo = scanner.nextLine();
                        List<Service> history = serviceManager.viewHistoryForVehicle(historyRegNo);
                        if (history.isEmpty()) {
                            System.out.println("No service history found for this vehicle.");
                        } else {
                            System.out.println("\n-- Service History for " + historyRegNo + " --");
                            for (Service s : history) {
                                System.out.println(s);
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number or cost.");
                scanner.nextLine();
            }
        }
    }
}