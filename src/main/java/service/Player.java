package service;

import com.mpatric.mp3agic.Mp3File;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;

public class Player {
    private static Player player = new Player();
    private MusicInfo musicInfo = MusicInfo.getPlayerInfo();
    private MediaPlayer mediaPlayer;
    private double defaultVolume = 0.1; //默认音量
    private Media media;
    private Mp3File mp3File;
    private File file;
    private URI uri;
    private SimpleBooleanProperty isChanged = new SimpleBooleanProperty(false); //歌曲是否重置或切换
    private SimpleBooleanProperty isPlaying = new SimpleBooleanProperty(false);

    /**
     * 初始化播放器
     */
    private Player() {
        if (MusicInfo.getPlayerInfo().getPathList().size()==0){
            return;
        }
        this.file = new File(musicInfo.getMp3File_list().get(0).getFilename());
        uri = file.toURI();
        media = new Media(uri.toString());
        mediaPlayer = new MediaPlayer(media);
    }



    /**
     * 初始化播放音乐信息
     */
    private void initInfo(){
        file = new File(mp3File.getFilename());
        uri = file.toURI();
        media = new Media(uri.toString());
        mediaPlayer = new MediaPlayer(media);
//        nowIndex.set(index);
    }

    /**
     * 初始化播放器并播放音乐
     */
    public void initPlayer(Mp3File File){
        mediaPlayer.dispose();
        initInfo();
        isChanged.set(false);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                setIsPlaying(true);
                mediaPlayer.setVolume(defaultVolume);
                mediaPlayer.play();
            }
        });
    }

    public void exInit(Mp3File file){

        this.mp3File = file;
        isChanged.set(true);
        initPlayer(file);
    }

    public boolean isIsPlaying() {
        return isPlaying.get();
    }

    public SimpleBooleanProperty isPlayingProperty() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying.set(isPlaying);
    }

    public double getDefaultVolume() {
        return defaultVolume;
    }

    public void setDefaultVolume(double defaultVolume) {
        this.defaultVolume = defaultVolume;
    }

    public boolean isIsChanged() {
        return isChanged.get();
    }

    public SimpleBooleanProperty isChangedProperty() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged.set(isChanged);
    }

    public static Player getPlayer() {
        return player;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }

    public void setMp3File(Mp3File mp3File) {
        this.mp3File = mp3File;
    }
}
