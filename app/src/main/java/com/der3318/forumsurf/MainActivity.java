package com.der3318.forumsurf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ForumBoard> boardList = new ArrayList<>();
        boardList.add(new ForumBoard("Soft Job", "PPT"));
        boardList.add(new ForumBoard("Tech Job", "PPT"));
        boardList.add(new ForumBoard("Stock", "PPT"));
        boardList.add(new ForumBoard("Software Engineer", "D Card"));
        boardList.add(new ForumBoard("Frontend Engineer", "D Card"));

        ForumBoardAdapter boardAdapter = new ForumBoardAdapter(this, boardList);

        ListView listView = (ListView) findViewById(R.id.board_list);
        listView.setAdapter(boardAdapter);
    }
}