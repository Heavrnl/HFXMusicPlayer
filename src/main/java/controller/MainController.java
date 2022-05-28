package controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import service.MusicInfo;


public class MainController {

    @FXML private BottomController bottomController;
    @FXML private MainViewController mainViewController;
    @FXML private TopController topController;
    @FXML private DisplayController displayController;
    @FXML private AlbumController albumController;
    @FXML private PlayListController playListController;
    @FXML private BorderPane bp;
    private KeyValue kv1,kv2;
    private Timeline timeline = new Timeline();
    private AnchorPane displayerPane;
    private boolean isDisplayPane = false;

    @FXML
    public void initialize(){

        bottomController.setMainController(this);
        mainViewController.setMainController(this);
        topController.setMainController(this);
        displayController.setMainController(this);
        albumController.setMainController(this);
        playListController.setMainController(this);

        if (MusicInfo.getPlayerInfo() ==null){
            return;
        }


        //显示音乐主页面
        bottomController.getImg().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BorderPane root = (BorderPane) bp.getScene().getRoot();
                displayerPane = displayController.getBg_pane();
                //加入当动画进行时无法点击的条件，防止异常
                if (timeline.getStatus() != Animation.Status.RUNNING) {
                    if (isDisplayPane) {
                        isDisplayPane = false;
                        kv1 = new KeyValue(displayerPane.layoutYProperty(), 0);
                        kv2 = new KeyValue(displayerPane.layoutYProperty(), -680, Interpolator.EASE_OUT);
                        timeline.getKeyFrames().remove(0, timeline.getKeyFrames().size());
                        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), kv1), new KeyFrame(Duration.seconds(0.25), kv2));
                        AnchorPane.setTopAnchor(displayerPane, null);
                        AnchorPane.setBottomAnchor(displayerPane, null);
                        timeline.play();
                        root.setPadding(new Insets(0,5,5,5));
                        timeline.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                root.setPadding(new Insets(5));
                            }
                        });
                    } else
                        {
                        isDisplayPane = true;
                        kv1 = new KeyValue(displayerPane.layoutYProperty(), -680);
                        kv2 = new KeyValue(displayerPane.layoutYProperty(), 0, Interpolator.EASE_OUT);
                        timeline.getKeyFrames().remove(0, timeline.getKeyFrames().size());
                        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), kv1), new KeyFrame(Duration.seconds(0.25), kv2));
                        timeline.play();
                        root.setPadding(new Insets(0,5,5,5));
                        timeline.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                root.setPadding(new Insets(5));
                                AnchorPane.setTopAnchor(displayerPane, 0.0);
                                AnchorPane.setBottomAnchor(displayerPane, 0.0);
                            }
                        });

                    }
                }
            }
        });


        displayController.getTab2().setContent(playListController.getPlayListView());
    }


    public boolean isDisplayPane() {
        return isDisplayPane;
    }

    public PlayListController getPlayListController() {
        return playListController;
    }
}


