package com.der3318.forumsurf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DCARDProcessor implements ForumProcessor {

    private String loadMoreDataToken = null;

    @Override
    public String getUrlForPostList(String boardToken, boolean loadMoreData) {
        String url;
        if (loadMoreData && this.loadMoreDataToken != null) {
            final String urlFormat = "https://www.dcard.tw/service/api/v2/forums/%s/posts?popular=false&limit=20&before=%s";
            url = String.format(urlFormat, boardToken, this.loadMoreDataToken);
        } else {
            final String urlFormat = "https://www.dcard.tw/service/api/v2/forums/%s/posts?popular=false&limit=20";
            url = String.format(urlFormat, boardToken);
        }
        return url;
    }

    @Override
    public String getUrlForPost(String postToken) {
        final String urlFormat = "https://www.dcard.tw/service/api/v2/posts/%s";
        return String.format(urlFormat, postToken);
    }

    @Override
    public String getUrlForCommentList(String postToken) {
        final String urlFormat = "https://www.dcard.tw/service/api/v2/posts/%s/comments";
        return String.format(urlFormat, postToken);
    }

    @Override
    public List<ForumPost> convertResponseToPostList(String response) {
        List<ForumPost> postList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                String title = jsonObject.getString("title");
                String user = (jsonObject.isNull("school") ? "匿名" : jsonObject.getString("school")) + " (" + jsonObject.getString("gender") + ")";
                String time = jsonObject.getString("createdAt").substring(0, 10);
                String content = jsonObject.getString("excerpt");
                String token = Long.toString(jsonObject.getLong("id"));
                postList.add(new ForumPost(title, user, time, content, token));
            }
        } catch (JSONException ignored) {
        }
        return postList;
    }

    @Override
    public void updateTokenUsedToLoadMoreData(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                this.loadMoreDataToken = Long.toString(jsonObject.getLong("id"));
            }
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void updatePostUsingResponse(ForumPost post, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            post.setContent(jsonObject.getString("content"));
        } catch (JSONException ignored) {
        }
    }

    @Override
    public List<String> convertResponseToCommentList(String response) {
        List<String> commentList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                commentList.add(jsonObject.getString("content"));
            }
        } catch (JSONException ignored) {
        }
        return commentList;
    }

}
