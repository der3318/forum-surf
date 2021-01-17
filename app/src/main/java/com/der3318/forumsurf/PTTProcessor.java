package com.der3318.forumsurf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PTTProcessor implements ForumProcessor {

    private String loadMoreDataToken = null;

    @Override
    public String getUrlForPostList(String boardToken, boolean loadMoreData) {
        String url;
        if (loadMoreData && this.loadMoreDataToken != null) {
            final String urlFormat = "https://www.ptt.cc%s";
            url = String.format(urlFormat, this.loadMoreDataToken);
        } else {
            final String urlFormat = "https://www.ptt.cc/bbs/%s/index.html";
            url = String.format(urlFormat, boardToken);
        }
        return url;
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
            String time = element.select("div.meta").select("div.date").text().trim();
            String token = element.select("div.title").select("a").attr("href");
            postList.add(new ForumPost(title, user, time ,"", token));
        }
        Collections.reverse(postList);
        return postList;
    }

    @Override
    public void updateTokenUsedToLoadMoreData(String response) {
        Document document = Jsoup.parse(response);
        this.loadMoreDataToken = document.select("a:containsOwn(‹ 上頁)").attr("href");
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
            if (element.select("span.f3.push-content").text().length() > 2) {
                commentList.add(element.select("span.f3.push-content").text().substring(2));
            }
        }
        return commentList;
    }

}
