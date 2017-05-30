package logic.entities;

import java.io.InputStream;

//Used for creating Invoices for the datalayer
public class Invoice {
    //Alle
    private int invoiceNumber;
    private int customerCvr;
    private String invoiceDate;
    private String paymentDate;
    private int fileId;
    private int userId;
    private String fileName;
    private InputStream inputStream;
    private String customerName;


    public Invoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.userId = userId;
    }

    //Used to create an Invoice.
    public Invoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String fileName) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.fileName = fileName;
        this.userId = userId;
        this.fileId = fileId;

    }

    //Used to query the DB invoices created by the logged in user and for making search queries.
    public Invoice(int invoiceNumber, String customerName, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String fileName) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.fileName = fileName;
        this.userId = userId;
        this.fileId = fileId;

    }
    //Used to Map Invoice to ObservableInvoice with Inputstream.
    public Invoice(int invoiceNumber,  int customerCvr, String invoiceDate, String paymentDate, String fileName, InputStream inputStream) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.fileName = fileName;
        this.inputStream = inputStream;

    }

    //Used to map Invoice to ObservableInvoice without Inputstream
    public Invoice(int invoiceNumber,  int customerCvr, String invoiceDate, String paymentDate, String fileName) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.fileName = fileName;


    }


    public Invoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.fileId = fileId;
    }
    public Invoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, InputStream inputStream) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.inputStream = inputStream;
    }
    public Invoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, InputStream inputStream, String fileName) {
        this.invoiceNumber = invoiceNumber;
        this.customerCvr = customerCvr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public int getCustomerCvr() {
        return customerCvr;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public int getFileId() {
        return fileId;
    }

    public int getUserId() {
        return userId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFileName() { return fileName;  }
    public String getCustomerName() {return customerName; }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setCustomerCvr(int customerCvr) {
        this.customerCvr = customerCvr;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {this.fileName = fileName; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

}
