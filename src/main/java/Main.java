import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.DropShadowHelper;
import utils.ResizeHelper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane bp =  FXMLLoader.load(getClass().getResource("fxml/mainController.fxml"));
        primaryStage.setScene(new Scene(bp));
        DropShadowHelper.setDropShadow(primaryStage);
        ResizeHelper.setResize(primaryStage,false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
