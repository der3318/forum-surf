package com.der3318.forumsurf;

import android.content.Intent;
import android.os.AsyncTask;
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

        /* data placeholder */
        List<ForumBoard> boardList = new ArrayList<>();
        ForumBoard.ForumBoardAdapter boardAdapter = new ForumBoard.ForumBoardAdapter(this, boardList);

        /* read config and process json */
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                boardList.addAll(new ForumJsonReader(getApplicationContext(), R.raw.forumsurf, "forumsurf.json").readBoardList());
                boardAdapter.notifyDataSetChanged();
            }
        });

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