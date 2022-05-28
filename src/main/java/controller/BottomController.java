package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import service.MusicInfo;
import service.Player;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.io.ByteArrayInputStream;
import java.util.*;

public class BottomController {

    @FXML private AnchorPane bg_pane;
    @FXML private HBox hb;
    @FXML private Region region;
    @FXML private JFXButton b_playOrPause;
    @FXML private Text musicText;
    @FXML private ImageView img;
    @FXML private Line vol_proline;
    @FXML private Circle vol_circle;
    @FXML private Line bg_line;
    @FXML private Line progress_line;
    @FXML private Circle progress_circle;
    @FXML private Label right_time;
    @FXML private Label left_time;
    @FXML private JFXButton b_cycle;
    @FXML private JFXButton b_random;
    @FXML private JFXButton b_volcontrol;
    private Timeline timeline = new Timeline();
    private KeyValue kv1;
    private KeyValue kv2;
    private Duration mp_length;
    private Mp3File mp3File;
    private ID3v2 id3v2Tag;
    private MainController mainController;
    private double vol_save;
    private SimpleBooleanProperty isLoopOne = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isRandom = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isMute = new SimpleBooleanProperty(false);

    private Player player = Player.getPlayer();
    private MusicInfo musicInfo = MusicInfo.getPlayerInfo();

    @FXML
    void next(ActionEvent event) {
        if (isRandom.get()){
            randomList();
        }
        PlayListController playListController = mainController.getPlayListController();
        int nowIndex = playListController.getNowIndex();
        JFXListView<Mp3File> playListView = playListController.getPlayListView();
        if (nowIndex>=playListView.getItems().size()-1){
            player.exInit(playListView.getItems().get(0));
            playListController.setNowIndex(0);
        }
        else {
            player.exInit(playListView.getItems().get(nowIndex+1));
            playListController.setNowIndex(nowIndex+1);
        }
    }

    @FXML
    void previous(ActionEvent event) {
        PlayListController playListController = mainController.getPlayListController();
        int nowIndex = playListController.getNowIndex();
        JFXListView<Mp3File> playListView = playListController.getPlayListView();
        int size = playListView.getItems().size();
        if (nowIndex==0){
            player.exInit(playListView.getItems().get(size-1));
            playListController.setNowIndex(size-1);
        }
        else {
            player.exInit(playListView.getItems().get(nowIndex-1));
            playListController.setNowIndex(nowIndex-1);
        }
    }

    @FXML
    void random(ActionEvent event) {
        if (isRandom.get()){
            isRandom.set(false);
        }else {
            isRandom.set(true);
        }
    }

    @FXML
    void cycle(ActionEvent event) {
        if (isLoopOne.get()){
            isLoopOne.set(false);
        }else {
            isLoopOne.set(true);
        }
    }

    @FXML
    void mute(ActionEvent event) {
        if (isMute.get()){
            vol_circle.setLayoutX(vol_save);
            isMute.set(false);

        }else {
            vol_save = vol_circle.getLayoutX();
            vol_circle.setLayoutX(0);
            isMute.set(true);
        }
    }

    @FXML
    void bgLineSetVolume(MouseEvent event) {
        vol_circle.setLayoutX(event.getX());
    }

    @FXML
    void proLineSetVolume(MouseEvent event){
        vol_circle.setLayoutX(event.getX());
    }

    @FXML
    void bgJumpTo(MouseEvent event){
       progress_circle.setLayoutX(event.getX());
       player.getMediaPlayer().seek(Duration.millis(event.getX() / 400 * mp_length.toMillis()));
       timeline.playFrom(Duration.millis(event.getX() / 400 * mp_length.toMillis()));
    }

    @FXML
    void prolineJumpTo(MouseEvent event){
        progress_circle.setLayoutX(event.getX());
        player.getMediaPlayer().seek(Duration.millis(event.getX() / 400 * mp_length.toMillis()));
        timeline.playFrom(Duration.millis(event.getX() / 400 * mp_length.toMillis()));

    }

    @FXML
    void playOrPause(ActionEvent event) {
        if (player.isIsPlaying()){
            player.getMediaPlayer().pause();
            player.setIsPlaying(false);

        }else {
            player.getMediaPlayer().play();
            player.setIsPlaying(true);

        }
    }



