package model;

public class Service {
    private String serviceId;
    private String vehicleReg;
    private String serviceType;
    private String mechanic;
    private String serviceDate;
    private double cost;

    public Service(String serviceId, String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        this.serviceId = serviceId;
        this.vehicleReg = vehicleReg;
        this.serviceType = serviceType;
        this.mechanic = mechanic;
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
        return mechanic;
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
    public void setMechanic(String mechanic){
        this.mechanic=mechanic;
    }
    public void setServiceDate(String serviceDate){
        this.serviceDate=serviceDate;
    }   
    public void setCost(double cost){
        this.cost=cost;
    }
    @Override
    public String toString(){
        return "Service Id:" + serviceId + ", Vehicle Reg:" + vehicleReg + ", Service Type:" + serviceType + ", Mechanic:" + mechanic + ", Service Date:" + serviceDate + ", Cost:" + cost;
    }
    public String toFileString(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanic + "," + serviceDate + "," + cost;
    }
    public String toDisplayString(){
        return "Service ID:" + serviceId + "\nVehicle Reg:" + vehicleReg + "\nService Type:" + serviceType + "\nMechanic:" + mechanic + "\nService Date:" + serviceDate + "\nCost:" + cost;
    }
    public String toShortString(){
        return "Service Id:" + serviceId + ", Vehicle Reg:" + vehicleReg + ", Service Type:" + serviceType + ", Cost:" + cost;
    }
    public String toCompactString(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }
    public String toSummaryString(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Mechanic: " + mechanic + ", Service Date: " + serviceDate + ", Cost: " + cost;
    }

    public static Service fromCSV(String csv) {
        try {
        String[] parts = csv.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid CSV format for Service");
        }
        String serviceId = parts[0];
        String vehicleReg = parts[1];
        String serviceType = parts[2];
        String mechanic = parts[3];
        String serviceDate = parts[4];
        double cost = Double.parseDouble(parts[5]);
        return new Service(serviceId, vehicleReg, serviceType, mechanic, serviceDate, cost);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid number format in CSV for Service", e);
    }
    }

}
