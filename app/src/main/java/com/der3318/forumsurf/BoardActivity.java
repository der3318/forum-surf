package com.der3318.forumsurf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
            BoardActivity.board = boardReceived;
        }

        /* data placeholder */
        List<ForumPost> postList = new ArrayList<>();
        ForumPost.ForumPostAdapter postAdapter = new ForumPost.ForumPostAdapter(this, postList);

        /* send request and process */
        ForumProcessor processor = new DcardProcessor();
        RequestQueue requestQueue = Volley.newRequestQueue(BoardActivity.this);
        requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForBoard(BoardActivity.board.getToken()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        postList.addAll(processor.convertResponseToPostList(response));
                        postAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }));

        /* update view */
        textView.setText(BoardActivity.board.getName());
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