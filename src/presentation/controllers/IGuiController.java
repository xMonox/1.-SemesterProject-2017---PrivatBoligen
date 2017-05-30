package presentation.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;

public interface IGuiController {
    //Jesper, Bertram
    Scene loginScene();
    Scene masterScene();
    HBox calendarContent();
    HBox invoiceContent() throws FileNotFoundException;
    HBox controlContent();
    HBox customerContent();
    HBox windowControl();
    HBox showLogo();

}
