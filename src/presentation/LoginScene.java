package presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Controller;
import logic.entities.User;
import logic.services.PasswordEncryption;
import logic.services.PasswordService;
import presentation.controllers.GuiController;

public class LoginScene {
    private Controller controller;
    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private HBox hBoxTop;
    private VBox vBoxContainer, vBoxLogin, vBoxTextFields;
    private Label lblHeader;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;
    private GuiController guiController;
    private HBox hBoxLogo;

    //Bertram, Jesper
    public Scene getScene(GuiController guiController, Controller controller, Stage stage) {
        this.guiController = guiController;
        this.controller = controller;
        this.stage      = guiController.getStage();
        txtUsername     = new TextField();
        txtPassword     = new PasswordField();
        borderPane      = new BorderPane();
        vBoxLogin       = new VBox();
        vBoxTextFields  = new VBox();
        lblHeader       = new Label();
        btnLogin        = new Button();
        hBoxTop         = guiController.windowControl();
        hBoxLogo        = guiController.showLogo();

        lblHeader.setText("Velkommen");
        lblHeader.setId("Header");

        btnLogin.setText("Log ind");
        btnLogin.setMinHeight(35);
        btnLogin.setMinWidth(200);
        btnLogin.setOnAction(e -> btnLoginClicked());
        txtUsername.setPromptText("Brugernavn");
        txtPassword.setPromptText("Kode");
        txtUsername.setMinHeight(30);
        txtPassword.setMinHeight(30);
        txtPassword.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                btnLoginClicked();
            }
        });

        vBoxTextFields.getChildren().addAll(txtUsername,txtPassword);
        vBoxTextFields.setSpacing(15);

        vBoxLogin.getChildren().addAll(lblHeader, vBoxTextFields, btnLogin);
        vBoxLogin.setMinHeight(225); vBoxLogin.setMaxHeight(225);
        vBoxLogin.setMinWidth(250); vBoxLogin.setMaxWidth(250);
        vBoxLogin.setId("LoginBox");
        vBoxLogin.setAlignment(Pos.TOP_CENTER);
        vBoxLogin.setPadding(new Insets(12, 25, 0, 25));
        vBoxLogin.setSpacing(15);

        vBoxContainer = new VBox();
        vBoxContainer.setAlignment(Pos.TOP_CENTER);
        vBoxContainer.setSpacing(50);
        vBoxContainer.getChildren().addAll(hBoxLogo, vBoxLogin);

        borderPane.setCenter(vBoxContainer);
        borderPane.getStylesheets().add("presentation/style/style.css");
        btnLogin.requestFocus();
        scene = new Scene(borderPane, 1280, 720);
        return scene;

    }

    //Bertram
    public void btnLoginClicked() {
        User user = controller.loginUser(txtUsername.getText(), txtPassword.getText());
        PasswordService ps = new PasswordService(new PasswordEncryption());
        if (user != null) {
            guiController.getStage().setScene(guiController.masterScene());
        }
    }
}
