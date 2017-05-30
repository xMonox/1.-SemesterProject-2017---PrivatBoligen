package logic.repositories;

import dataaccess.IDataAccessFacade;
import javafx.collections.ObservableList;
import logic.entities.Customer;
import logic.entities.ObservableCustomer;
import logic.mapper.CustomerMapper;

import java.util.List;


public class CustomerRepository {
    private IDataAccessFacade dataAccess;
    private CustomerMapper mapper;

    //Alle har været over metoderne i repositoriet
    //Used in the Controller
    public CustomerRepository(IDataAccessFacade dataAccess, CustomerMapper mapper) {
        this.dataAccess = dataAccess;
        this.mapper = mapper;
    }

    //Used to create Customer objects. Creates the object and returns it with dataAccess so it can be used in queries
    public Customer createCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email) {
        Customer customer = new Customer(cvr, name, address, zipcode, city, contact, phoneNumber, email);
        return dataAccess.createCustomer(customer);
    }

    //Creates a Custumer object, calls the mapper with the object as a parameter, maps it to an ObservableCustumer object, and returns it.
    public ObservableCustomer createObservableCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email){
        Customer customer = createCustomer(cvr, name, address, zipcode, city, contact, phoneNumber, email);
        ObservableCustomer observableCustomer = mapper.mapObservableCustomer(customer);
        return observableCustomer;
    }

    //Returns a list of all Customers.
    public List<Customer> getAllCustomers(){
        return dataAccess.getAllCustomers();
    }

    //Used to get City from zipcode, when zip is entered in the GUI.
    public String getCityFromZipcode(int zipcode){ return dataAccess.getCityFromZipcode(zipcode); }

    //Used to delete customer by cvr (cvr is primary key in the Customer table)
    public void deleteCustomerByCvr(int cvr){
        dataAccess.deleteCustomerByCvr(cvr);
    }

    //Gets all customers, returns an Observable list of ObservableCustomers via the mapper.
    public List<ObservableCustomer> getObservableCustomers() {
        List<Customer> list = getAllCustomers();

        return mapper.mapCustomerList(list);
    }

    //Gets a list of Customers with only name and cvr, and returns an observable list og ObservableCustomers via the mapper.
    public ObservableList<ObservableCustomer> getObservableCustomerAndCvr() {
        List<Customer> list = dataAccess.getCustomerAndCvr();

        return mapper.mapCustomerAndCvrList(list);
    }

    //Takes an ObservableCustomer object and relevant Strings / ints. Sets the Object with the received Strings / ints
    //calls the mapper to create a Customer object from the ObservableCustomer, then sends the updated object to the database
    public void updateObservableCustomer(ObservableCustomer observableCustomer, int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email){
        int tempCvr = observableCustomer.getCvr();

        observableCustomer.setCvr(cvr);
        observableCustomer.setName(name);
        observableCustomer.setAddress(address);
        observableCustomer.setZipcode(zipcode);
        observableCustomer.setCity(city);
        observableCustomer.setContact(contact);
        observableCustomer.setPhoneNumber(phoneNumber);
        observableCustomer.setEmail(email);

        Customer customer = mapper.mapCustomer(observableCustomer);

        dataAccess.updateCustomer(tempCvr, customer);

    }

    //Takes a search string from the GUI searchfield, creates a List of matches from the database that returns a list of Customers.
    //Then returns a mapped list of ObservableCustomer objects.
    public List<ObservableCustomer> searchCustomer(String searchString){
        List<Customer> list = dataAccess.searchCustomer(searchString);

        return mapper.mapCustomerList(list);
    }

}
