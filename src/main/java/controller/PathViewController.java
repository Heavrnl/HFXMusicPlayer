package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.stage.*;
import javafx.util.Callback;
import service.MusicInfo;
import utils.EventBusUtil;
import utils.IOutil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PathViewController {


    @FXML private Label header;
    @FXML private JFXListView<File> pathListView;
    @FXML private JFXButton add;
    @FXML private JFXButton close;

    private boolean isAdd = false;


    @FXML
    void close(ActionEvent event) throws InvalidDataException, IOException, UnsupportedTagException {
        Stage stage = (Stage)header.getScene().getWindow();
        List<File> pathList = MusicInfo.getPlayerInfo().getPathList();
        pathList.clear();
        pathListView.getItems().forEach(pathList::add);
        MusicInfo.getPlayerInfo().init();
        isAdd = false;
        EventBusUtil.getDefault().post(true);
        IOutil.write();
        stage.close();
    }

    @FXML
    void add(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择歌曲存放目录");
        File file = chooser.showDialog((Stage)pathListView.getScene().getWindow());
        if (file == null){
            return;
        }
        pathListView.getItems().add(file);

        isAdd = true;
    }

    @FXML
    public void initialize(){
        if (MusicInfo.getPlayerInfo()==null){
            return;
        }
        List<File> pathList = MusicInfo.getPlayerInfo().getPathList();
        pathList.forEach(pathListView.getItems()::add);

        pathListView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> fileListView) {
                return new JFXListCell<>(){
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty){
                            return;
                        }
                        JFXButton jfxButton = new JFXButton("移除");
                        jfxButton.setPrefSize(40,30);
                        jfxButton.setStyle("-fx-background-color: rgba(0,0,0,0.5);-fx-text-fill: white");
                        this.setGraphic(jfxButton);

                        jfxButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                pathListView.getItems().remove(getIndex());
                            }
                        });

                        jfxButton.hoverProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                if (t1){
                                    setCursor(Cursor.HAND);
                                    jfxButton.setStyle("-fx-background-color: rgb(77,178,255);-fx-text-fill: white");

                                }else {
                                    setCursor(Cursor.DEFAULT);
                                    jfxButton.setStyle("-fx-background-color: rgba(0,0,0,0.5);-fx-text-fill: white");
                                }
                            }
                        });

                    }
                };
            }
        });

    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public JFXListView<File> getPathListView() {
        return pathListView;
    }

}
