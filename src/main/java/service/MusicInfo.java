package service;

import CusComponent.CusLabel;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import utils.ID3v2Util;
import utils.IOutil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化路径下所有音乐信息
 */
public class MusicInfo {

    private static MusicInfo musicInfo;
    private List<File> pathList = new ArrayList<>();
    private List<Mp3File> mp3File_list = new ArrayList<>();
    private List<CusLabel> cusLabelList = new ArrayList<>();
    private Map<String,List<Mp3File>> albumMap = new HashMap<>();//存放专辑Map
    static {
        try {
            musicInfo = new MusicInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MusicInfo() throws Exception {
        /**
         * 读取音乐信息
         */
        pathList = IOutil.read();
        if (pathList.size()==0){
            return;
        }

        ID3v2 id3v2Tag;
        File[] files;
        for (int i = 0; i < pathList.size(); i++) {
            files = pathList.get(i).listFiles();
            for (int j = 0; j < files.length; j++) {
                if (!files[j].getName().matches("^.*\\.mp3$")) {
                    continue;
                }
                mp3File_list.add(new Mp3File(pathList.get(i)+"/"+files[j].getName()));
                if (mp3File_list.get(mp3File_list.size()-1).getId3v2Tag()==null){
                    ID3v2Util.setID3v2(mp3File_list.get(mp3File_list.size()-1), files[j].getName());
                }
            };
        }


        //把歌曲放进专辑Map
        for (int i = 0; i < mp3File_list.size(); i++) {
            id3v2Tag = mp3File_list.get(i).getId3v2Tag();
            if (!albumMap.containsKey(id3v2Tag.getAlbum())){
                albumMap.put(id3v2Tag.getAlbum(),new ArrayList<>());
            }
            albumMap.get(id3v2Tag.getAlbum()).add(mp3File_list.get(i));
        }
        for (String key : albumMap.keySet()) {
            cusLabelList.add(new CusLabel(key,albumMap.get(key)));
        }
    }

    public void init() throws InvalidDataException, IOException, UnsupportedTagException {
        mp3File_list.clear();
        albumMap.clear();
        cusLabelList.clear();
        ID3v2 id3v2Tag;
        File[] files;
        for (int i = 0; i < pathList.size(); i++) {
            files = pathList.get(i).listFiles();
            for (int j = 0; j < files.length; j++) {
                if (!files[j].getName().matches("^.*\\.mp3$")) {
                    continue;
                }
                mp3File_list.add(new Mp3File(pathList.get(i)+"/"+files[j].getName()));
                if (mp3File_list.get(mp3File_list.size()-1).getId3v2Tag()==null){
                    ID3v2Util.setID3v2(mp3File_list.get(mp3File_list.size()-1), files[j].getName());
                }
            };

        }

        //把歌曲放进专辑Map

        for (int i = 0; i < mp3File_list.size(); i++) {
            id3v2Tag = mp3File_list.get(i).getId3v2Tag();
            if (!albumMap.containsKey(id3v2Tag.getAlbum())){
                albumMap.put(id3v2Tag.getAlbum(),new ArrayList<>());
            }
            albumMap.get(id3v2Tag.getAlbum()).add(mp3File_list.get(i));
        }

        for (String key : albumMap.keySet()) {
            cusLabelList.add(new CusLabel(key,albumMap.get(key)));
        }
    }

    public static MusicInfo getPlayerInfo() {
        return musicInfo;
    }

    public List<CusLabel> getCusLabelList() {
        return cusLabelList;
    }

    public Map<String, List<Mp3File>> getAlbumMap() {
        return albumMap;
    }

    public List<File> getPathList() {
        return pathList;
    }

    public void setPathList(List<File> pathList) {
        this.pathList = pathList;
    }

    public List<Mp3File> getMp3File_list() {
        return mp3File_list;
    }

    public void setMp3File_list(List<Mp3File> mp3File_list) {
        this.mp3File_list = mp3File_list;
    }

    public void setCusLabelList(List<CusLabel> cusLabelList) {
        this.cusLabelList = cusLabelList;
    }


}
