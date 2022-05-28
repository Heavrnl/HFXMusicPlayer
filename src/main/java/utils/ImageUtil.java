package utils;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ImageUtil {
     private Image image;
     private ImageView imageView = new ImageView();
     private byte[] albumImage;


    public ImageView getIv(Mp3File mp3File,double width,double height){
        imageView.setSmooth(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();
        if (id3v2Tag == null||mp3File.getId3v2Tag() == null||id3v2Tag.getAlbumImage()==null){
            image = new Image(this.getClass().getResource("/img/empty.png").toExternalForm());
            imageView.setImage(image);
            return imageView;
        }

        albumImage = id3v2Tag.getAlbumImage();
        image = new Image(new ByteArrayInputStream(albumImage));
        imageView.setImage(image);
        return imageView;
    }

    public ImageView getIv(Mp3File mp3File){
        imageView.setSmooth(true);
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();
        if (id3v2Tag == null||mp3File.getId3v2Tag() == null){
            image = new Image(this.getClass().getResource("/img/empty.png").toExternalForm());
            imageView.setImage(image);
            return imageView;
        }

        albumImage = id3v2Tag.getAlbumImage();
        image = new Image(new ByteArrayInputStream(albumImage));
        imageView.setImage(image);
        return imageView;
    }

    public Image getImage(Mp3File mp3File){
        imageView.setSmooth(true);
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();
        albumImage = id3v2Tag.getAlbumImage();
        image = new Image(new ByteArrayInputStream(albumImage));
        return image;
    }
}
