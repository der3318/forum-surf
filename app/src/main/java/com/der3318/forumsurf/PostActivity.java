package com.der3318.forumsurf;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private static ForumPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        TextView textViewTitle = (TextView) findViewById(R.id.post_title);
        TextView textViewUser = (TextView) findViewById(R.id.post_user);
        TextView textViewContent = (TextView) findViewById(R.id.post_content);
        ListView listView = (ListView) findViewById(R.id.comment_list);

        /* try get data from parent page */
        ForumPost postReceived = (ForumPost) getIntent().getSerializableExtra("post");
        if (postReceived != null) {
            post = postReceived;
        }

        /* page data */
        ForumPost.ForumCommentAdapter commentAdapter = new ForumPost.ForumCommentAdapter(this, post.getCommentList());

        /* update view */
        textViewTitle.setText(post.getTitle());
        textViewUser.setText(post.getUser());
        textViewContent.setText(post.getContent());
        listView.setAdapter(commentAdapter);

    }

}