package com.der3318.forumsurf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class ForumBoard implements Serializable {

    public static class ForumBoardAdapter extends ArrayAdapter<ForumBoard> {

        public ForumBoardAdapter(Activity context, List<ForumBoard> boardList) {
            super(context, 0, boardList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_simple, parent, false);
            }

            ForumBoard board = getItem(position);

            ((TextView) listItemView.findViewById(R.id.text_left)).setText(board.getName());
            ((TextView) listItemView.findViewById(R.id.text_right)).setText(board.getType().name());

            return listItemView;
        }

    }

    private final String name;
    private final ForumProcessor.Type type;
    private final String token;

    public ForumBoard(String name, ForumProcessor.Type type, String token) {
        this.name = name;
        this.type = type;
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public ForumProcessor.Type getType() {
        return this.type;
    }

    public String getToken() {
        return this.token;
    }

}
