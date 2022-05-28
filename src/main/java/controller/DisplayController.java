package controller;

import com.jfoenix.controls.JFXTabPane;
import com.mpatric.mp3agic.ID3v2;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.Player;
import utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DisplayController {
    @FXML private ImageView iv;
    @FXML private AnchorPane bg_pane;
    @FXML private ScrollPane lyricsPane;
    @FXML private VBox scorllVbox;
    @FXML private JFXTabPane jfxTabPane;
    @FXML private Tab tab2;
    private byte[] albumImage;
    private ID3v2 id3v2Tag;
    private String lyrics;
    private String[] split;
    private Player player = Player.getPlayer();
    private MainController mainController;


    @FXML
    public void initialize(){


        iv.setEffect(new DropShadow(15, Color.BLACK));
        bg_pane.setStyle("-fx-background-color: #e9e9e9");

        lyricsPane.prefWidthProperty().addListener((observableValue, number, t1) -> scorllVbox.setPrefWidth(t1.doubleValue()));
        lyricsPane.prefHeightProperty().addListener((observableValue, number, t1) -> scorllVbox.setPrefHeight(t1.doubleValue()));
        lyricsPane.setStyle("-fx-background-color: transparent");

        //专辑封面，歌词设置
        player.isChangedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {

                if (t1){
                    ImageUtil imageUtil = new ImageUtil();
                    Image image = imageUtil.getImage(player.getMp3File());
                    iv.setImage(image);
                    albumImage = player.getMp3File().getId3v2Tag().getAlbumImage();
                    DisplayController.this.iv.setImage(new Image(new ByteArrayInputStream(albumImage)));
                    id3v2Tag = player.getMp3File().getId3v2Tag();
                    lyrics = id3v2Tag.getLyrics();
                    split = lyrics.split("\n");
                    scorllVbox.getChildren().clear();
//                for (String s : split) {
//                    System.out.println(s);
//                }

                    // 匹配歌词部分，去除广告或无关部分
                    for (String l : split) {
                        String p="\\[([0-9]+:[0-9]+.[0-9]+)\\]";
                        Pattern pattern=Pattern.compile(p);
                        Matcher m=pattern.matcher(l);
                        if (m.find()){
                            scorllVbox.getChildren().add(new Text(l.substring(10)));
                        }
                    }
                    //歌词面板水平拖拽条设置为居中
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lyricsPane.setHvalue(0.5);
                        }
                    });
                }
                }

        });


        //专辑封面y轴设置
        bg_pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (mainController.isDisplayPane()){
                    iv.setLayoutY(t1.doubleValue()/2-iv.getFitHeight()/2);
                }

            }
        });

    }

    public Tab getTab2() {
        return tab2;
    }

    public AnchorPane getBg_pane() {
        return bg_pane;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
