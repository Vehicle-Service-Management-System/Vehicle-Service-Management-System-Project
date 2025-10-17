package model;

public class Service {
    private String serviceId;
    private String vehicleReg;
    private String serviceType;
    private String mechanicId;
    private String serviceDate;
    private double cost;

    public Service(String serviceId, String vehicleReg, String serviceType, String mechanicId, String serviceDate, double cost) {
        this.serviceId = serviceId;
        this.vehicleReg = vehicleReg;
        this.serviceType = serviceType;
        this.mechanicId = mechanicId;
        this.serviceDate = serviceDate;
        this.cost = cost;
    }
    public String getServiceId(){
        return serviceId;
    }
    public String getVehicleReg(){
        return vehicleReg;
    }
    public String getServiceType(){
        return serviceType;
    }
    public String getMechanic(){
        return mechanicId;
    }
    public String getServiceDate(){
        return serviceDate;
    }
    public double getCost(){
        return cost;
    }
    public void setVehicleReg(String vehicleReg){
        this.vehicleReg=vehicleReg;
    }
    public void setServiceType(String serviceType){
        this.serviceType=serviceType;
    }
    public void setMechanic(String mechanicId){
        this.mechanicId=mechanicId;
    }
    public void setServiceDate(String serviceDate){
        this.serviceDate=serviceDate;
    }   
    public void setCost(double cost){
        this.cost=cost;
    }
    @Override
    public String toString(){
        return "Service Id:" + serviceId + ", Vehicle Reg:" + vehicleReg + ", Service Type:" + serviceType + ", Mechanic:" + mechanicId + ", Service Date:" + serviceDate + ", Cost:" + cost;
    }
    public String toFileString(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanicId + "," + serviceDate + "," + cost;
    }
    public String toDisplayString(){
        return "Service ID:" + serviceId + "\nVehicle Reg:" + vehicleReg + "\nService Type:" + serviceType + "\nMechanic:" + mechanicId + "\nService Date:" + serviceDate + "\nCost:" + cost;
    }
    public String toShortString(){
        return "Service Id:" + serviceId + ", Vehicle Reg:" + vehicleReg + ", Service Type:" + serviceType + ", Cost:" + cost;
    }
    public String toCompactString(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }
    public String toSummaryString(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Mechanic: " + mechanicId + ", Service Date: " + serviceDate + ", Cost: " + cost;
    }

}
