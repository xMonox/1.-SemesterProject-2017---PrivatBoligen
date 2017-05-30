package presentation;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Controller;
import logic.entities.ObservableCustomer;
import logic.entities.ObservableInvoice;
import logic.entities.ObservableUser;
import logic.mapper.ObservableCustomerStringConverter;

import presentation.controllers.GuiController;
import presentation.controllers.IGuiController;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;


public class InvoiceNode {
    private ObservableList<ObservableInvoice> observableInvoiceList;
    private SortedList<ObservableInvoice> sortedList;
    private HBox hBox, hBoxLeft, hBoxRight, hboxNewandDelete, hboxDownload;
    private VBox vBoxInvoiceObject, vBoxSearchTableAndbtnNew;
    private TableView<ObservableInvoice> tableView;
    private ComboBox<ObservableCustomer> customerPicker;
    private TextField txtInvoiceNumber, txtCustomerId, txtSearchField, txtFileName;
    private DatePicker dtpInvoice, dtpPayment;
    private IGuiController guiController;
    private Controller controller;
    private Button btnSave, btnDelete, btnNew, btnSelectFile, btnDownload;
    private ButtonType btnTypeYes, btnTypeOk, btnTypeCancel;
    private FileChooser fileChooser;
    private File file;
    private Label lblInvoiceDate, lblInvoice, lblCvr, lblPaymentDate, lblAction, lblChooseCustomer, lblFileName, lblInfo1;
    private Stage primaryStage;
    private String fileChooserResult;
    private BooleanProperty edit;
    private Alert alert;


    //Svend, Bertram, Jesper
    public HBox getHbox(GuiController guiController, Controller controller, Stage primaryStage){
        this.guiController = guiController;
        this.controller = controller;
        this.primaryStage = primaryStage;
        observableInvoiceList = FXCollections.observableList(controller.getUserObservableInvoices(controller.getActiveUser().getId()));
        tableView = new InvoiceTable().createTable(observableInvoiceList);
        tableView.setMinWidth(890);
        tableView.setMaxWidth(890);
        tableView.setMaxHeight(580);
        fileChooserResult = "";


        edit = new SimpleBooleanProperty(false);

        ObservableList observableCustomerAndCvr = controller.getObservableCustomerAndCvr();
        if(observableCustomerAndCvr.size()>0) {
            customerPicker = new ComboBox<>(observableCustomerAndCvr);
            customerPicker.setConverter(new ObservableCustomerStringConverter());
            customerPicker.setValue(customerPicker.getItems().get(0));
            //Bertram
            //set the value of txtCustomerId through the combobox
            customerPicker.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                    txtCustomerId.setText(Integer.toString(customerPicker.getSelectionModel().getSelectedItem().getCvr()))
            );
        } else {
            customerPicker = new ComboBox<>();
        }

        btnSave         = new Button("Opret");
        btnDelete       = new Button("Slet");
        btnNew          = new Button("Ny");
        btnSelectFile   = new Button("Vælg fil");
        btnDownload     = new Button("Download");

        btnSave.setPrefWidth(100);
        btnDelete.setPrefWidth(60);
        btnNew.setPrefWidth(60);

        fileChooser     = new FileChooser();
        lblInvoiceDate  = new Label("Faktura dato");
        lblPaymentDate  = new Label("Betalings dato");
        lblAction       = new Label("Indtast faktura");
        lblInvoice      = new Label("Faktura");
        lblCvr          = new Label("Skriv evt. CVR (skal eksistere)");
        lblFileName     = new Label("Fil navn");
        lblChooseCustomer = new Label("Vælg kunde");
        lblInfo1 = new Label("Faktura kan ændres, slettes eller downloades ved at vælge den i tabellen." + " Klik på \"Ny\" for at oprette en ny faktura");

        txtInvoiceNumber = new TextField();
        txtCustomerId   = new TextField();
        txtSearchField  = new TextField();
        txtFileName     = new TextField();

        dtpInvoice = new DatePicker();
        dtpPayment = new DatePicker();
        dtpInvoice.setMinWidth(250);
        dtpPayment.setMinWidth(250);

        txtInvoiceNumber.setPromptText("Faktura nr.");
        txtCustomerId.setPromptText("Cvr nr.");
        txtSearchField.setPromptText("Søg");


        btnSelectFile.setOnAction(e -> btnFileChooserClicked(primaryStage));
        btnDelete.setOnAction(e -> btnDeleteClicked());
        tableView.setOnMouseClicked(event -> tableClicked());
        btnNew.setOnAction(e -> btnNewClicked());
        btnDownload.setVisible(false);
        btnDownload.setOnAction(e -> btnDownloadClicked());


        btnSave.setOnAction(event -> btnSaveClicked());
        //Bertram, Svend
        //make the buttons text reflect what action is about to happen
        edit.addListener(e-> {
            if (edit.getValue()) {
                lblAction.setText("Ændre faktura");
                btnSave.setText("Opdater");
            } else {
                lblAction.setText("Indtast faktura");
                btnSave.setText("Opret");
            }
        });

