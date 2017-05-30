package logic;

import logic.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

//Sets the behavior of Controller.
public interface IController {
    //Alle har arbejdet her
    User loginUser(String username, String password);
    void updateUserPassword(int userId, String password);
    User getActiveUser();
    User createUser(String username, String password, String role);
    User setUser(int userId);
    Invoice createInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName) throws FileNotFoundException;
    ObservableInvoice createObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName) throws FileNotFoundException;
    Customer createCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email);
    List<ObservableInvoice> getUserObservableInvoices(int userId);
    List<ObservableInvoice> getUserObservableInvoicesBetweenDates(int userId, String dateFrom, String dateTo);
    List<ObservableUser> getAllObservableUsers();
    List<ObservableCustomer> getObservableCustomers();
    void deleteInvoiceById(int invoiceNumber);
    ObservableCustomer createObservableCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email);
    String getCityFromZipcode(int zipcode);
    void deleteCustomerByCvr(int cvr);
    LocalDate parseDateString(String dateString);
    String parseLocalDateToString(LocalDate localDate);
    void saveFileToDisc(int fileId, File file);
    void updateObservableCustomer(ObservableCustomer customer, int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email);
    List<ObservableCustomer> searchCustomer(String searchString);
    List<ObservableInvoice> searchInvoice(String searchString);
    void updateObservableInvoice(ObservableInvoice invoice, int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, String fileName, String filePath) throws FileNotFoundException;
    List<ObservableUser> searchUser (String searchString);
    List getObservableCustomerAndCvr();
    Boolean isAdmin();
    Boolean isOtherAdmin(ObservableUser observableUser);

}