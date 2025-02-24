package com.der3318.forumsurf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class BoardActivity extends AppCompatActivity {

    public static ForumBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        TextView textView = (TextView) findViewById(R.id.board_name);
        Button button = (Button) findViewById(R.id.load_more);
        ListViewWithoutScroll listView = (ListViewWithoutScroll) findViewById(R.id.post_list);

        /* try get data from parent page */
        ForumBoard boardReceived = (ForumBoard) getIntent().getSerializableExtra("board");
        if (boardReceived != null) {
            BoardActivity.board = boardReceived;
        }

        /* data placeholder */
        List<ForumPost> postList = new ArrayList<>();
        ForumPost.ForumPostAdapter postAdapter = new ForumPost.ForumPostAdapter(this, postList);

        /* send request and process */
        try {
            Class<?> processorClass = Class.forName(String.format("com.der3318.forumsurf.%sProcessor", BoardActivity.board.getType().name()));
            ForumProcessor processor = (ForumProcessor) processorClass.newInstance();
            RequestQueue requestQueue = Volley.newRequestQueue(BoardActivity.this);
            requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForPostList(BoardActivity.board.getToken(), false),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            postList.addAll(processor.convertResponseToPostList(response));
                            postAdapter.notifyDataSetChanged();
                            processor.updateTokenUsedToLoadMoreData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getText(R.string.error_connection_failure), Toast.LENGTH_SHORT).show();
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        }
                    }));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setClickable(false);
                    requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForPostList(BoardActivity.board.getToken(), true),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    button.setClickable(true);
                                    postList.addAll(processor.convertResponseToPostList(response));
                                    postAdapter.notifyDataSetChanged();
                                    processor.updateTokenUsedToLoadMoreData(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getText(R.string.error_connection_failure), Toast.LENGTH_SHORT).show();
                                    button.setClickable(true);
                                }
                            }));
                }
            });
        } catch (ClassNotFoundException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (InstantiationException ignored) {
        }

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