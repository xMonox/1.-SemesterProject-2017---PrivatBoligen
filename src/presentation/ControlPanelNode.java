package presentation;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.Controller;
import logic.entities.ObservableCustomer;
import logic.entities.ObservableUser;
import presentation.controllers.GuiController;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by JulieBang on 19/05/2017.
 */
public class ControlPanelNode {
    private Stage primaryStage;
    private Controller controller;
    private ObservableList<ObservableUser> observableUserList;
    private SortedList<ObservableUser> sortedList;
    private Button btnChangePassword, btnChangeUser, btnCreateUser, btnLogOut, btnSubmit, btnBack, btnChooseUser;
    private HBox hBoxScene;
    private VBox vBoxScene, vBoxCenter, vBoxBtnLogOut, vBoxBtnBack;
    private TextField txtUsername, txtSearch;
    private PasswordField txtPassword, txtNewPassword, txtRepeatPassword;
    private RadioButton rbtnYes, rbtnNo;
    private Label lblCreateUser, lblUsername, lblPassword, lblAdminRights, lblChangeUser,
            lblNewPassword, lblRepeatPassword, lblChangePassword, lblTryAgain, lblSavedPassword, lblSavedUser;
    private ToggleGroup tglgRadiobtn;
    private String role;
    private GridPane gridPane;
    private TableView<ObservableUser> tableView;
    private GuiController guiController;
    private ButtonType btnTypeCancel, btnTypeOK;

    //Julie
    public HBox getHbox(GuiController guiController, Controller controller, Stage primaryStage) {
        this.guiController  = guiController;
        this.primaryStage   = primaryStage;
        this.controller     = controller;

        //guiController       = new GuiController(controller);
        btnBack             = new Button("Tilbage");
        btnChangePassword   = new Button("Skift kode");
        btnChangeUser       = new Button("Skift bruger");
        btnCreateUser       = new Button("Opret bruger");
        btnLogOut           = new Button("Log ud");

        vBoxBtnBack         = new VBox(btnBack);
        vBoxCenter          = new VBox(btnChangePassword, btnChangeUser, btnCreateUser);
        vBoxBtnLogOut       = new VBox(btnLogOut);
        vBoxScene           = new VBox(vBoxBtnBack, vBoxCenter, vBoxBtnLogOut);
        hBoxScene           = new HBox(vBoxScene);

        //set button position
        vBoxCenter.setSpacing(20);
        vBoxCenter.setAlignment(Pos.CENTER);
        vBoxCenter.setPrefSize(1280, 530);

        vBoxBtnLogOut.setPadding(new Insets(0, 20, 10, 0));
        vBoxBtnLogOut.setAlignment(Pos.BASELINE_RIGHT);
        vBoxBtnLogOut.setMinSize(1280, 0);

        //set back button
        btnBack.setId("btnBack");
        btnBack.setOnAction(event -> {
            btnBackClicked();
        });
        vBoxBtnBack.setAlignment(Pos.TOP_RIGHT);
        vBoxBtnBack.setPadding(new Insets(20, 20, 0, 0));
        vBoxBtnBack.setVisible(false);

        //set button size
        btnChangeUser.setPrefSize(200, 50);
        btnChangePassword.setPrefSize(200, 50);
        btnCreateUser.setPrefSize(200, 50);
        btnLogOut.setPrefSize(200, 50);

        //set button ID
        btnChangePassword.setId("btnChangePassword");
        btnChangeUser.setId("btnChangeUser");
        btnCreateUser.setId("btnCreateUser");
        btnLogOut.setId("btnLogOut");

        isAdminButtons();

        //set on action
        btnChangePassword.setOnAction(event -> {
            btnChangePasswordClicked();
        });

        btnCreateUser.setOnAction((ActionEvent event) -> {
            btnCreateUserClicked();
        });

        btnChangeUser.setOnAction(event -> {
            btnChangeUserClicked();
        });

        btnLogOut.setOnAction(event -> {
            btnLogOutClicked();
        });

        hBoxScene.getStylesheets().add("presentation/style/style.css");

        return hBoxScene;
    }

    //Julie, Bertram
    private void isAdminButtons() {
        if (!controller.isAdmin()) {
            btnChangeUser.setVisible(false);
            btnCreateUser.setVisible(false);
        }
    }

    //Bertram
    private void btnLogOutClicked() {
        Scene loginScene = guiController.loginScene();
        primaryStage.setScene(loginScene);
    }

