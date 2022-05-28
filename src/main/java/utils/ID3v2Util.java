package utils;

import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.io.RandomAccessFile;

public class ID3v2Util {

    public static Mp3File setID3v2(Mp3File mp3File,String s){

        if (s.matches("^.*\\.mp3$")){
            s = s.replace(".mp3", "");
        }

        mp3File.setId3v2Tag(new AbstractID3v2Tag() {
            @Override
            public void setTitle(String s) {
                super.setTitle(s);
            }

            @Override
            public void setArtist(String s) {
                super.setArtist(s);
            }

            @Override
            public void setLyrics(String s) {
                super.setLyrics(s);
            }

            @Override
            public byte[] getAlbumImage() {
                return super.getAlbumImage();
            }

            @Override
            public void setAlbum(String s) {
                super.setAlbum(s);
            }

            @Override
            protected void unpackFlags(byte[] bytes) {

            }

            @Override
            protected void packFlags(byte[] bytes, int i) {

            }
        });
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/img/empty.png", "r");
            byte[] bytes = new byte[(int) file.length()];
            file.read(bytes);
            file.close();
            mp3File.getId3v2Tag().setAlbumImage(bytes, "image/png");
        }catch (Exception e){
            System.out.println(e);
        }
        mp3File.getId3v2Tag().setTitle(s);
        mp3File.getId3v2Tag().setArtist("");
        mp3File.getId3v2Tag().setLyrics("无歌词");
        mp3File.getId3v2Tag().setAlbum(s);
        return mp3File;
    }
}
