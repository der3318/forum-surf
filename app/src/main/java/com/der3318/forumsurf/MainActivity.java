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
        boardList.add(new ForumBoard("Soft Job", ForumProcessor.Type.PTT, "Soft_Job"));
        boardList.add(new ForumBoard("Tech Job", ForumProcessor.Type.PTT, "Tech_Job"));
        boardList.add(new ForumBoard("Stock", ForumProcessor.Type.PTT, "Stock"));
        boardList.add(new ForumBoard("Software Engineer", ForumProcessor.Type.DCARD, "softwareengineer"));
        boardList.add(new ForumBoard("Frontend Engineer", ForumProcessor.Type.DCARD, "f2e"));
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