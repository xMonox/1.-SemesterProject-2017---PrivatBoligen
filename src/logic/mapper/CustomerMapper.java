package logic.mapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.entities.Customer;
import logic.entities.ObservableCustomer;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

//This class is used for mapping Objects. GUI uses objects with Properties, and the datalayer uses "normal" objects.
public class CustomerMapper {
    //Julie, Svend, Bertram
    //This method takes a list of Customers, and returns a list of ObservableCustomers
    public List<ObservableCustomer> mapCustomerList(List<Customer> customerList) {
        List<ObservableCustomer> customerResult = new LinkedList<>();
        Iterator<Customer> iterator = customerList.iterator();

        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            customerResult.add(new ObservableCustomer(customer.getCvr(), customer.getName(), customer.getAddress(), customer.getZipcode(), customer.getCity(), customer.getContact(), customer.getPhoneNumber(), customer.getEmail()));
        }
        return customerResult;
    }

    //Julie, Bertram, Svend
    //This method takes a Customer object, and returns an ObservableCustomer
    public ObservableCustomer mapObservableCustomer(Customer customer) {
        return new ObservableCustomer(customer.getCvr(), customer.getName(), customer.getAddress(), customer.getZipcode(), customer.getCity(), customer.getContact(), customer.getPhoneNumber(), customer.getEmail());
    }

    //Julie, Bertram, Svend
    //This method takes an Observable Customer object and returns a Customer object.
    public Customer mapCustomer(ObservableCustomer observableCustomer) {
        return new Customer(observableCustomer.getCvr(), observableCustomer.getName(), observableCustomer.getAddress(), observableCustomer.getZipcode(), observableCustomer.getCity(), observableCustomer.getContact(), observableCustomer.getPhoneNumber(), observableCustomer.getEmail());
    }

    //Jesper
    //This method takes a list of Customers and returns an Observable List of ObservableCustomer objects.
    public ObservableList<ObservableCustomer> mapCustomerAndCvrList(List<Customer> customerList) {
        ObservableList<ObservableCustomer> customerResult = FXCollections.observableArrayList();
        Iterator<Customer> iterator = customerList.iterator();

        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            customerResult.add(new ObservableCustomer(customer.getCvr(), customer.getName()));
        }
        return customerResult;
    }
}
