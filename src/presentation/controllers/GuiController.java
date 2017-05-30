package presentation.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Controller;
import presentation.*;

import java.io.FileNotFoundException;

//Used to pass Gui controls to Gui classes.
public class GuiController implements IGuiController {
    //Jesper, Bertram
    private Controller controller;
    private LoginScene loginScene;
    public MasterScene masterScene;
    private Stage primaryStage;
    private CalendarNode calendarNode;
    private InvoiceNode invoiceNode;
    private ControlPanelNode controlPanelNode;
    private CustomerNode customerNode;
    private WindowControls windowControls;
    private LogoNode logoNode;

    public GuiController(Controller controller) {
        this.controller = controller;
        this.loginScene = new LoginScene();
        this.primaryStage = new Stage();
        this.masterScene = new MasterScene();
        this.calendarNode = new CalendarNode();
        this.invoiceNode = new InvoiceNode();
        this.controlPanelNode = new ControlPanelNode();
        this.customerNode = new CustomerNode();
        this.windowControls = new WindowControls();
        this.logoNode = new LogoNode();
    }

    @Override
    public Scene loginScene() {
        return loginScene.getScene(this, controller, primaryStage);
    }

    @Override
    public Scene masterScene() {
        return masterScene.getMasterScene(this);
    }

    @Override
    public HBox calendarContent() {
        return calendarNode.getCalendar(this, controller, primaryStage);
    }

    @Override
    public HBox invoiceContent() {
            return invoiceNode.getHbox(this, controller, primaryStage);
    }

    @Override
    public HBox controlContent() {
        return controlPanelNode.getHbox(this, controller, primaryStage);
    }

    @Override
    public HBox customerContent() {
        return customerNode.getHbox(controller, primaryStage);
    }

    @Override
    public HBox windowControl() {
        return windowControls.gethBoxWindowControls(primaryStage);
    }

    @Override
    public HBox showLogo() {
        return logoNode.hBoxShowLogo();
    }


    public Stage getStage() {
        return primaryStage;
    }

    public Controller getController() {
        return controller;
    }
}
