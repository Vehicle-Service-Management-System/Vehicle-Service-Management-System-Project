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

}
