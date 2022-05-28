package utils;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class DropShadowHelper {

    private static final double SHADOW_SIZE = 5.0;//阴影范围
    private static final double PADDING_SIZE = 5.0;//内边距范围

    public static void setDropShadow(Stage stage){
        BorderPane borderPane = new BorderPane();
        Parent root = stage.getScene().getRoot();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        borderPane.setCenter(root);
        root.setEffect(new DropShadow(SHADOW_SIZE, Color.BLACK));
        borderPane.setPadding(new Insets(PADDING_SIZE));
        scene.setFill(Color.TRANSPARENT);
        borderPane.setStyle("-fx-background-color: null");
    }
}
