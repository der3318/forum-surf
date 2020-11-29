package com.der3318.forumsurf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private static ForumBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        TextView textView = (TextView) findViewById(R.id.board_name);
        ListView listView = (ListView) findViewById(R.id.post_list);

        /* try get data from parent page */
        ForumBoard boardReceived = (ForumBoard) getIntent().getSerializableExtra("board");
        if (boardReceived != null) {
            board = boardReceived;
        }

        /* page data */
        List<ForumPost> postList = new ArrayList<>();
        postList.add(new ForumPost("Hello", "user1", "content1"));
        postList.add(new ForumPost("Good Morning", "user2", "content2"));
        postList.add(new ForumPost("Thanks", "user3", "content3"));
        postList.add(new ForumPost("Happy", "user4", "content4"));
        postList.add(new ForumPost("See This", "user5", "content5"));
        ForumPost.ForumPostAdapter postAdapter = new ForumPost.ForumPostAdapter(this, postList);

        /* update view */
        textView.setText(board.getName());
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ForumPost post = postList.get(i);
                Intent intent = new Intent(BoardActivity.this, PostActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });

    }

}