package presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Controller;
import presentation.controllers.GuiController;
import presentation.controllers.IGuiController;

public class Gui extends Application {
    public Scene scene;
    GuiController guiController;
    Controller controller;

    //Bertram, Jesper
    @Override
    public void start(Stage primaryStage){

        controller = new Controller();
        guiController = new GuiController(controller);
        scene = new LoginScene().getScene(guiController, controller, primaryStage);
        primaryStage = guiController.getStage();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("style/icons/logo_privatboligen_punch128.png")));
        primaryStage.setTitle("Privatboligen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
