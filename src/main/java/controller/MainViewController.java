package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import service.MusicInfo;
import utils.EventBusUtil;


public class MainViewController {

    @FXML private AnchorPane bg_pane;
    @FXML private ScrollPane scroll_pane;
    @FXML private JFXMasonryPane jfxMasonryPane;
    private MainController mainController;

    public MainViewController(){
        EventBusUtil.getDefault().register(this);
    }

    @FXML
    public void initialize() {
        if (MusicInfo.getPlayerInfo()==null){
            return;
        }
        scroll_pane.widthProperty().addListener((observableValue, number, t1) -> jfxMasonryPane.setPrefWidth(t1.doubleValue()));
        jfxMasonryPane.setPadding(new Insets(40,0,0,40));
        jfxMasonryPane.setVSpacing(40);
        jfxMasonryPane.setHSpacing(35);
        MusicInfo musicInfo = MusicInfo.getPlayerInfo();
        for (int i = 0; i < musicInfo.getCusLabelList().size(); i++) {
            jfxMasonryPane.getChildren().add(musicInfo.getCusLabelList().get(i));
        }

    }

    @Subscribe
    private void reInit(Boolean b){
        jfxMasonryPane.getChildren().clear();
        MusicInfo musicInfo = MusicInfo.getPlayerInfo();
        System.out.println(musicInfo.getCusLabelList().size());
        for (int i = 0; i < musicInfo.getCusLabelList().size(); i++) {
            jfxMasonryPane.getChildren().add(musicInfo.getCusLabelList().get(i));
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
