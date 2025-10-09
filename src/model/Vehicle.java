package model;

public class Vehicle {
    private String registrationNumber;
    private String make;
    private String model;
    private int year;   
    private String ownerName;
    private String ownerContact;

    public Vehicle(String registrationNumber, String make, String model, int year, String ownerName, String ownerContact) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }  
    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public String getOwnerContact() {
        return ownerContact;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }
    @Override
    public String toString() {
        return "Registration Number: " + registrationNumber + ", Make: " + make + ", Model: " + model + ", Year: " + year + ", Owner Name: " + ownerName + ", Owner Contact: " + ownerContact;
    }
    public String toFileString() {
        return registrationNumber + "," + make + "," + model + "," + year + "," + ownerName + "," + ownerContact;
    }
    public String toDisplayString() {
        return "Registration Number: " + registrationNumber + "\nMake: " + make + "\nModel: " + model + "\nYear: " + year + "\nOwner Name: " + ownerName + "\nOwner Contact: " + ownerContact;
    }
    public String toShortString() {
        return "Reg No: " + registrationNumber + ", Make: " + make + ", Model: " + model;
    }  
    public String toCompactString() {
        return registrationNumber + " - " + make + " " + model;
    }
    public String getSummary() {
        return registrationNumber + " | " + make + " | " + model + " | " + year;
    } 
    public String getOwnerInfo() {
        return ownerName + " (" + ownerContact + ")";
    }
    public String getVehicleInfo() {
        return make + " " + model + " (" + year + ")";
    }
    public String getFullInfo() {
        return registrationNumber + " - " + make + " " + model + " (" + year + "), Owner: " + ownerName + " (" + ownerContact + ")";
    }
    public String getContactCard() {
        return "Owner: " + ownerName + "\nContact: " + ownerContact + "\nVehicle: " + make + " " + model + " (" + year + ")\nReg No: " + registrationNumber;
    } 
}
