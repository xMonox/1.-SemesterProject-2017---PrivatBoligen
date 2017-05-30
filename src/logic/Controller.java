package logic;

import dataaccess.IDataAccessFacade;
import dataaccess.db.FacadeDatabase;
import javafx.collections.ObservableList;
import logic.entities.*;
import logic.mapper.CustomerMapper;
import logic.mapper.InvoiceMapper;
import logic.mapper.UserMapper;
import logic.repositories.CustomerRepository;
import logic.repositories.FileRepository;
import logic.repositories.InvoiceRepository;
import logic.services.*;
import logic.repositories.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

//Used to pass around Objects and dependencies between layers. Gets behavior from iController interface.
public class Controller implements IController {
    //Alle har lavet metoder til controlleren
    private IDataAccessFacade iDataAccessFacade;
    private InvoiceMapper invoiceMapper;
    private UserMapper userMapper;
    private CustomerMapper customerMapper;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private InvoiceRepository invoiceRepository;
    private CustomerRepository customerRepository;
    private LoginService loginService;
    private PasswordService passwordService;
    private DateService dateService;
    private FileService fileService;


    public Controller() {
        this.iDataAccessFacade      = new FacadeDatabase(Constants.DB_DRIVER, Constants.DB_USER, Constants.DB_PASSWORD);
        this.passwordService        = new PasswordService(new PasswordEncryption());
        this.dateService            = new DateService();
        this.fileService            = new FileService();
        this.customerMapper         = new CustomerMapper();
        this.invoiceMapper          = new InvoiceMapper(dateService);
        this.userMapper             = new UserMapper();
        this.userRepository         = new UserRepository(iDataAccessFacade, passwordService, userMapper);
        this.invoiceRepository      = new InvoiceRepository(iDataAccessFacade, new FileService(), dateService, invoiceMapper);
        this.fileRepository         = new FileRepository(iDataAccessFacade, fileService);
        this.customerRepository     = new CustomerRepository(iDataAccessFacade, customerMapper);
        this.loginService           = new LoginService(userRepository, iDataAccessFacade, passwordService);
    }

    /*

    USER STUFF
     */
    @Override
    public User loginUser(String username, String password) {
        return loginService.loginUser(username, password);
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        userRepository.updateUserPassword(userId, password);
    }

    @Override
    public User getActiveUser() {
        return userRepository.getUser();
    }

    @Override
        public User createUser(String username, String password, String role) {
        return userRepository.createUser(username, password, role);
    }

    @Override
    public User setUser(int userId) {
        return loginService.setUserFromId(userId);
    }

    @Override
    public List<ObservableUser> getAllObservableUsers() {
        return userRepository.getAllObservableUsers();
    }

    @Override
    public List<ObservableUser> searchUser(String searchString) {
        return userRepository.searchUser(searchString);
    }

    @Override
    public Boolean isAdmin() {
        return loginService.isAdmin();
    }

    @Override
    public Boolean isOtherAdmin(ObservableUser observableUser) {
        return userRepository.isOtherAdmin(observableUser);
    }


    /*

    INVOICE STUFF
     */
    @Override
    public Invoice createInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName) throws FileNotFoundException {
        return invoiceRepository.createInvoice(invoiceNumber, customerCvr, invoiceDate, paymentDate, userId, fileId, filePath, fileName);
    }

    @Override
    public ObservableInvoice createObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName) throws FileNotFoundException {
        return invoiceRepository.createObservableInvoice(invoiceNumber, customerCvr, invoiceDate, paymentDate, userId, fileId, filePath, fileName);
    }

    public void updateObservableInvoice(ObservableInvoice invoice, int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, String fileName, String filePath) throws FileNotFoundException {
         invoiceRepository.updateObservableInvoice(invoice, invoiceNumber, customerCvr, invoiceDate, paymentDate, fileName, filePath);
    }



    @Override
    public List<ObservableInvoice> getUserObservableInvoices(int userId) {
        return invoiceRepository.getUserObserverableInvoices(userId);
    }

    @Override
    public List<ObservableInvoice> getUserObservableInvoicesBetweenDates(int userId, String dateFrom, String dateTo) {
        return invoiceRepository.getUserObservableInvoicesBetweenDates(userId, dateFrom, dateTo);
    }

    public void deleteInvoiceById(int invoiceNumber){ invoiceRepository.deleteInvoiceById(invoiceNumber);
    }

    @Override
    public List<ObservableInvoice> searchInvoice(String searchString) {
        return invoiceRepository.searchInvoice(searchString);
    }

    /*

    CUSTOMER STUFF
     */

    @Override
    public List<ObservableCustomer> getObservableCustomers() {
        return customerRepository.getObservableCustomers();
    }


    @Override
    public ObservableCustomer createObservableCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email) {
        return customerRepository.createObservableCustomer(cvr, name, address, zipcode, city, contact, phoneNumber, email);
    }

    @Override
    public String getCityFromZipcode(int zipcode) {
        return customerRepository.getCityFromZipcode(zipcode);
    }

    @Override
    public Customer createCustomer(int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email) {
        return customerRepository.createCustomer(cvr, name, address, zipcode, city, contact, phoneNumber, email);
    }

    @Override
    public void deleteCustomerByCvr(int cvr) {
        customerRepository.deleteCustomerByCvr(cvr);
    }

    @Override
    public LocalDate parseDateString(String dateString) {
        return dateService.formatStringToLocalDate(dateString);
    }

    @Override
    public String parseLocalDateToString(LocalDate localDate) {
        return dateService.formatLocalDateToString(localDate);
    }


    @Override
    public void saveFileToDisc(int fileId, File destination) {
        fileRepository.saveFileToDisc(fileId, destination);
    }

    @Override
    public void updateObservableCustomer(ObservableCustomer customer, int cvr, String name, String address, int zipcode, String city, String contact, int phoneNumber, String email) {
        customerRepository.updateObservableCustomer(customer, cvr, name, address, zipcode, city, contact, phoneNumber, email);
    }

    @Override
    public List<ObservableCustomer> searchCustomer(String searchString) {
        return customerRepository.searchCustomer(searchString);
    }

    @Override
    public ObservableList getObservableCustomerAndCvr() {
        return customerRepository.getObservableCustomerAndCvr();
    }



}





