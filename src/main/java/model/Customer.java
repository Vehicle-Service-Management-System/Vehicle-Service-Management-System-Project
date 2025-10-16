package model;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public Customer(String id, String name, String email, String phoneNumber, String address ){
        this.id =id;
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }
    public String getId(){
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
    
}