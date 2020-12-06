package com.der3318.forumsurf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DcardProcessor implements ForumProcessor {

    @Override
    public String getUrlForBoard(String boardToken) {
        final String urlFormat = "https://www.dcard.tw/service/api/v2/forums/%s/posts?popular=false&limit=50";
        return String.format(urlFormat, boardToken);
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
                String user = jsonObject.getString("school") + " (" + jsonObject.getString("gender") + ")";
                String content = jsonObject.getString("excerpt");
                String token = jsonObject.getString("id");
                postList.add(new ForumPost(title, user, content, token));
            }
        } catch (JSONException ignored) {
        }
        return postList;
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
