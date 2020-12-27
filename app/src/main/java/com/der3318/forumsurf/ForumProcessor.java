package com.der3318.forumsurf;

import java.util.List;

public interface ForumProcessor {

    enum Type {
        DCARD,
        PTT
    }

    public String getUrlForPostList(String boardToken, boolean loadMoreData);

    public String getUrlForPost(String postToken);

    public String getUrlForCommentList(String postToken);

    public List<ForumPost> convertResponseToPostList(String response);

    public void updateTokenUsedToLoadMoreData(String response);

    public void updatePostUsingResponse(ForumPost post, String response);

    public List<String> convertResponseToCommentList(String response);

}
