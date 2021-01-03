package com.der3318.forumsurf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class ForumPost implements Serializable {

    public static class ForumPostAdapter extends ArrayAdapter<ForumPost> {

        public ForumPostAdapter(Activity context, List<ForumPost> postList) {
            super(context, 0, postList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_posts, parent, false);
            }

            ForumPost post = getItem(position);

            ((TextView) listItemView.findViewById(R.id.post_title)).setText(post.getTitle());
            ((TextView) listItemView.findViewById(R.id.post_user)).setText(post.getUser());

            return listItemView;
        }

    }

    public static class ForumCommentAdapter extends ArrayAdapter<String> {

        public ForumCommentAdapter(Activity context, List<String> commentList) {
            super(context, 0, commentList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_comments, parent, false);
            }

            String comment = getItem(position);

            ((TextView) listItemView.findViewById(R.id.comment_index)).setText(String.format(getContext().getResources().getString(R.string.comment_index_format), position + 1));
            ((TextView) listItemView.findViewById(R.id.comment_content)).setText(comment);

            return listItemView;
        }

    }

    private final String title;
    private final String user;
    private String content;
    private final String token;

    public ForumPost(String title, String user, String content, String token) {
        this.title = title;
        this.user = user;
        this.content = content;
        this.token = token;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUser() {
        return this.user;
    }

    public String getContent() {
        return this.content;
    }

    public String getToken() {
        return this.token;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
