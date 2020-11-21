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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        List<ForumPost> postList = new ArrayList<>();
        postList.add(new ForumPost("Hello", "user1", "content1"));
        postList.add(new ForumPost("Good Morning", "user2", "content2"));
        postList.add(new ForumPost("Thanks", "user3", "content3"));
        postList.add(new ForumPost("Happy", "user4", "content4"));
        postList.add(new ForumPost("See This", "user5", "content5"));

        ForumPost.ForumPostAdapter postAdapter = new ForumPost.ForumPostAdapter(this, postList);

        TextView textView = (TextView) findViewById(R.id.board_name);
        ListView listView = (ListView) findViewById(R.id.post_list);
        listView.setAdapter(postAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ForumPost post = postList.get(i);
                Intent intent = new Intent(BoardActivity.this, BoardActivity.class /* change to PostActivity */);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });

        ForumBoard board = (ForumBoard) getIntent().getSerializableExtra("board");
        textView.setText(board.getName());
    }
}