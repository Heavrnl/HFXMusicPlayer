package CusComponent;

import com.jfoenix.controls.JFXButton;
import com.mpatric.mp3agic.Mp3File;
import controller.AlbumController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utils.ImageUtil;
import java.util.List;



public class CusLabel extends Label {
    private int index;
    private Mp3File mp3File;
    private byte[] albumImage;
    private ImageView iv;
    private String album;
    private List<Mp3File> songList;
    private Timeline timeline = new Timeline();
    private KeyValue kv1;
    private KeyValue kv2;
    private ImageUtil imageUtil = new ImageUtil();

    public CusLabel(String album, List<Mp3File> songList){
        super();
        this.album = album;
        this.songList = songList;

        Mp3File mp3File = songList.get(0);
        iv = imageUtil.getIv(mp3File, 170, 170);
        this.iv.setFitHeight(170);
        this.iv.setFitWidth(170);
        this.setPrefSize(150,150);
        JFXButton jfxButton = new JFXButton(album);
        jfxButton.setStyle("-fx-font-weight: bold;-fx-text-fill: white;-fx-background-radius: 15;-fx-border-radius: 15;-fx-background-color: rgba(0,0,0,0.8)");
        jfxButton.setDisable(true);
        VBox vBox = new VBox();
        vBox.getChildren().add(this.iv);
        vBox.getChildren().add(jfxButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setGraphic(vBox);
        this.iv.setEffect(new DropShadow(10.0, Color.BLACK));

        jfxButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        this.iv.fitWidthProperty().bind(this.iv.fitHeightProperty());

        this.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){

                    setCursor(Cursor.HAND);
                    if (timeline.getStatus() == Animation.Status.RUNNING) {
                        timeline.stop();
                    }
                    timeline.getKeyFrames().clear();
                    kv1 = new KeyValue(CusLabel.this.iv.fitHeightProperty(), 170);
                    kv2 = new KeyValue(CusLabel.this.iv.fitHeightProperty(), 195);
                    timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), kv1), new KeyFrame(Duration.seconds(0.2), kv2));
                    timeline.play();
                }else {
                    setCursor(Cursor.DEFAULT);
                    if (timeline.getStatus() == Animation.Status.RUNNING) {
                        timeline.stop();
                    }
                    timeline.getKeyFrames().clear();
                    kv1 = new KeyValue(CusLabel.this.iv.fitHeightProperty(), 195);
                    kv2 = new KeyValue(CusLabel.this.iv.fitHeightProperty(), 170);
                    timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), kv1), new KeyFrame(Duration.seconds(0.2), kv2));
                    timeline.play();
                }
            }
        });

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AlbumController.isVisProperty().set(true);
                AlbumController.setIsInit(true,songList);
            }
        });


    }
}
