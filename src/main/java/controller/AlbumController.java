package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import service.Player;
import utils.ImageUtil;

import java.io.ByteArrayInputStream;
import java.util.List;

public class AlbumController {

    @FXML private AnchorPane bg_pane;
    @FXML private Label albumCover;
    @FXML private Label albumTitle;
    @FXML private Label artist;
    @FXML private Label musicCount;
    @FXML private JFXButton playAll;
    @FXML private JFXButton exit;
    @FXML private JFXListView<Mp3File> songListView;
    @FXML private AnchorPane vbox_bgpane;
    @FXML private VBox vbox;
    private byte[] albumImage;
    private ImageView iv;
    private ID3v2 id3v2;
    private MainController mainController;
    private Player player = Player.getPlayer();
    static List<Mp3File> songList_info;
    static SimpleBooleanProperty  isVis = new SimpleBooleanProperty(false);
    static SimpleBooleanProperty  isInit = new SimpleBooleanProperty(false);

    @FXML
    void exit(ActionEvent event) {
        bg_pane.setVisible(false);
        isVis.set(false);
    }


    @FXML
    void playListAll(ActionEvent event) {
        mainController.getPlayListController().setPlaylist(false);
        player.exInit(songListView.getItems().get(0));
        mainController.getPlayListController().setNowIndex(0);
        mainController.getPlayListController().setPlaylist(true);
    }

    @FXML public void initialize() {

        bg_pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setLayoutY(t1.doubleValue()/2-vbox.getPrefHeight()/2);
            }
        });

        //初始化列表
        isInit.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    songListView.getItems().remove(0,songListView.getItems().size());

                    for (int i = 0; i < songList_info.size(); i++) {
                        songListView.getItems().add(songList_info.get(i));
                    }
                    id3v2 = songList_info.get(0).getId3v2Tag();

                    ImageUtil imageUtil = new ImageUtil();
                    iv = imageUtil.getIv(songList_info.get(0), 350, 350);
                    albumCover.setGraphic(AlbumController.this.iv);
                    albumCover.setEffect(new DropShadow(10, Color.BLACK));
                    albumTitle.setText(id3v2.getAlbum());
                    artist.setText(id3v2.getArtist());

                    musicCount.setText("歌曲总数:"+songList_info.size());

                    isInit.set(false);

                    songListView.setCellFactory(new Callback<ListView<Mp3File>, ListCell<Mp3File>>() {
                        @Override
                        public ListCell<Mp3File> call(ListView<Mp3File> mp3FileListView) {
                            return new JFXListCell<>(){
                                @Override
                                protected void updateItem(Mp3File item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty){
                                        return;
                                    }
                                    this.setText("");
                                    AnchorPane ap = new AnchorPane();
                                    Label label;
                                    if (item.getId3v2Tag() == null){
                                        label = new Label(this.getIndex() + 1 + ". " + item.getFilename());
                                    }else {
                                        label = new Label(this.getIndex() + 1 + ". " + item.getId3v2Tag().getTitle());
                                    }
                                    ap.getChildren().add(label);
                                    setGraphic(ap);
                                    this.hoverProperty().addListener(new ChangeListener<Boolean>() {
                                        @Override
                                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                            if (t1){
                                                setCursor(Cursor.HAND);
                                            }else {
                                                setCursor(Cursor.DEFAULT);
                                            }
                                        }
                                    });
                                    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            mainController.getPlayListController().setPlaylist(false);
                                            player.exInit(item);
                                            mainController.getPlayListController().setNowIndex(getIndex());
                                            mainController.getPlayListController().setPlaylist(true);
                                        }
                                    });
                                }
                            };
                        }
                    });
                }
            }
        });


        isVis.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    bg_pane.setVisible(true);
                    isVis.set(false);
                }
            }
        });

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public static void setIsInit(boolean isInit,List<Mp3File> List) {

        songList_info = List;
        AlbumController.isInit.set(isInit);

    }

    public static SimpleBooleanProperty isVisProperty() {
        return isVis;
    }

}
