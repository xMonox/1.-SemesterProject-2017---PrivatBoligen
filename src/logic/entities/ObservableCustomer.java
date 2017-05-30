package logic.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//Used for creating Custumer objects for use in Observable list.
public class ObservableCustomer {
    //Alle
    private IntegerProperty cvr;
    private StringProperty name;
    private StringProperty address;
    private IntegerProperty zipcode;
    private StringProperty city;
    private StringProperty contact;
    private IntegerProperty phoneNumber;
    private StringProperty email;

    //Used when mapping Customer to ObservableCustomer. Used in ComboBox on the "Faktura" page.
    public ObservableCustomer(int cvr, String name) {
        this.cvr            = new SimpleIntegerProperty();
        this.name           = new SimpleStringProperty();
        setCvr(cvr);
        setName(name);
    }

    //used when mapping Customer to ObservableCustumer. Used by the GUI whenever a Customer is listed.
    public ObservableCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email){
        this.cvr            = new SimpleIntegerProperty();
        this.name           = new SimpleStringProperty();
        this.address        = new SimpleStringProperty();
        this.zipcode        = new SimpleIntegerProperty();
        this.city           = new SimpleStringProperty();
        this.contact        = new SimpleStringProperty();
        this.phoneNumber    = new SimpleIntegerProperty();
        this.email          = new SimpleStringProperty();

        setCvr(cvr);
        setName(name);
        setAddress(address);
        setZipcode(zipcode);
        setCity(city);
        setContact(contact);
        setPhoneNumber(phoneNumber);
        setEmail(email);

    }

    public int getCvr() {
        return cvr.get();
    }

    public IntegerProperty cvrProperty() {
        return cvr;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public int getZipcode() {
        return zipcode.get();
    }

    public IntegerProperty zipcodeProperty() {
        return zipcode;
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setCvr(int cvr) {
        this.cvr.set(cvr);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setZipcode(int zipcode) {
        this.zipcode.set(zipcode);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

}