        //Bertram
        //find and set the value of value for customerPicker when focus of cvr is unset
        createCommitBinding(txtCustomerId).addListener((observableValue, oldText, newText) -> {
            for (ObservableCustomer customer : customerPicker.getItems()) {
                if (Integer.toString(customer.getCvr()).equals(newText)) {
                    customerPicker.setValue(customer);
                    break;
                }
            }
        });



        hboxDownload = new HBox();
        hboxDownload.getChildren().add(btnDownload);
        hboxDownload.setSpacing(10);
        hboxDownload.setAlignment(Pos.BASELINE_RIGHT);

        hboxNewandDelete = new HBox();
        hboxNewandDelete.getChildren().addAll(btnNew,btnDelete, hboxDownload);
        hboxNewandDelete.setSpacing(10);

        hBoxLeft = new HBox();
        hBoxLeft.setMinWidth(960);
        hBoxLeft.setPadding(new Insets(35));


        hBoxRight = new HBox();
        hBoxRight.setMinWidth(320);
        hBoxRight.setPadding(new Insets(35));
        hBoxRight.setId("input-panel");

        vBoxSearchTableAndbtnNew = new VBox();
        vBoxSearchTableAndbtnNew.getChildren().addAll(txtSearchField,tableView,hboxNewandDelete, lblInfo1);
        vBoxSearchTableAndbtnNew.setSpacing(15);
        vBoxSearchTableAndbtnNew.setAlignment(Pos.CENTER_LEFT);

        lblAction.getStyleClass().add("labels");
        lblCvr.getStyleClass().add("labels");
        lblInvoice.getStyleClass().add("labels");
        lblInvoiceDate.getStyleClass().add("labels");
        lblChooseCustomer.getStyleClass().add("labels");
        lblPaymentDate.getStyleClass().add("labels");
        lblFileName.getStyleClass().add("labels");
        lblInfo1.getStyleClass().add("labels");

        vBoxInvoiceObject = new VBox();
        vBoxInvoiceObject.getChildren().addAll(lblAction, lblInvoice, txtInvoiceNumber, lblChooseCustomer, customerPicker, lblCvr, txtCustomerId, lblInvoiceDate, dtpInvoice, lblPaymentDate, dtpPayment, lblFileName, txtFileName, btnSelectFile, btnSave);
        vBoxInvoiceObject.setSpacing(10);
        vBoxInvoiceObject.setMinWidth(250);
        vBoxInvoiceObject.setMaxWidth(250);


        hBoxLeft.getChildren().add(vBoxSearchTableAndbtnNew);
        hBoxRight.getChildren().add(vBoxInvoiceObject);
        hBox = new HBox();
        hBox.getChildren().addAll(hBoxLeft, hBoxRight);

