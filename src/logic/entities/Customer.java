package logic.entities;

//Used for creating objects for the datalayer
public class Customer {
    //Alle
    private int cvr;
    private String name;
    private String address;
    private int zipcode;
    private String city;
    private String contact;
    private int phoneNumber;
    private String email;

    //Used for ComboBox in Customer scene. Only needs cvr and nane
    public Customer(int cvr, String name) {
        this.cvr = cvr;
        this.name = name;
    }

    //Used whenever we need to create a Customer object.
    public Customer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email) {
        this.cvr = cvr;
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.contact = contact;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public int getCvr() {
        return cvr;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getContact() {
        return contact;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

