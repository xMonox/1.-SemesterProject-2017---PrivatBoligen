package presentation;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import logic.entities.ObservableInvoice;

import java.util.List;
import java.util.function.Function;

/**
 * Created by lupus on 5/26/17.
 */
public class InvoiceTable {
    //Svend, Bertram
    public TableView createTable(ObservableList observableInvoiceList){
        TableView tableView = new TableView<>();
        tableView.setItems(FXCollections.observableList(observableInvoiceList));
        tableView.getColumns().add(columnInt("Faktura nr.", ObservableInvoice::invoiceNumberProperty));
        tableView.getColumns().add(column("Kundenavn", ObservableInvoice::customerNameProperty));
        tableView.getColumns().add(columnInt("CVR-nr.", ObservableInvoice::customerCvrProperty));
        tableView.getColumns().add(column("Oprettelsesdato",ObservableInvoice::invoiceDateProperty));
        tableView.getColumns().add(column("Betalingsdato",  ObservableInvoice::paymentDateProperty));
        tableView.getColumns().add(column("Fil", ObservableInvoice::fileNameProperty));
        return tableView;
    }
    private TableColumn<ObservableInvoice, String> column(String title, Function<ObservableInvoice, ObservableValue<String>> property) {
        TableColumn<ObservableInvoice, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setResizable(true);
        return col ;
    }
    private TableColumn<ObservableInvoice, Number> columnInt(String title, Function<ObservableInvoice, ObservableValue<Number>> property) {
        TableColumn<ObservableInvoice, Number> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(ComboBoxTableCell.forTableColumn(2, 3, 4, 5, 6));
        col.setResizable(true);
        return col ;
    }
}