        txtSearchField.setOnKeyReleased(e -> txtSearchFieldAction());
        return hBox;
    }
    //Jesper, Bertram
    private void btnFileChooserClicked(Stage primaryStage) {
        file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            fileChooserResult = file.getAbsolutePath();
            txtFileName.setText(file.getName());
        }
    }
    //Svend, Bertram
    private void btnDeleteClicked(){
        if(tableView.getSelectionModel().getSelectedItem() != null) {
           ObservableInvoice ObservableInvoice = tableView.getSelectionModel().getSelectedItem();
           alertUser(ObservableInvoice);
           controller.deleteInvoiceById(tableView.getSelectionModel().getSelectedItem().getInvoiceNumber());
           tableView.setItems(observableInvoiceList);
           observableInvoiceList.remove(tableView.getSelectionModel().getSelectedItem());
        }
    }

    //Svend, Bertram
    private void tableClicked() {
        if(tableView.getSelectionModel().getSelectedItem() != null){
            LocalDate localDateInvoice = controller.parseDateString(tableView.getSelectionModel().getSelectedItem().getInvoiceDate());
            LocalDate localDatePayment = controller.parseDateString(tableView.getSelectionModel().getSelectedItem().getPaymentDate());
            btnDownload.setVisible(true);
            btnNew.setVisible(true);
            txtInvoiceNumber.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getInvoiceNumber()));
            txtCustomerId.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getCustomerCvr()));
            dtpInvoice.setValue(localDateInvoice);
            dtpPayment.setValue(localDatePayment);
            txtFileName.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getFileName()));
            edit.setValue(true);

            //set the combobox initial value
            for (ObservableCustomer customer : customerPicker.getItems()) {
                if (customer.getCvr() == tableView.getSelectionModel().getSelectedItem().getCustomerCvr()) {
                    customerPicker.setValue(customer);
                    break;
                }
            }
        } else {
            edit.setValue(false);
        }
    }

    //Svend
    private void btnNewClicked(){
        txtFileName.setVisible(true);
        btnDownload.setVisible(false);
        txtInvoiceNumber.clear();
        txtCustomerId.clear();
        dtpInvoice.getEditor().clear();
        dtpPayment.getEditor().clear();
        txtFileName.clear();
        tableView.getSelectionModel().clearSelection();
        edit.setValue(false);

    }
    //Julie, Svend
    private void alertUser(ObservableInvoice observableInvoice) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Slet faktura");
        alert.setHeaderText(null);
        alert.setContentText("Vil du slette faktura: " + observableInvoice.getInvoiceNumber() + "?");

        btnTypeYes = new ButtonType("Ja");
        btnTypeCancel = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(btnTypeYes, btnTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnTypeYes) {
            observableInvoiceList.remove(observableInvoice);
            controller.deleteInvoiceById(tableView.getSelectionModel().getSelectedItem().getInvoiceNumber());

        } else {
            alert.close();
        }

    }
    private void alertUserInvoice() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error 404");
        alert.setHeaderText(null);
        alert.setContentText("Du kan ikke oprette en faktura uden tilhørende fil");

        btnTypeOk = new ButtonType("ok");

       alert.getButtonTypes().setAll(btnTypeOk);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btnTypeOk){
            alert.close();

        }



    }
    //Bertram, Svend, Jesper
    private void btnSaveClicked() {
        if (file != null) {
            String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
            //if the user has left the inputfield blank
            if (txtFileName.getText().equals("")) {
                txtFileName.setText(file.getName());
                //if the user has renamed the file but the filextension is missing
            } else if (!txtFileName.getText().substring(txtFileName.getText().length() - fileExtension.length()).equals(fileExtension)) {
                txtFileName.setText(txtFileName.getText() + fileExtension);
            }
        }
        if(edit.getValue()) {
            try {
                controller.updateObservableInvoice(tableView.getSelectionModel().getSelectedItem(),
                        Integer.valueOf(txtInvoiceNumber.getText()),
                        Integer.valueOf(customerPicker.getSelectionModel().getSelectedItem().getCvr()),
                        String.valueOf(dtpInvoice.getValue()),
                        String.valueOf(dtpPayment.getValue()),
                        String.valueOf(txtFileName.getText()),
                        fileChooserResult
                );
                //make sure the dates are displayed correctly on systems with foreign locales
                tableView.getSelectionModel().getSelectedItem().setInvoiceDate(String.valueOf(controller.parseLocalDateToString(dtpInvoice.getValue())));
                tableView.getSelectionModel().getSelectedItem().setPaymentDate(String.valueOf(controller.parseLocalDateToString(dtpPayment.getValue())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        else if (file != null) {
            try {
                tableView.setItems(observableInvoiceList);
                ObservableInvoice invoice = controller.createObservableInvoice(
                        Integer.valueOf(txtInvoiceNumber.getText()),
                        Integer.valueOf(customerPicker.getSelectionModel().getSelectedItem().getCvr()),
                        dtpInvoice.getValue().toString(),
                        dtpPayment.getValue().toString(),
                        controller.getActiveUser().getId(),
                        -1,
                        fileChooserResult,
                        txtFileName.getText());
                observableInvoiceList.add(invoice);

                //manually set the customer name for the table so it doesn't have to reload.
                int index = observableInvoiceList.indexOf(invoice);
                observableInvoiceList.get(index).setCustomerName(customerPicker.getSelectionModel().getSelectedItem().getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            alertUserInvoice();
        }
        btnNewClicked();
    }

    //Jesper, Bertram
    private void btnDownloadClicked() {
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

    //Bertram
    private void txtSearchFieldAction() {
        // kode lånt herfra: https://www.youtube.com/watch?v=6kL2pQ9KABgz

        FilteredList<ObservableInvoice> filteredList = new FilteredList<>(observableInvoiceList, e -> true);
        txtSearchField.setOnKeyReleased(e -> {
            txtSearchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super ObservableInvoice>) invoice -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Integer.toString(invoice.getInvoiceNumber()).contains(lowerCaseFilter)) {
                        return true;
                    }else if (Integer.toString(invoice.getCustomerCvr()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getInvoiceDate().contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getPaymentDate().contains(lowerCaseFilter)) {
                        return  true;
                    } else if (Integer.toString(invoice.getCustomerCvr()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getFileName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Integer.toString(invoice.getFileId()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    //https://stackoverflow.com/questions/23058980/javafx-binding-between-textfield-and-a-property
    private StringBinding createCommitBinding(TextField textField) {
        StringBinding binding = Bindings.createStringBinding(() -> textField.getText());
        textField.addEventHandler(ActionEvent.ACTION, evt -> binding.invalidate());
        textField.focusedProperty().addListener((obs, wasFocused, isFocused)-> {
            if (! isFocused) binding.invalidate();
        });
        return binding ;
    }
}
