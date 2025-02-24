package com.der3318.forumsurf;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostActivity extends AppCompatActivity {

    public static ForumPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* link resource */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ScrollView scrollView = (ScrollView) findViewById((R.id.post_scrollview));
        TextView textViewTitle = (TextView) findViewById(R.id.post_title);
        TextView textViewUser = (TextView) findViewById(R.id.post_user);
        TextView textViewContent = (TextView) findViewById(R.id.post_content);
        ListViewWithoutScroll listViewImages = (ListViewWithoutScroll) findViewById(R.id.image_list);
        ListViewWithoutScroll listViewComments = (ListViewWithoutScroll) findViewById(R.id.comment_list);

        /* try get data from parent page */
        ForumPost postReceived = (ForumPost) getIntent().getSerializableExtra("post");
        if (postReceived != null) {
            PostActivity.post = postReceived;
        }

        /* data placeholder */
        List<String> urlList = new ArrayList<>();
        ForumPost.ForumImageAdapter imageAdapter = new ForumPost.ForumImageAdapter(this, urlList);
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
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            processor.updatePostUsingResponse(PostActivity.post, response);
                            textViewContent.setText(PostActivity.post.getContent());
                            urlList.addAll(PostActivity.this.extractAllImageLinks(PostActivity.post.getContent(), getApplicationContext().getResources().getString(R.string.image_links_regex)));
                            imageAdapter.notifyDataSetChanged();
                            scrollView.fullScroll(View.FOCUS_UP);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getText(R.string.error_connection_failure), Toast.LENGTH_SHORT).show();
                        }
                    }));
            requestQueue.add(new StringRequest(Request.Method.GET, processor.getUrlForCommentList(PostActivity.post.getToken()),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            commentList.addAll(processor.convertResponseToCommentList(response));
                            commentAdapter.notifyDataSetChanged();
                            scrollView.fullScroll(View.FOCUS_UP);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getText(R.string.error_connection_failure), Toast.LENGTH_SHORT).show();
                        }
                    }));
        } catch (ClassNotFoundException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (InstantiationException ignored) {
        }

        /* update view */
        textViewTitle.setText(PostActivity.post.getTitle());
        textViewUser.setText(PostActivity.post.getUser());
        listViewImages.setAdapter(imageAdapter);
        listViewComments.setAdapter(commentAdapter);

    }

    private List<String> extractAllImageLinks(String content, String regex) {
        List<String> imageList = new ArrayList<>();
        final Matcher matcher = Pattern.compile(regex).matcher(content);
        while (matcher.find()) {
            imageList.add(matcher.group(0));
        }
        return imageList;
    }

}