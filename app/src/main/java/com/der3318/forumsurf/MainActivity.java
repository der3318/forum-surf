package com.der3318.forumsurf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.board_list);

        /* page data */
        List<ForumBoard> boardList = new ArrayList<>();
        boardList.add(new ForumBoard("Soft Job", "PPT"));
        boardList.add(new ForumBoard("Tech Job", "PPT"));
        boardList.add(new ForumBoard("Stock", "PPT"));
        boardList.add(new ForumBoard("Software Engineer", "D Card"));
        boardList.add(new ForumBoard("Frontend Engineer", "D Card"));
        ForumBoard.ForumBoardAdapter boardAdapter = new ForumBoard.ForumBoardAdapter(this, boardList);

        /* update view */
        listView.setAdapter(boardAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ForumBoard board = boardList.get(i);
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                intent.putExtra("board", board);
                startActivity(intent);
            }
        });

    }

}