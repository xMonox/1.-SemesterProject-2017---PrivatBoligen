package dataaccess;
import dataaccess.dto.FileDTO;
import logic.entities.*;

import java.util.List;


//Sets the behavior of FacadeDatabase and is also used for dependency injection.
public interface IDataAccessFacade {
    //Alle har arbejdet her
    User fetchUser(String username, String password);
    User getUserById(int userId);
    List<User> getAllUsers();
    List<User> searchUser(String searchString);
    User createUser(User user);
    void updateUserPassword(int userId, String password);

    List<Invoice> getUserInvoices(int userId);
    List<Invoice> getUserInvoicesBetweenDates(int userId, String dateFrom, String dateTo);
    List<Invoice> searchInvoice(String searchString);
    Invoice createInvoice(Invoice invoice);
    void deleteInvoiceById(int invoiceNumber);
    void updateInvoice(int tempInvoiceNumber, Boolean hasInputStream, Invoice invoice);

    FileDTO getFile(int fileId);

    List<Customer> getAllCustomers();
    List<Customer> getCustomerAndCvr();
    List<Customer> searchCustomer(String searchString);
    Customer createCustomer(Customer customer);
    void deleteCustomerByCvr(int cvr);
    void updateCustomer(int tempCvr, Customer customer);

    String getCityFromZipcode(int zipcode);




}
