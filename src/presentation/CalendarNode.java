package presentation;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.entities.ObservableInvoice;
import presentation.controllers.GuiController;
import logic.Controller;
import presentation.controllers.IGuiController;

import java.io.File;
import java.time.LocalDate;

public class CalendarNode {
    ObservableList<ObservableInvoice> observableInvoiceList;
    Stage primaryStage;
    IGuiController guiController;
    Controller controller;
    TableView<ObservableInvoice> tableView;
    DatePicker datePicker1, datePicker2;
    Label lblTitle, lblListSize, lblListDescription, lblDateFrom, lblDateTo;
    HBox hBox, hBoxRight;
    HBox hBoxContent;
    VBox vBoxLeft, vBoxRight;
    String userName;
    Button btnDownload;

    //Bertram
    public HBox getCalendar(GuiController guiController, Controller controller, Stage primaryStage) {
        this.guiController      = guiController;
        this.controller         = guiController.getController();
        this.primaryStage       = primaryStage;
        userName                = controller.getActiveUser().getUsername();
        datePicker1             = new DatePicker();
        datePicker2             = new DatePicker();
        hBox                    = new HBox();
        vBoxLeft                = new VBox();
        hBoxContent             = new HBox();
        lblTitle                = new Label();
        lblListDescription      = new Label("Resultater: ");
        lblListSize             = new Label();
        lblDateFrom             = new Label("Fra");
        lblDateTo               = new Label("Til");
        btnDownload             = new Button("Download");

        lblTitle.getStyleClass().add("labels");
        lblListDescription.getStyleClass().add("labels");
        lblListSize.getStyleClass().add("labels");
        lblDateFrom.getStyleClass().add("labels");
        lblDateTo.getStyleClass().add("labels");


        datePicker1.setMinWidth(200);
        datePicker2.setMinWidth(200);

        hBoxContent.setPadding(new Insets(50));

        LocalDate now = LocalDate.now();
        LocalDate aWeekAgo = now.minusDays(7);
        LocalDate aWeekAhead = now.plusDays(7);

        datePicker1.setValue(aWeekAgo);
        datePicker2.setValue(aWeekAhead);



        //observableInvoiceList.addListener(lblListSize.setText(observableInvoiceList.size()));

        observableInvoiceList = FXCollections.observableList(controller.getUserObservableInvoicesBetweenDates(controller.getActiveUser().getId(), datePicker1.getValue().toString(), datePicker2.getValue().toString()));
        tableView = new InvoiceTable().createTable(observableInvoiceList);

        IntegerBinding sizeProperty = Bindings.size(observableInvoiceList);
        lblListSize.textProperty().bind(sizeProperty.asString());
        hBox.getChildren().addAll(lblListDescription, lblListSize);
        datePicker1.setOnAction(e -> datePickerSet());
        datePicker2.setOnAction(e -> datePickerSet());
        btnDownload.setOnAction(e -> btnDownloadClicked());


        lblTitle.setText("Viser " + userName + "'s fakturaer med udløbsdato i perioden " + datePicker1.getValue() + " til " + datePicker2.getValue());
        tableView.setMinWidth(720);
        tableView.setMaxWidth(720);
        tableView.setMinHeight(500);
        tableView.setMaxHeight(500);
        vBoxRight = new VBox();
        vBoxRight.getChildren().addAll(lblTitle, tableView, btnDownload);
        vBoxRight.setAlignment(Pos.TOP_CENTER);
        vBoxRight.setSpacing(20);
        vBoxLeft.setPadding(new Insets(30));
        vBoxLeft.getChildren().addAll(lblDateFrom, datePicker1, lblDateTo, datePicker2, hBox);
        hBoxContent.getChildren().addAll(vBoxLeft, vBoxRight);
        return hBoxContent;
    }
    //Bertram
    private void datePickerSet() {
        observableInvoiceList.clear();
        observableInvoiceList.setAll(controller.getUserObservableInvoicesBetweenDates(controller.getActiveUser().getId(), datePicker1.getValue().toString(), datePicker2.getValue().toString()));
        tableView.setItems(observableInvoiceList);
        lblTitle.setText("Viser " + userName + "'s fakturaer med udløbsdato i perioden " + datePicker1.getValue() + " til " + datePicker2.getValue());
    }
    //Bertram, Jesper
    private void btnDownloadClicked() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Gem Fil");
            fileChooser.setInitialFileName(tableView.getSelectionModel().getSelectedItem().getFileName());
            //Filename on Windows doesn't behave the same as on Mac OS and Linux and wont allow for a generic extension to be set
            // this is a quick fix
            if (System.getProperty("os.name").toString().toLowerCase().contains("windows")) {
                fileChooser.setInitialFileName(fileChooser.getInitialFileName() + ".");
            }
            File destinationFile = fileChooser.showSaveDialog(primaryStage);
            if (destinationFile != null) {
                controller.saveFileToDisc(tableView.getSelectionModel().getSelectedItem().getFileId(), destinationFile);
            }
        }
    }
}
