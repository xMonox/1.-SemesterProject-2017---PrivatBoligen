package logic.mapper;

import logic.entities.Invoice;
import logic.entities.ObservableInvoice;
import logic.services.DateService;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

//This class is used for mapping Objects. GUI uses objects with Properties, and the datalayer uses "normal" objects.
public class InvoiceMapper {
    private DateService dateService;

    public InvoiceMapper(DateService dateService) {
        this.dateService = dateService;
    }

    //Bertram
    //Takes a list of Invoice objects, maps it to a list of ObservableInvoice objects and returns it with MysqlDate formatted to LocalDate
    public List<ObservableInvoice> mapInvoiceList (List<Invoice> invoiceList){
        List<ObservableInvoice> invoiceResult = new LinkedList<>();
        Iterator<Invoice> iterator = invoiceList.iterator();
        while (iterator.hasNext()) {
            Invoice invoice = iterator.next();
            invoiceResult.add(new ObservableInvoice(
                    invoice.getInvoiceNumber(), invoice.getCustomerName(), invoice.getCustomerCvr(),
                    dateService.formatMysqlDateToLocalDateString(invoice.getInvoiceDate()),
                    dateService.formatMysqlDateToLocalDateString(invoice.getPaymentDate()),
                    invoice.getUserId(), invoice.getFileId(), invoice.getFileName()));
        }
        return invoiceResult;
    }
    //Bertram
    //Takes an Invoice object , and returns an ObservableInvoice object with MySqlDate formatted to LocalDate
    public ObservableInvoice mapObservableInvoice(Invoice invoice) {
        ObservableInvoice observableInvoice = new ObservableInvoice(
                invoice.getInvoiceNumber(), invoice.getCustomerCvr(),
                dateService.formatMysqlDateToLocalDateString(invoice.getInvoiceDate()),
                dateService.formatMysqlDateToLocalDateString(invoice.getPaymentDate()),
                invoice.getUserId(), invoice.getFileId(), invoice.getFileName());
        return observableInvoice;
    }
    //Bertram, Svend
    //Takes an ObservableInvoice object and returns an Invoice object.
    public Invoice mapInvoice(ObservableInvoice observableInvoice) {
        return new Invoice(observableInvoice.getInvoiceNumber(), observableInvoice.getCustomerCvr(), observableInvoice.getInvoiceDate(), observableInvoice.getPaymentDate(), observableInvoice.getFileName(), observableInvoice.getInputStream());
    }
    //Bertram, Svend
    //Takes an ObservableInvoice object and returns an Invoice object without InputSteam
    public Invoice mapInvoiceWithOutInputStream(ObservableInvoice observableInvoice) {
        return new Invoice(observableInvoice.getInvoiceNumber(), observableInvoice.getCustomerCvr(), observableInvoice.getInvoiceDate(), observableInvoice.getPaymentDate(), observableInvoice.getFileName());
    }

}