    //Julie
    private void btnChangeUserClicked() {

        //make back button visible
        vBoxBtnBack.setVisible(true);

        //remove buttons
        vBoxCenter.getChildren().clear();

        //add labels and set id
        lblChangeUser = new Label("Skift Bruger");
        lblChangeUser.getStyleClass().add("labels");

        //add button and set on action
        btnChooseUser = new Button("Vælg bruger");

        btnChooseUser.setOnAction(event -> btnChooseUserClicked());

        btnChooseUser.setPrefWidth(120);

        //add search field
        txtSearch = new TextField();
        txtSearch.setPromptText("Søg");
        txtSearch.setMaxWidth(500);

        txtSearch.setOnKeyReleased(e -> txtSearchFieldAction());

        //get all users from database
        observableUserList = FXCollections.observableList(controller.getAllObservableUsers());

        createTable();

        vBoxCenter.getChildren().addAll(txtSearch, tableView, btnChooseUser);
    }

    //Julie
    private void createTable() {

        tableView = new TableView<>();
        tableView.setItems(observableUserList);
        tableView.getColumns().add(column("Brugernavn", ObservableUser::usernameProperty));
        tableView.getColumns().add(column("Rolle", ObservableUser::roleProperty));

        tableView.setMaxSize(501, 300);
    }
    //Julie
    private void btnChooseUserClicked() {
        ObservableUser observableUser = tableView.getSelectionModel().getSelectedItem();
        alertUser(observableUser);
    }

    //Julie, Bertram
    private void alertUser(ObservableUser observableUser) {
        //kode lånt herfra, dog refaktureret
        //  http://code.makery.ch/blog/javafx-dialogs-official/

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Skift bruger");
        alert.setHeaderText(null);

        if(controller.isOtherAdmin(observableUser)) {
            alert.setContentText("Vil du logge ind som: " + observableUser.getUsername() + "?");
        } else {
            alert.setContentText("Vil du logge ind som: " + observableUser.getUsername() + "?\nDu vil miste dine administrator rettigheder. ");
        }

        btnTypeOK       = new ButtonType("Ja");
        btnTypeCancel   = new ButtonType("Annuller");

        alert.getButtonTypes().setAll(btnTypeOK, btnTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnTypeOK) {
            controller.setUser(observableUser.getUserId());
            guiController.masterScene.content.getChildren().clear();
            guiController.masterScene.content.getChildren().add(guiController.controlContent());
        } else if (result.get() == btnTypeCancel) {
            alert.close();
        }

        //lånt kode stop
    }

    //Julie
    private TableColumn<ObservableUser, String> column(String title, Function<ObservableUser, ObservableValue<String>> property) {
        TableColumn<ObservableUser, String> col = new TableColumn<>(title);
        col.setMinWidth(250);
        col.setMaxWidth(250);
        col.setEditable(true);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setResizable(false);
        return col;
    }