    @FXML
    public void initialize(){
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

        bg_pane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                hb.setLayoutX(t1.doubleValue()/2-400/2);
                right_time.setLayoutX(hb.getLayoutX()+410);
                left_time.setLayoutX(hb.getLayoutX()-40);
            }
        });

        isMute.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    b_volcontrol.setStyle("-fx-background-image: url('/img/volume_close.png')");
                }else {
                    b_volcontrol.setStyle("-fx-background-image: url('/img/volume_open.png')");
                }
            }
        });

        //循环按键绑定
        isLoopOne.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    b_cycle.setStyle("-fx-background-image:  url('/img/loop-once.png')");
                }else {
                    b_cycle.setStyle("-fx-background-image:  url('/img/play-cycle.png')");
                }
            }
        });

        //随机按键绑定
        isRandom.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    b_random.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
                }else {
                    b_random.setStyle("-fx-background-color: transparent");
                }
            }
        });

        //播放按钮绑定
        player.isPlayingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    b_playOrPause.setStyle("-fx-background-image: url('/img/pause.png')");
                    timeline.play();
                }else {
                    b_playOrPause.setStyle("-fx-background-image: url('/img/play.png')");
                    timeline.pause();
                }
            }
        });


        //绑定显示时间
        timeline.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                String s = DurationFormatUtils.formatDuration((long) player.getMediaPlayer().getCurrentTime().toMillis(), "mm:ss", true);
                left_time.setText(s);
            }
        });

        //进度条初始化
        kv1 = new KeyValue(progress_line.endXProperty(),0,Interpolator.LINEAR);
        kv2 = new KeyValue(progress_line.endXProperty(),bg_line.getEndX(), Interpolator.LINEAR);
        progress_line.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                progress_circle.setLayoutX(t1.doubleValue());
            }
        });




        player.isChangedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1){
                    mp3File = player.getMp3File();
                    id3v2Tag = mp3File.getId3v2Tag();
                    //设置缩略图
                    byte[] albumImage = id3v2Tag.getAlbumImage();
                    img.setImage(new Image(new ByteArrayInputStream(albumImage)));
                    //设置歌曲，歌手信息
                    musicText.setText(id3v2Tag.getArtist()+" - "+id3v2Tag.getTitle());
                    //初始化歌曲长度
                    long length = mp3File.getLengthInMilliseconds();
                    String s = DurationFormatUtils.formatDuration(length, "mm:ss", true);
                    right_time.setText(s);

                    //初始化进度条
                    mp_length = Duration.seconds(mp3File.getLengthInSeconds()) ;
                    //重置动画时间
                    if (timeline.getKeyFrames().size() !=0 ){
                        timeline.getKeyFrames().remove(0,timeline.getKeyFrames().size());
                    }
                    timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0),kv1),new KeyFrame(mp_length,kv2));
                    timeline.playFromStart();
                }
            }
        });



        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isLoopOne.get()){
                    player.getMediaPlayer().seek(Duration.seconds(0));
                    player.getMediaPlayer().play();
                    timeline.playFromStart();
                    return;
                }
                PlayListController playListController = mainController.getPlayListController();
                int nowIndex = playListController.getNowIndex();
                JFXListView<Mp3File> playListView = playListController.getPlayListView();
                if (isRandom.get()){
                    randomList();
                }

                if (nowIndex>=playListView.getItems().size()-1){
                    player.exInit(playListView.getItems().get(0));
                    playListController.setNowIndex(0);
                }
                else {
                    player.exInit(playListView.getItems().get(nowIndex+1));
                    playListController.setNowIndex(nowIndex+1);
                }

            }
        });

        /**
         * 音量条设置
         */
        vol_circle.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.doubleValue()>0){
                    isMute.set(false);
                }
                vol_proline.setEndX(t1.doubleValue());
                player.getMediaPlayer().setVolume(t1.doubleValue()/100);
            }
        });

        vol_circle.setLayoutX(player.getDefaultVolume()*100);
    }
    private void randomList(){
        JFXListView<Mp3File> playListView = mainController.getPlayListController().getPlayListView();
        playListView.getItems().clear();
        int i = 0;
        while (i < 15 || playListView.getItems().size() == musicInfo.getMp3File_list().size()) {
            playListView.getItems().add(musicInfo.getMp3File_list().get(i));
            i += 1;
        }
        Collections.shuffle(playListView.getItems());
        isRandom.set(false);
    }


    public AnchorPane getBg_pane() {
        return bg_pane;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }



    public ImageView getImg() {
        return img;
    }
}
