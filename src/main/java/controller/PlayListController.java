package controller;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.mpatric.mp3agic.Mp3File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import service.MusicInfo;
import service.Player;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayListController {

    @FXML AnchorPane bg_pane;
    @FXML private JFXListView<Mp3File> playListView;
    private Player player = Player.getPlayer();
    private MusicInfo musicInfo = MusicInfo.getPlayerInfo();
    private List<Mp3File> mp3Files = new ArrayList<>();
    private MainController mainController;
    private int nowIndex = 0;
    private boolean isPlaylist = true;

    @FXML
    public void initialize(){



        player.isChangedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1 && !isPlaylist){
                    playListView.getItems().clear();
                    if (player.getMp3File().getId3v2Tag()==null){
                        System.out.println(player.getMp3File().getId3v1Tag().getAlbum());
                        for (int i = 0; i < mp3Files.size(); i++) {
                            playListView.getItems().add(mp3Files.get(i));
                        }
                    }else {
                        mp3Files = musicInfo.getAlbumMap().get(player.getMp3File().getId3v2Tag().getAlbum());
                        mp3Files.forEach(playListView.getItems()::add);
                    }


                    playListView.setCellFactory(new Callback<ListView<Mp3File>, ListCell<Mp3File>>() {
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
                                    if (item.getId3v2Tag().getArtist()==null){
                                        label = new Label(item.getId3v2Tag().getTitle());
                                    }else {
                                        label = new Label(item.getId3v2Tag().getArtist()+" - "+item.getId3v2Tag().getTitle());
                                    }
                                    ap.getChildren().add(label);
                                    byte[] albumImage = item.getId3v2Tag().getAlbumImage();
                                    ImageView iv = new ImageView(new Image(new ByteArrayInputStream(albumImage)));
                                    iv.setSmooth(true);
                                    iv.setFitWidth(40);
                                    iv.setFitHeight(40);
                                    label.setGraphic(iv);
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
                                            nowIndex = getIndex();
                                            player.exInit(item);
                                        }
                                    });
                                }
                            };
                        }
                    });
                }
            }
        });
    }


    public JFXListView<Mp3File> getPlayListView() {
        return playListView;
    }

    public int getNowIndex() {
        return nowIndex;
    }

    public void setNowIndex(int nowIndex) {
        this.nowIndex = nowIndex;
    }

    public AnchorPane getBg_pane() {
        return bg_pane;
    }

    public boolean isPlaylist() {
        return isPlaylist;
    }

    public void setPlaylist(boolean playlist) {
        isPlaylist = playlist;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
