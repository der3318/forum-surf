package com.der3318.forumsurf;

import java.util.List;

public interface ForumProcessor {

    enum Type {
        DCARD,
        PTT
    }

    /**
     * step no.1
     * request with GET using this URL to get posts of a board
     **/
    public String getUrlForPostList(String boardToken, boolean loadMoreData);

    /**
     * step no.4
     * request with GET using this URL to view detailed info of a post
     **/
    public String getUrlForPost(String postToken);

    /**
     * step no.6
     * request with GET using this URL to view comments of a post
     **/
    public String getUrlForCommentList(String postToken);

    /**
     * step no.2
     * convert raw response string to managed ForumPost list
     **/
    public List<ForumPost> convertResponseToPostList(String response);

    /**
     * step no.3
     * update the token required to get more posts
     **/
    public void updateTokenUsedToLoadMoreData(String response);

    /**
     * step no.5
     * update the post with detailed info
     **/
    public void updatePostUsingResponse(ForumPost post, String response);

    /**
     * step no.7
     * convert raw response string to comment list
     **/
    public List<String> convertResponseToCommentList(String response);

}
