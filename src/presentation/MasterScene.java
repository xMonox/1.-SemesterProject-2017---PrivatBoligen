package presentation;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.Constants;
import logic.Controller;
import presentation.controllers.GuiController;
import presentation.controllers.IGuiController;

import java.io.FileNotFoundException;
import java.util.Locale;


/**
 * Created by lupus on 5/24/17.
 */
public class MasterScene {
    HBox hBoxTop;
    HBox content;
    VBox vBox;
    Group root;
    Scene scene;
    ToggleButton tglBtnCalendar, tglBtnInvoices, tglBtnCustomers, tglBtnControlPanel;
    ToggleGroup toggleGroup;
    IGuiController guiController;

    //Bertram, Jesper
    public Scene getMasterScene(GuiController guiController) {
        Locale.setDefault(Constants.DENMARK);
        this.guiController = guiController;
        vBox        = new VBox();
        hBoxTop     = new HBox();
        content     = new HBox();
        root        = new Group();
        toggleGroup = new ToggleGroup();
        tglBtnCalendar = new ToggleButton("Kalender");
        tglBtnInvoices = new ToggleButton("Faktura");
        tglBtnCustomers = new ToggleButton("Kunder");
        tglBtnControlPanel = new ToggleButton("Kontrolpanel");
        tglBtnCalendar.setMinHeight(30);

        tglBtnCalendar.getStyleClass().add("panel-buttons");
        tglBtnInvoices.getStyleClass().add("panel-buttons");
        tglBtnCustomers.getStyleClass().add("panel-buttons");
        tglBtnControlPanel.getStyleClass().add("panel-buttons");

        tglBtnCalendar.setToggleGroup(toggleGroup);
        tglBtnInvoices.setToggleGroup(toggleGroup);
        tglBtnCustomers.setToggleGroup(toggleGroup);
        tglBtnControlPanel.setToggleGroup(toggleGroup);

        Node test = new HBox();


        hBoxTop.getChildren().addAll(tglBtnCalendar, tglBtnInvoices, tglBtnCustomers, tglBtnControlPanel);
        hBoxTop.setMaxHeight(30);
        hBoxTop.setMinHeight(30);

        content.setMinHeight(690);
        content.setMaxHeight(690);
        content.setMinWidth(1280);
        content.setMaxWidth(1280);
        content.getChildren().add(guiController.calendarContent());

        tglBtnCalendar.setSelected(true);

        //make each button load the appropiate content and disable themselves if they're selected.
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Toggle selection = toggleGroup.getSelectedToggle();
            if(selection.equals(tglBtnCalendar)) {
                content.getChildren().clear();
                content.getChildren().add(guiController.calendarContent());
                tglBtnCalendar.setDisable(true);
            } else {
                tglBtnCalendar.setDisable(false);
            }

            if(selection.equals(tglBtnInvoices)) {
                    content.getChildren().clear();
                    content.getChildren().add(guiController.invoiceContent());
                    tglBtnInvoices.setDisable(true);
            } else {
                tglBtnInvoices.setDisable(false);
            }

            if(selection.equals(tglBtnCustomers)){
                content.getChildren().clear();
                content.getChildren().add(guiController.customerContent());
                tglBtnCustomers.setDisable(true);
            } else {
                tglBtnCustomers.setDisable(false);
            }

            if(selection.equals(tglBtnControlPanel)) {
                content.getChildren().clear();
                content.getChildren().add(guiController.controlContent());
                tglBtnControlPanel.setDisable(true);
            } else {
                tglBtnControlPanel.setDisable(false);
            }
        });

        vBox.getStylesheets().add("presentation/style/style.css");
        vBox.getChildren().addAll(hBoxTop, content);
        scene       = new Scene(vBox, 1280, 720);
        return scene;
    }
}
