package logic.repositories;

import dataaccess.IDataAccessFacade;
import logic.entities.Invoice;
import logic.entities.ObservableInvoice;
import logic.mapper.InvoiceMapper;
import logic.services.DateService;
import logic.services.FileService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class InvoiceRepository {
    private IDataAccessFacade dataAccess;
    private FileService fileService;
    private DateService dateService;
    private InvoiceMapper mapper;

    //Alle har været over metoderne i repositoriet
    public InvoiceRepository(IDataAccessFacade dataAccess, FileService fileService, DateService dateService, InvoiceMapper mapper) {
        this.dataAccess = dataAccess;
        this.fileService = fileService;
        this.dateService = dateService;
        this.mapper = mapper;
    }

    public Invoice createInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName)
            throws FileNotFoundException {
        InputStream inputStream = fileService.getFileStream(filePath);
        Invoice invoice;
        if (inputStream != null) {
            invoice = new Invoice(invoiceNumber, customerCvr, invoiceDate, paymentDate, userId, inputStream, fileName);
        } else {
            invoice = new Invoice(invoiceNumber, customerCvr, invoiceDate, paymentDate, userId, fileId, fileName);
        }
        return dataAccess.createInvoice(invoice);
    }

    public ObservableInvoice createObservableInvoice(int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, int userId, int fileId, String filePath, String fileName) throws FileNotFoundException {
        Invoice invoice = createInvoice(invoiceNumber, customerCvr, invoiceDate, paymentDate, userId, fileId, filePath, fileName);
        ObservableInvoice observableInvoice = mapper.mapObservableInvoice(invoice);
        return observableInvoice;
    }

    private List<Invoice> getUserInvoices(int userId){
        return dataAccess.getUserInvoices(userId);
    }

    private List<Invoice> getUserInvoicesBetweenDates(int userId, String dateFrom, String dateTo){
        return dataAccess.getUserInvoicesBetweenDates(userId, dateFrom, dateTo);
    }

    public List<ObservableInvoice> getUserObserverableInvoices(int userId) {
        List<Invoice> list = getUserInvoices(userId);
        List<ObservableInvoice> obslist = mapper.mapInvoiceList(list);
        return obslist;
    }

    public List<ObservableInvoice> getUserObservableInvoicesBetweenDates(int userId, String dateFrom, String dateTo) {
        List<Invoice> list = getUserInvoicesBetweenDates(userId, dateFrom, dateTo);
        List<ObservableInvoice> obslist = mapper.mapInvoiceList(list);
        return obslist;
    }

    public void deleteInvoiceById (int invoiceNumber){
         dataAccess.deleteInvoiceById(invoiceNumber);
    }

    public List<ObservableInvoice> searchInvoice(String searchString){
        List<Invoice> list = dataAccess.searchInvoice(searchString);
        return mapper.mapInvoiceList(list);
    }

    public void updateObservableInvoice(ObservableInvoice observableInvoice, int invoiceNumber, int customerCvr, String invoiceDate, String paymentDate, String fileName, String filePath) throws FileNotFoundException{
        int tempInvoiceNumber = observableInvoice.getInvoiceNumber();
        boolean hasInputStream = false;

        observableInvoice.setInvoiceNumber(invoiceNumber);
        observableInvoice.setCustomerCvr(customerCvr);
        observableInvoice.setInvoiceDate(dateService.formatLocalDateStringToMysqlDate(invoiceDate));
        observableInvoice.setPaymentDate(dateService.formatLocalDateStringToMysqlDate(paymentDate));
        observableInvoice.setFileName(fileName);


        Invoice invoice;
        if (filePath.equals("")) {
            invoice = mapper.mapInvoiceWithOutInputStream(observableInvoice);
        } else {
            hasInputStream = true;
            InputStream inputStream = fileService.getFileStream(filePath);
            observableInvoice.setInputStream(inputStream);
            invoice = mapper.mapInvoice(observableInvoice);
        }
        dataAccess.updateInvoice(tempInvoiceNumber, hasInputStream, invoice);
    }
}
