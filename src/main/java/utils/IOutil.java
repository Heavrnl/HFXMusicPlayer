package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.MusicInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOutil {
    private final static String FILE = "src/main/resources/path.txt";

    public static List<File> read() throws IOException {
        List<File> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String s;
        while ((s = br.readLine())  != null) {
            list.add(new File(s));
        }
        return list;
    }

    public static void write() throws IOException {
        MusicInfo playerInfo = MusicInfo.getPlayerInfo();
        List<File> pathList = playerInfo.getPathList();
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));
        for (int i = 0; i < pathList.size(); i++) {
            bw.write(pathList.get(i).getPath());
            bw.newLine();
        }
        bw.flush();
        bw.close();

    }
}
