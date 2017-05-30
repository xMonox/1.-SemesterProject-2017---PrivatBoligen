package presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Created by Mono on 26-05-2017.
 */
public class LogoNode {
    private HBox hBoxLogo;
    private Image image;
    private ImageView viewLogo;

    //Jesper
    public HBox hBoxShowLogo() {
        image = new Image("presentation/style/logo_privatboligen_punch copy.png");
        viewLogo = new ImageView();
        viewLogo.setImage(image);
        viewLogo.setSmooth(true);
        viewLogo.setCache(true);

        hBoxLogo = new HBox();
        //hBoxLogo.setMinWidth(1280);
        hBoxLogo.setMinHeight(200);
        hBoxLogo.setPadding(new Insets(10));
        hBoxLogo.setAlignment(Pos.BOTTOM_CENTER);
        hBoxLogo.getChildren().add(viewLogo);
        return hBoxLogo;
    }
}
