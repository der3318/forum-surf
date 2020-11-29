package com.der3318.forumsurf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Arrays;
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
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_simple, parent, false);
            }

            ForumPost post = getItem(position);

            ((TextView) listItemView.findViewById(R.id.text_left)).setText(post.getTitle());
            ((TextView) listItemView.findViewById(R.id.text_right)).setText(post.getUser());

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
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_simple, parent, false);
            }

            String comment = getItem(position);

            ((TextView) listItemView.findViewById(R.id.text_left)).setText(String.format("# %d", position + 1));
            ((TextView) listItemView.findViewById(R.id.text_right)).setText(comment);

            return listItemView;
        }

    }

    private final String title;
    private final String user;
    private final String content;
    private final List<String> commentList;

    public ForumPost(String title, String user, String content) {
        this.title = title;
        this.user = user;
        this.content = content;
        this.commentList = Arrays.asList("comment1", "comment2", "comment3");
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

    public List<String> getCommentList() {
        return this.commentList;
    }

}
