package logic.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.InputStream;

//Used for creating observable Invoice objects for use in the GUI.
public class ObservableInvoice {
    //Alle
    private IntegerProperty invoiceNumber;
    private IntegerProperty customerCvr;
    private StringProperty invoiceDate;
    private StringProperty paymentDate;
    private IntegerProperty fileId;
    private IntegerProperty userId;
    private InputStream inputStream;
    private StringProperty filePath;
    private StringProperty fileName;
    private StringProperty customerName;


    public ObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId) {
        this.invoiceNumber = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerCvr = new SimpleIntegerProperty();
        this.invoiceDate = new SimpleStringProperty();
        this.paymentDate = new SimpleStringProperty();
        this.fileId = new SimpleIntegerProperty();
        this.userId = new SimpleIntegerProperty();

        setInvoiceNumber(invoiceNumber);
        setCustomerCvr(customerCvr);
        setInvoiceDate(invoiceDate);
        setPaymentDate(paymentDate);
        setUserId(userId);
        setFileId(fileId);

    }

    //Used then mapping Invoice to observable invoice for use in Observable list in the GUI
    public ObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String fileName) {
        this.invoiceNumber = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerCvr = new SimpleIntegerProperty();
        this.invoiceDate = new SimpleStringProperty();
        this.paymentDate = new SimpleStringProperty();
        this.userId = new SimpleIntegerProperty();
        this.fileId = new SimpleIntegerProperty();
        this.fileName = new SimpleStringProperty();

        setInvoiceNumber(invoiceNumber);
        setCustomerCvr(customerCvr);
        setInvoiceDate(invoiceDate);
        setPaymentDate(paymentDate);
        setUserId(userId);
        setFileId(fileId);
        setFileName(fileName);

    }
    public ObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, String fileName, InputStream inputStream) {
        this.invoiceNumber = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerCvr = new SimpleIntegerProperty();
        this.invoiceDate = new SimpleStringProperty();
        this.paymentDate = new SimpleStringProperty();
        this.fileName = new SimpleStringProperty();
        this.inputStream = inputStream;

        setInvoiceNumber(invoiceNumber);
        setCustomerCvr(customerCvr);
        setInvoiceDate(invoiceDate);
        setPaymentDate(paymentDate);
        setFileName(fileName);
        setInputStream(inputStream);

    }
    public ObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, String fileName) {
        this.invoiceNumber = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerCvr = new SimpleIntegerProperty();
        this.invoiceDate = new SimpleStringProperty();
        this.paymentDate = new SimpleStringProperty();
        this.fileName = new SimpleStringProperty();

        setInvoiceNumber(invoiceNumber);
        setCustomerCvr(customerCvr);
        setInvoiceDate(invoiceDate);
        setPaymentDate(paymentDate);
        setFileName(fileName);

    }

    //Used when mapping observable Invoice to Invoice for the data layer.
    public ObservableInvoice(int invoiceNumber, String customerName, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String fileName) {
        this.invoiceNumber = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerCvr = new SimpleIntegerProperty();
        this.invoiceDate = new SimpleStringProperty();
        this.paymentDate = new SimpleStringProperty();
        this.userId = new SimpleIntegerProperty();
        this.fileId = new SimpleIntegerProperty();
        this.fileName = new SimpleStringProperty();

        setInvoiceNumber(invoiceNumber);
        setCustomerName(customerName);
        setCustomerCvr(customerCvr);
        setInvoiceDate(invoiceDate);
        setPaymentDate(paymentDate);
        setUserId(userId);
        setFileId(fileId);
        setFileName(fileName);

    }

    public int getInvoiceNumber() {
        return invoiceNumber.get();
    }

    public IntegerProperty invoiceNumberProperty() {
        return invoiceNumber;
    }

    public String getCustomerName() {return customerName.get(); }

    public StringProperty customerNameProperty() {return customerName; }

    public int getCustomerCvr() {
        return customerCvr.get();
    }

    public IntegerProperty customerCvrProperty() {
        return customerCvr;
    }

    public String getInvoiceDate() {
        return invoiceDate.get();
    }

    public StringProperty invoiceDateProperty() {
        return invoiceDate;
    }

    public String getPaymentDate() {
        return paymentDate.get();
    }

    public StringProperty paymentDateProperty() {
        return paymentDate;
    }

    public int getFileId() {
        return fileId.get();
    }

    public IntegerProperty fileIdProperty() {
        return fileId;
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFilePath() {
        return filePath.get();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public String getFileName() {return fileName.get();}

    public StringProperty fileNameProperty() {return fileName;}

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber.set(invoiceNumber);
    }

    public void setCustomerCvr(int customerCvr) {
        this.customerCvr.set(customerCvr);
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate.set(invoiceDate);
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate.set(paymentDate);
    }

    public void setFileId(int fileId) {
        this.fileId.set(fileId);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public void setFileName(String fileName) {this.fileName.set(fileName); }

    public void setCustomerName(String customerName) {this.customerName.set(customerName); }
}