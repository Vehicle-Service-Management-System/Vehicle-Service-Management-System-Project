package model;

public class Service {
    private int serviceId;
    private String vehicleReg;
    private String serviceType;
    private String mechanic;
    private String serviceDate;
    private double cost;

    public Service(int serviceId, String vehicleReg, String serviceType, String mechanic, String serviceDate, double cost) {
        this.serviceId = serviceId;
        this.vehicleReg = vehicleReg;
        this.serviceType = serviceType;
        this.mechanic = mechanic;
        this.serviceDate = serviceDate;
        this.cost = cost;
    }
    public int getServiceId(){
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
    public String toSummaryStringShort(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Cost: " + cost;
    }
    public String toSummaryStringCompact(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }
    public String toSummaryStringFile(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanic + "," + serviceDate + "," + cost;
    }
    public String toSummaryStringFileShort(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + cost;
    }
    public String toSummaryStringFileCompact(){
        return serviceId + "," + vehicleReg + "," + serviceType;
    }
    public String toDetailedString(){
        return "Service ID: " + serviceId + "\nVehicle Reg: " + vehicleReg + "\nService Type: " + serviceType + "\nMechanic: " + mechanic + "\nService Date: " + serviceDate + "\nCost: " + cost;
    }
    public String toDetailedStringShort(){
        return "Service ID: " + serviceId + "\nVehicle Reg: " + vehicleReg + "\nService Type: " + serviceType + "\nCost: " + cost;
    }
    public String toDetailedStringCompact(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }
    public String toDetailedStringFile(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanic + "," + serviceDate + "," + cost;
    }
    public String toDetailedStringFileShort(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + cost;
    }
    public String toDetailedStringFileCompact(){
        return serviceId + "," + vehicleReg + "," + serviceType;
    }
    public String toLongString(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Mechanic: " + mechanic + ", Service Date: " + serviceDate + ", Cost: " + cost;
    }
    public String toLongStringShort(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Cost: " + cost;
    }
    public String toLongStringCompact(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }
    public String toLongStringFile(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanic + "," + serviceDate + "," + cost;
    }
    public String toLongStringFileShort(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + cost;
    }   
    public String toLongStringFileCompact(){
        return serviceId + "," + vehicleReg + "," + serviceType;
    }  
    public String toVeryLongString(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Mechanic: " + mechanic + ", Service Date: " + serviceDate + ", Cost: " + cost;
    }  
    public String toVeryLongStringShort(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Cost: " + cost;
    }   
    public String toVeryLongStringCompact(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    }   
    public String toVeryLongStringFile(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + mechanic + "," + serviceDate + "," + cost;
    }   
    public String toVeryLongStringFileShort(){
        return serviceId + "," + vehicleReg + "," + serviceType + "," + cost;
    }   
    public String toVeryLongStringFileCompact(){
        return serviceId + "," + vehicleReg + "," + serviceType;
    }
    public String toExtraLongString(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Mechanic: " + mechanic + ", Service Date: " + serviceDate + ", Cost: " + cost;
    }        
    public String toExtraLongStringShort(){
        return "Service ID: " + serviceId + ", Vehicle Reg: " + vehicleReg + ", Service Type: " + serviceType + ", Cost: " + cost;
    }  
    public String toExtraLongStringCompact(){
        return serviceId + " - " + vehicleReg + " - " + serviceType;
    } 
    public String fromFileString(String fileString){
        String[] parts = fileString.split(",");
        this.serviceId = Integer.parseInt(parts[0]);
        this.vehicleReg = parts[1];
        this.serviceType = parts[2];
        this.mechanic = parts[3];
        this.serviceDate = parts[4];
        this.cost = Double.parseDouble(parts[5]);
        return toString();
    }   
}