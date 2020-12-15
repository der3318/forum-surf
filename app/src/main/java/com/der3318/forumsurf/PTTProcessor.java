package com.der3318.forumsurf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PTTProcessor implements ForumProcessor {

    @Override
    public String getUrlForBoard(String boardToken) {
        final String urlFormat = "https://www.ptt.cc/bbs/%s/index.html";
        return String.format(urlFormat, boardToken);
    }

    @Override
    public String getUrlForPost(String postToken) {
        final String urlFormat = "https://www.ptt.cc%s";
        return String.format(urlFormat, postToken);
    }

    @Override
    public String getUrlForCommentList(String postToken) {
        final String urlFormat = "https://www.ptt.cc%s";
        return String.format(urlFormat, postToken);
    }

    @Override
    public List<ForumPost> convertResponseToPostList(String response) {
        List<ForumPost> postList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        for (Element element : document.select("div.r-ent")) {
            if (element.select("div.title").select("a").isEmpty()) {
                continue;
            }
            String title = element.select("div.title").select("a").text();
            String user = element.select("div.meta").select("div.author").text();
            String token = element.select("div.title").select("a").attr("href");
            postList.add(new ForumPost(title, user, "", token));
        }
        return postList;
    }

    @Override
    public void updatePostUsingResponse(ForumPost post, String response) {
        Document document = Jsoup.parse(response);
        Element content = document.select("div.bbs-screen.bbs-content").first();
        content.select("div").remove();
        post.setContent(content.wholeText());
    }

    @Override
    public List<String> convertResponseToCommentList(String response) {
        List<String> commentList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element content = document.select("div.bbs-screen.bbs-content").first();
        for (Element element : document.select("div.push")) {
            commentList.add(element.select("span.f3.push-content").text().substring(2));
        }
        return commentList;
    }

}
