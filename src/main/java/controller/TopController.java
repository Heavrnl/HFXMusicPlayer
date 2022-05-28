package controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import utils.DropShadowHelper;
import java.io.IOException;

public class TopController {
    @FXML Region region;
    @FXML JFXButton btn_playlist;
//    private JFXDialog jfxDialog;
    private MainController mainController;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage)region.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maximize(ActionEvent event) {
        Stage stage = (Stage)region.getScene().getWindow();
        stage.setMaximized(true);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)region.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void openFile(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage)region.getScene().getWindow();
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        AnchorPane ap = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml/pathViewController.fxml"));
        stage.setScene(new Scene(ap));
        DropShadowHelper.setDropShadow(stage);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }



    @FXML
    public void initialize() throws Exception {
        Image image = new Image(this.getClass().getResource("/img/1111.jpg").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100,100,true,true,true,true);
        BackgroundPosition backgroundPosition = new BackgroundPosition(Side.LEFT,1,true,Side.BOTTOM,1,true);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                backgroundPosition,
                backgroundSize);
        Background bg = new Background(backgroundImage);
        region.setEffect(new GaussianBlur(5));
        region.setBackground(bg);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public JFXButton getBtn_playlist() {
        return btn_playlist;
    }
}
