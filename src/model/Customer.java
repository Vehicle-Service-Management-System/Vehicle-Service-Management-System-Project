package model;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public Customer(int id, String name, String email, String phoneNumber, String address ){
        this.id =id;
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getAddress(){
        return address;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public void setAddress(String address){
        this.address=address;
    }

    @Override
    public String toString(){
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Address: " + address;
    }
    public String toFileString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toDisplayString(){
        return  "Customer ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    } 
    public String toShortString(){
        return "Customer ID: " + id + ", Name: " + name;
    }
    public String toCompactString(){
        return id + " - " + name;
    }
    public String toSummaryString(){
        return "Customer ID: " + id + ", Name: " + name;
    }
    public String toDetailedString(){
        return "Customer ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    }
    public String toInfoString(){
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Address: " + address;
    }
    public String toFullString(){
        return "Customer ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    }
    public String toVerboseString(){
        return "Customer ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    }
    public String toCompleteString(){
        return "Customer ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    }
    public String toConciseString(){
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Address: " + address;
    }   
    public String toBriefString(){
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Address: " + address;
    }
    public String toSnapshotString(){
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Address: " + address;
    }   
    public String toRecordString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toEntryString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toLogString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toDataString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toExportString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toImportString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toBackupString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }   
    public String toRestoreString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }
    public String toSyncString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }   
    public String toArchiveString(){
        return id + "," + name + "," + email + "," + phoneNumber + "," + address;
    }   
    public String fromFileString(String fileString){
        String[] parts = fileString.split(",");
        if(parts.length == 5){
            this.id = Integer.parseInt(parts[0]);
            this.name = parts[1];
            this.email = parts[2];
            this.phoneNumber = parts[3];
            this.address = parts[4];
        }
        return fileString;
    }
    
}