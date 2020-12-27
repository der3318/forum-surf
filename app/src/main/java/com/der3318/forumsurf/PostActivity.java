package com.der3318.forumsurf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.der3318.widget.ListViewWithoutScroll;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    public static ForumPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        TextView textViewTitle = (TextView) findViewById(R.id.post_title);
        TextView textViewUser = (TextView) findViewById(R.id.post_user);
        TextView textViewContent = (TextView) findViewById(R.id.post_content);
        ListViewWithoutScroll listView = (ListViewWithoutScroll) findViewById(R.id.comment_list);

        /* try get data from parent page */
        ForumPost postReceived = (ForumPost) getIntent().getSerializableExtra("post");
        if (postReceived != null) {
            PostActivity.post = postReceived;
        }

        /* data placeholder */
        List<String> commentList = new ArrayList<>();
        ForumPost.ForumCommentAdapter commentAdapter = new ForumPost.ForumCommentAdapter(this, commentList);

        /* send request and process */
        try {
            Class<?> processorClass = Class.forName(String.format("com.der3318.forumsurf.%sProcessor", BoardActivity.board.getType().name()));
            ForumProcessor processor = (ForumProcessor) processorClass.newInstance();
            RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
            requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForPost(PostActivity.post.getToken()),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            processor.updatePostUsingResponse(PostActivity.post, response);
                            textViewContent.setText(PostActivity.post.getContent());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }));
            requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForCommentList(PostActivity.post.getToken()),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            commentList.addAll(processor.convertResponseToCommentList(response));
                            commentAdapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }));
        } catch (ClassNotFoundException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (InstantiationException ignored) {
        }

        /* update view */
        textViewTitle.setText(PostActivity.post.getTitle());
        textViewUser.setText(PostActivity.post.getUser());
        textViewContent.setText(PostActivity.post.getContent());
        listView.setAdapter(commentAdapter);

    }

}