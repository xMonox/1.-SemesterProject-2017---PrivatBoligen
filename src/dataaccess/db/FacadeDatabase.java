package dataaccess.db;

import dataaccess.IDataAccessFacade;
import dataaccess.db.queries.Read;
import dataaccess.db.queries.Write;
import dataaccess.dto.FileDTO;
import logic.entities.Customer;
import logic.entities.Invoice;
import logic.entities.User;

import java.util.List;

//Used to inject database connection and queries into Objects that depend on these. Behavior of this class is set by
//iDataAccessFacade
public class FacadeDatabase implements IDataAccessFacade {
    //Alle har arbejdet her
    DBConnection dbcon;
    Read read;
    Write write;

    public FacadeDatabase(String url, String user, String password) {
        this.dbcon = new DBConnection(url, user, password);
        this.read = new Read(dbcon);
        this.write = new Write(dbcon);
    }

    /*USER STUFF*/
    @Override
    public User fetchUser(String username, String password) {
        return read.fetchUser(username, password);
    }

    @Override
    public User getUserById(int userId) {
        return read.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() { return read.getAllUsers(); }

    @Override
    public List<User> searchUser(String searchString) {
        return read.searchUser(searchString);
    }

    @Override
    public User createUser(User user) {
        return write.createUser(user);
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        write.updateUserPassword(userId, password);
    }


    /*INVOICE STUFF*/
    @Override
    public List<Invoice> getUserInvoices(int userId) {
        return read.getUserInvoices(userId);
    }

    @Override
    public List<Invoice> getUserInvoicesBetweenDates(int userId, String dateFrom, String dateTo) {
        return read.getUserInvoicesBetweenDates(userId, dateFrom, dateTo);
    }

    @Override
    public List<Invoice> searchInvoice(String searchString) {
        return read.searchInvoice(searchString);
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return write.createInvoice(invoice);
    }

    @Override
    public void deleteInvoiceById(int invoiceNumber) {
        write.deleteInvoiceById(invoiceNumber);
    }

    @Override
    public void updateInvoice(int tempInvoiceNumber, Boolean hasInputStream, Invoice invoice) {
        write.updateInvoice(tempInvoiceNumber, hasInputStream, invoice);
    }

    /*FILE STUFF*/
    @Override
    public FileDTO getFile(int fileId) {
        return read.getFile(fileId);
    }

    /*CUSTOMER STUFF*/
    @Override
    public List<Customer> getAllCustomers() {
        return read.getAllCustomers();
    }

    @Override
    public List getCustomerAndCvr() {
        return read.getCustomerAndCvr();
    }

    @Override
    public List<Customer> searchCustomer(String searchString) {
        return read.searchCustomer(searchString);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return write.createCustomer(customer);
    }

    @Override
    public void deleteCustomerByCvr(int cvr) {
        write.deleteCustomerByCvr(cvr);
    }

    @Override
    public void updateCustomer(int tempCvr, Customer customer) {
        write.updateCustomer(tempCvr, customer);
    }

    @Override
    public String getCityFromZipcode(int zipcode) {
        return read.getCityFromZipcode(zipcode);
    }



    //public void updateInvoice(int tempInvoiceNUmber, Boolean testForInputStream, Invoice invoice) {queries.updateInvoice(tempInvoiceNUmber, testForInputStream, invoice); }
}
