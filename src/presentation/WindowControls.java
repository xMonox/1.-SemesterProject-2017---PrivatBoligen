package presentation;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowControls {

    private double xOffset = 0;
    private double yOffset = 0;
    private HBox hBoxWindowControls;
    private Button btnMin, btnClose;
    private ToolBar toolBar;

    //Jesper
    public HBox gethBoxWindowControls(Stage primaryStage) {
        //Toppane HboxWindowControls
        hBoxWindowControls = new HBox();
        hBoxWindowControls.setMinWidth(1280);
        hBoxWindowControls.setPadding(new Insets(2));
        hBoxWindowControls.setAlignment(Pos.BASELINE_RIGHT);
        hBoxWindowControls.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        hBoxWindowControls.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });

        //Toolbar for close / minimize buttons - Top Right
        toolBar = new ToolBar();
        btnMin = new Button("__");
        btnMin.setTextFill(Color.WHITE);
        btnMin.setStyle("-fx-background-color: transparent");
        btnMin.setOnAction(e -> {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            // is stage minimizable into task bar. (true | false)
            stage.setIconified(true);
        });

        btnClose = new Button("X");
        btnClose.setTextFill(Color.WHITE);
        btnClose.setStyle("-fx-background-color: transparent");
        btnClose.setOnAction(e -> {
            Platform.exit();
        });

        toolBar.getItems().addAll(btnMin, btnClose);
        hBoxWindowControls.getChildren().addAll(btnMin, btnClose);
        hBoxWindowControls.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    return hBoxWindowControls;
    }



}
