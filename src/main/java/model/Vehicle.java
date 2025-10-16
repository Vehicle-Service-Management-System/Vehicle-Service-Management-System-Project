package model;

public class Vehicle {
    private String registrationNumber;
    private String make;
    private String model;
    private int year;   
    private String ownerId;

    
    public Vehicle(String registrationNumber2, String make2, String model2, int year2, String ownerId) {
        this.registrationNumber = registrationNumber2;
        this.make = make2;
        this.model = model2;
        this.year = year2;
        this.ownerId = ownerId;
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

    public String getOwnerId() {
        return ownerId;
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
    @Override
    public String toString() {
        return "Registration Number: " + registrationNumber + ", Make: " + make + ", Model: " + model + ", Year: " + year + ",Owner ID: " + ownerId;
    }
    public String toFileString() {
        return registrationNumber + "," + make + "," + model + "," + year + "," + ownerId;
    }
    public String toDisplayString() {
        return "Registration Number: " + registrationNumber + "\nMake: " + make + "\nModel: " + model + "\nYear: " + year + "\nOwner ID:"+ownerId;
    }
    public String toShortString() {
        return "Reg No: " + registrationNumber + ", Make: " + make + ", Model: " + model;
    }  
    public String toCompactString() {
        return registrationNumber + " - " + make + " " + model;
    }
    public String toSummString() {
        return registrationNumber + " (" + make + " " + model + ", " + year + ")";
    }
    

}   