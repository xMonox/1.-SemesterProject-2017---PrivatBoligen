package presentation;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Controller;
import logic.entities.ObservableCustomer;
import logic.entities.ObservableInvoice;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomerNode {
    private ObservableList<ObservableCustomer> observableCustomerList;
    private SortedList<ObservableCustomer> sortedList;
    private HBox hBox, hboxNewandDelete, hBoxLeft, hBoxRight;
    private VBox vBoxCustomerObject, vBoxSearchTableAndbtnNew, vBoxBtnSave;
    private TableView<ObservableCustomer> tableView;
    private TextField txtCvr, txtName, txtAddress, txtZipcode, txtContact, txtEmail, txtPhoneNumber, txtCity, txtSearchField;
    private Controller controller;
    private Button btnSave, btnDelete, btnNew;
    private Stage primaryStage;
    private Label lblCvr, lblName, lblAddress, lblZipcode, lblContact, lblPhoneNumber, lblEmail, lblCity;
    private Validation validation;
    private ButtonType btnTypeYes, btnTypeCancel;
    private BooleanProperty edit;

    //Julie, Bertram, Jesper
    public HBox getHbox(Controller controller, Stage primaryStage){
        this.primaryStage = primaryStage;
        this.controller = controller;

        edit = new SimpleBooleanProperty(false);

        observableCustomerList = FXCollections.observableList(controller.getObservableCustomers());

        createTable();

        tableView.setMaxWidth(890);
        tableView.setMaxHeight(580);

        btnSave     = new Button("Opret");
        btnDelete   = new Button("Slet");
        btnNew      = new Button("Ny");

        btnSave.setPrefWidth(100);
        btnDelete.setPrefWidth(60);
        btnNew.setPrefWidth(60);



        lblCvr          = new Label("CVR-nr.");
        lblName         = new Label("Kundenavn");
        lblAddress      = new Label("Adresse");
        lblZipcode      = new Label("Postnummer");
        lblContact      = new Label("Kontaktperson");
        lblPhoneNumber  = new Label("Telefonnummer");
        lblEmail        = new Label("E-mail");
        lblCity         = new Label("City");

        lblCvr.getStyleClass().add("labels");
        lblName.getStyleClass().add("labels");
        lblAddress.getStyleClass().add("labels");
        lblZipcode.getStyleClass().add("labels");
        lblContact.getStyleClass().add("labels");
        lblPhoneNumber.getStyleClass().add("labels");
        lblEmail.getStyleClass().add("labels");
        lblCity.getStyleClass().add("labels");

        txtCvr          = new TextField();
        txtName         = new TextField();
        txtAddress      = new TextField();
        txtZipcode      = new TextField();
        txtCity         = new TextField();
        txtContact      = new TextField();
        txtPhoneNumber  = new TextField();
        txtEmail        = new TextField();
        txtSearchField  = new TextField();

        // set prompt text
        txtCvr.setPromptText("CVR-nr.");
        txtName.setPromptText("Kundenavn");
        txtAddress.setPromptText("Adresse");
        txtZipcode.setPromptText("Postnummer");
        txtCity.setPromptText("By");
        txtContact.setPromptText("Kontaktperson");
        txtPhoneNumber.setPromptText("Telefon nr.");
        txtEmail.setPromptText("E-mail");
        txtSearchField.setPromptText("Søg");

        //validate all text fields that should only contain numbers
        validation = new Validation();
        validation.textFieldNumber(txtCvr);
        validation.textFieldNumber(txtZipcode);
        validation.textFieldNumber(txtPhoneNumber);

        //bind zipcode and city
        txtCity.setEditable(false);
        txtZipcode.textProperty().addListener((Observable e) -> {
            if (txtZipcode.getText().equals("")){

            } else {
                int zipcode = Integer.parseInt((txtZipcode.getText()));
                if (zipcode >= 1000 && zipcode < 10000) {
                    txtCity.setText(controller.getCityFromZipcode(zipcode));
                }
            }
        });

        btnDelete.setOnAction(event -> btnDeleteClicked());
        tableView.setOnMouseClicked(event -> tableViewClicked());
        btnNew.setOnAction(event -> btnNewClicked());

        btnSave.setOnAction(event -> btnSaveClicked());
        edit.addListener(e-> {
            if (edit.getValue()) {
                btnSave.setText("Opdater");
            } else {
                btnSave.setText("Opret");
            }
        });


        //search method
        txtSearchField.setOnKeyReleased(e -> txtSearchFieldAction());

        hboxNewandDelete = new HBox();
        hboxNewandDelete.getChildren().addAll(btnNew, btnDelete);
        hboxNewandDelete.setSpacing(10);

        hBoxLeft = new HBox();
        hBoxLeft.setMinWidth(960);
        hBoxLeft.setPadding(new Insets(35));

        hBoxRight = new HBox();
        hBoxRight.setMinWidth(320);
        hBoxRight.setPadding(new Insets(35));
        hBoxRight.setId("input-panel");

        vBoxBtnSave = new VBox(btnSave);
        vBoxBtnSave.setPadding(new Insets(5,0,0,0));

        vBoxSearchTableAndbtnNew = new VBox();
        vBoxSearchTableAndbtnNew.getChildren().addAll(txtSearchField, tableView, hboxNewandDelete);
        vBoxSearchTableAndbtnNew.setSpacing(15);
        vBoxSearchTableAndbtnNew.setAlignment(Pos.CENTER_LEFT);

        vBoxCustomerObject = new VBox();
        vBoxCustomerObject.getChildren().addAll(lblCvr, txtCvr, lblName, txtName, lblAddress, txtAddress, lblZipcode, txtZipcode, lblCity, txtCity, lblContact, txtContact, lblPhoneNumber, txtPhoneNumber, lblEmail, txtEmail, vBoxBtnSave);
        vBoxCustomerObject.setSpacing(5);
        vBoxCustomerObject.setMinWidth(250);
        vBoxCustomerObject.setMaxWidth(250);
        vBoxCustomerObject.setPadding(new Insets(50,0,0,0));

        hBoxLeft.getChildren().add(vBoxSearchTableAndbtnNew);
        hBoxRight.getChildren().add(vBoxCustomerObject);
        hBox = new HBox();
        hBox.getChildren().addAll(hBoxLeft, hBoxRight);

        return hBox;
    }

    //Julie
    private void createTable() {
        tableView = new TableView<>();
        tableView.setItems(observableCustomerList);

        tableView.getColumns().add(columnInt("CVR-nr.", ObservableCustomer::cvrProperty));
        tableView.getColumns().add(column("Kundenavn", ObservableCustomer::nameProperty));
        tableView.getColumns().add(column("Adresse", ObservableCustomer::addressProperty));
        tableView.getColumns().add(columnInt("Postnummer", ObservableCustomer::zipcodeProperty));
        tableView.getColumns().add(column("By", ObservableCustomer::cityProperty));
        tableView.getColumns().add(column("Kontaktperson", ObservableCustomer::contactProperty));
        tableView.getColumns().add(columnInt("Telefonnummer", ObservableCustomer::phoneNumberProperty));
        tableView.getColumns().add(column("E-mail", ObservableCustomer::emailProperty));

    }
    //Julie
    private TableColumn<ObservableCustomer, String> column(String title, Function<ObservableCustomer, ObservableValue<String>> property) {
        TableColumn<ObservableCustomer, String> col = new TableColumn<>(title);
        col.setMinWidth(111);
        col.setEditable(true);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setResizable(false);
        return col;
    }

    //Julie
    private TableColumn<ObservableCustomer, Number> columnInt(String title, Function<ObservableCustomer, ObservableValue<Number>> property) {
        TableColumn<ObservableCustomer, Number> col = new TableColumn<>(title);
        col.setMinWidth(111);
        col.setEditable(true);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setResizable(false);

        return col;
    }
    //Julie, Bertram
    private void btnDeleteClicked() {
        alertUser();

        //empty fields
        btnNewClicked();
    }

    //Julie, Bertram
    private void alertUser() {
        //kode lånt herfra, dog refaktureret
        //  http://code.makery.ch/blog/javafx-dialogs-official/

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Slet kunde");
        alert.setHeaderText(null);
        String customerName = tableView.getSelectionModel().getSelectedItem().getName();

        if(controller.isAdmin()){
            alert.setContentText("Er du sikker på at du vil slette " + customerName + ", og alle tilhørende fakturaer?");

            btnTypeYes = new ButtonType("Ja");
            btnTypeCancel = new ButtonType("Annuller");

            alert.getButtonTypes().setAll(btnTypeYes, btnTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnTypeYes) {
                controller.deleteCustomerByCvr(tableView.getSelectionModel().getSelectedItem().getCvr());
                observableCustomerList.remove(tableView.getSelectionModel().getSelectedItem());
            } else if (result.get() == btnTypeCancel) {
                alert.close();
            }
        } else {
            alert.setContentText("Du er ikke autoriseret til at slette kunder. Kontakt venligst en administrator.");

            alert.showAndWait();

        }

        //lånt kode stop
    }

    //Julie, Bertram
    private void tableViewClicked() {
        //add existing customer to textfields
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            txtCvr.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getCvr()));
            txtName.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getName()));
            txtAddress.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getAddress()));
            txtZipcode.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getZipcode()));
            txtCity.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getCity()));
            txtContact.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getContact()));
            txtPhoneNumber.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getPhoneNumber()));
            txtEmail.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getEmail()));
            edit.setValue(true);
        } else {
            edit.setValue(false);
        }
    }
    //Svend
    private void btnNewClicked() {
        //empty fields
        txtCvr.clear();
        txtName.clear();
        txtAddress.clear();
        txtZipcode.clear();

        txtCity.clear();
        txtContact.clear();
        txtPhoneNumber.clear();
        txtEmail.clear();
        tableView.getSelectionModel().clearSelection();
        edit.setValue(false);

    }

    //Bertram, Julie
    private void btnSaveClicked() {

        if(edit.getValue()) {
            //update the existing customer
            controller.updateObservableCustomer(tableView.getSelectionModel(). getSelectedItem(),
                    Integer.valueOf(txtCvr.getText()),
                    String.valueOf(txtName.getText()),
                    String.valueOf(txtAddress.getText()),
                    Integer.valueOf(txtZipcode.getText()),
                    String.valueOf(txtCity.getText()),
                    String.valueOf(txtContact.getText()),
                    Integer.valueOf(txtPhoneNumber.getText()),
                    String.valueOf(txtEmail.getText())
            );

        } else{
            //add a new customer
            observableCustomerList.add(controller.createObservableCustomer(
                    Integer.valueOf(txtCvr.getText()),
                    String.valueOf(txtName.getText()),
                    String.valueOf(txtAddress.getText()),
                    Integer.valueOf(txtZipcode.getText()),
                    String.valueOf(txtCity.getText()),
                    String.valueOf(txtContact.getText()),
                    Integer.valueOf(txtPhoneNumber.getText()),
                    String.valueOf(txtEmail.getText())
            ));
        }

        //empty fields
       btnNewClicked();
    }

    //Bertram
    private void txtSearchFieldAction() {
        //kode lånt herfra: https://www.youtube.com/watch?v=6kL2pQ9KABg

        FilteredList<ObservableCustomer> filteredList = new FilteredList<>(observableCustomerList, e -> true);
        txtSearchField.setOnKeyReleased(e -> {
            txtSearchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super ObservableCustomer>) customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Integer.toString(customer.getCvr()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Integer.toString(customer.getZipcode()).contains(lowerCaseFilter)) {
                        return  true;
                    } else if (customer.getCity().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getContact().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Integer.toString(customer.getPhoneNumber()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
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
}