    //Julie
    private void btnChangePasswordClicked() {

        vBoxBtnBack.setVisible(true);

        //remove buttons
        vBoxCenter.getChildren().clear();

        gridPane = new GridPane();

        //create password textfield and labels
        txtNewPassword      = new PasswordField();
        txtRepeatPassword   = new PasswordField();

        txtNewPassword.setPromptText("Ny kode");
        txtRepeatPassword.setPromptText("Gentag kode");

        lblChangePassword   = new Label("Skift kode");
        lblNewPassword      = new Label("Indtast ny kode:");
        lblRepeatPassword   = new Label("Bekræft ny kode:");
        lblTryAgain         = new Label("Adgangskoderne matcher ikke. Prøv igen");
        lblSavedPassword    = new Label("Din nye adgangskode er gemt");

        btnSubmit           = new Button("Gem");
        btnSubmit.setPrefWidth(60);

        lblTryAgain.setVisible(false);
        lblSavedPassword.setVisible(false);

        //add to gridpane
        gridPane.add(lblChangePassword, 3, 1);
        gridPane.add(lblNewPassword, 2, 2);
        gridPane.add(lblRepeatPassword, 2, 3);
        gridPane.add(txtNewPassword, 3, 2);
        gridPane.add(txtRepeatPassword, 3, 3);
        gridPane.add(btnSubmit, 3, 4);
        gridPane.add(lblTryAgain, 3, 5);
        gridPane.add(lblSavedPassword, 3, 5);

        //add gripane to vbox
        vBoxCenter.getChildren().add(gridPane);

        //set spacing and allignment
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //set ID
        btnSubmit.setId("btnSubmit");
        lblNewPassword.getStyleClass().add("labels");
        lblChangePassword.getStyleClass().add("labels");
        lblRepeatPassword.getStyleClass().add("labels");
        lblSavedPassword.getStyleClass().add("labels");
        lblTryAgain.getStyleClass().add("labels");

        txtRepeatPassword.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                btnSubmitClicked();
            }
        });
        btnSubmit.setOnAction(event -> btnSubmitClicked());
    }

    //Julie
    private void btnSubmitClicked() {
        if (txtNewPassword.getText().equals(txtRepeatPassword.getText())) {
            controller.updateUserPassword(controller.getActiveUser().getId(), txtNewPassword.getText());
            gridPane.getChildren().clear();

            btnChangePasswordClicked();
            lblSavedPassword.setVisible(true);
        } else {
            gridPane.getChildren().clear();
            btnChangePasswordClicked();
            lblTryAgain.setVisible(true);
        }
    }

    //Julie, Bertram
    private void btnCreateUserClicked() {

        vBoxBtnBack.setVisible(true);

        //remove buttons
        vBoxCenter.getChildren().clear();

        //create user textfield
        txtUsername = new TextField();
        txtPassword = new PasswordField();
        txtRepeatPassword = new PasswordField();

        txtUsername.setPromptText("Brugernavn");
        txtPassword.setPromptText("Kode");
        txtRepeatPassword.setPromptText("Gentag kode");

        //create radio buttons and togglegroup
        rbtnYes = new RadioButton("Ja");
        rbtnNo = new RadioButton("Nej");

        tglgRadiobtn = new ToggleGroup();
        rbtnYes.setToggleGroup(tglgRadiobtn);
        rbtnNo.setToggleGroup(tglgRadiobtn);
        rbtnNo.setSelected(true);

        //create labels
        lblCreateUser = new Label("Opret bruger");
        lblUsername = new Label("Brugernavn:");
        lblPassword = new Label("Kode:");
        lblRepeatPassword = new Label("Gentag kode:");
        lblAdminRights = new Label("Administrator rettigheder?");
        lblTryAgain = new Label("Adgangskoderne matcher ikke. Prøv igen");
        lblSavedUser = new Label("Den nye bruger er oprettet");

        lblTryAgain.setVisible(false);
        lblSavedUser.setVisible(false);

        //create submit button
        btnSubmit = new Button("Gem");
        btnSubmit.setPrefWidth(60);


        //add to gridpane
        gridPane = new GridPane();
        gridPane.add(lblCreateUser, 3, 1);

        gridPane.add(lblUsername, 2, 2);
        gridPane.add(txtUsername, 3, 2);

        gridPane.add(lblPassword, 2, 3);
        gridPane.add(txtPassword, 3, 3);

        gridPane.add(lblRepeatPassword, 2, 4);
        gridPane.add(txtRepeatPassword, 3, 4);

        gridPane.add(lblAdminRights, 3, 5);
        gridPane.add(rbtnYes, 3, 6);
        gridPane.add(rbtnNo, 3, 7);

        gridPane.add(btnSubmit, 3, 8);

        gridPane.add(lblTryAgain, 3, 9);
        gridPane.add(lblSavedUser, 3, 10);


        //set spacing and allignment
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //set ID
        btnSubmit.setId("btnSubmit");
        lblCreateUser.getStyleClass().add("labels");
        lblUsername.getStyleClass().add("labels");
        lblPassword.getStyleClass().add("labels");
        lblRepeatPassword.getStyleClass().add("labels");
        lblAdminRights.getStyleClass().add("labels");
        rbtnYes.getStyleClass().add("labels");
        rbtnNo.getStyleClass().add("labels");
        lblSavedUser.getStyleClass().add("labels");
        lblTryAgain.getStyleClass().add("labels");

        //add to VBox
        vBoxCenter.getChildren().add(gridPane);

        //call method create user from controller
        txtRepeatPassword.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                saveUserClicked();
            }
        });
        btnSubmit.setOnAction(e -> saveUserClicked());

    }

    //Julie
    private void saveUserClicked() {
        lblSavedUser.setVisible(false);
        lblTryAgain.setVisible(false);

        //validate on password
        if (txtPassword.getText().equals(txtRepeatPassword.getText())) {
            //admin or regular user
            if (rbtnYes.isSelected()) {
                role = "Administrator";
            } else {
                role = "Bruger";
            }
            //save user
            controller.createUser(txtUsername.getText(), txtPassword.getText(), role);
            lblSavedUser.setVisible(true);
        } else {
            lblTryAgain.setVisible(true);
        }
    }

    //Julie
    private void btnBackClicked() {
        vBoxBtnBack.setVisible(false);
        vBoxCenter.getChildren().clear();
        vBoxCenter.getChildren().addAll(btnChangePassword, btnChangeUser, btnCreateUser);

    }

    //Bertram
    private void txtSearchFieldAction() {
        //kode lånt herfra: https://www.youtube.com/watch?v=6kL2pQ9KABg

        FilteredList<ObservableUser> filteredList = new FilteredList<>(observableUserList, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super ObservableUser>) user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (user.getRole().toLowerCase().contains(lowerCaseFilter)) {
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
