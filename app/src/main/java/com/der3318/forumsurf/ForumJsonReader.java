package com.der3318.forumsurf;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ForumJsonReader {

    private final Context context;
    private final int resourceId;
    private final String fileName;

    public ForumJsonReader(Context context, int resourceId, String fileName) {
        this.context = context;
        this.resourceId = resourceId;
        this.fileName = fileName;
    }

    public List<ForumBoard> readBoardList() {
        String json = this.readJsonString();
        return json != null ? this.parseJsonToBoardList(json) : new ArrayList<>();
    }

    private String readJsonString() {
        InputStream inputStream = null;
        try {
            File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(download, this.fileName);
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            inputStream = this.context.getResources().openRawResource(this.resourceId);
        }
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : null;
    }

    private List<ForumBoard> parseJsonToBoardList(String json) {
        List<ForumBoard> boardList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("boards");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonBoard = jsonArray.getJSONObject(index);
                String name = jsonBoard.getString("name");
                String type = jsonBoard.getString("type");
                String token = jsonBoard.getString("token");
                boardList.add(new ForumBoard(name, ForumProcessor.Type.valueOf(type), token));
            }
        } catch (Exception ignored) {
        }
        return boardList;
    }

}
