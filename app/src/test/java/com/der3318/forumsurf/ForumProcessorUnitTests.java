package com.der3318.forumsurf;

import org.junit.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ForumProcessorUnitTests {

    @Test
    public void DcardConvertResponseToPostList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("DcardResponseForPostList.json")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new DCARDProcessor();
        List<ForumPost> postList = processor.convertResponseToPostList(response);

        /* verify */
        assertEquals(2, postList.size());
        assertEquals("Title1", postList.get(0).getTitle());
        assertEquals("Title2", postList.get(1).getTitle());
        assertEquals("User1 (F)", postList.get(0).getUser());
        assertEquals("User2 (M)", postList.get(1).getUser());
        assertEquals("2021-01-24", postList.get(0).getTime());
        assertEquals("2021-01-24", postList.get(1).getTime());
        assertEquals("235227512", postList.get(0).getToken());
        assertEquals("235227421", postList.get(1).getToken());

    }

    @Test
    public void DcardGetUrlForPostList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("DcardResponseForPostList.json")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new DCARDProcessor();
        processor.updateTokenUsedToLoadMoreData(response);

        /* verify */
        assertNotEquals(processor.getUrlForPostList("token", false), processor.getUrlForPostList("token", true));
        assertTrue(processor.getUrlForPostList("token", true).contains("235227421"));

    }

    @Test
    public void DcardUpdatePostUsingResponse() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("DcardResponseForPost.json")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new DCARDProcessor();
        ForumPost post = new ForumPost("Title", "User", "2021-01-24", "", "235227512");
        processor.updatePostUsingResponse(post, response);

        /* verify */
        assertEquals("Title", post.getTitle());
        assertEquals("User", post.getUser());
        assertEquals("2021-01-24", post.getTime());
        assertEquals("Content", post.getContent());
        assertEquals("235227512", post.getToken());

    }

    @Test
    public void DcardConvertResponseToCommentList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("DcardResponseForCommentList.json")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new DCARDProcessor();
        List<String> commentList = processor.convertResponseToCommentList(response);

        /* verify */
        assertEquals(3, commentList.size());
        assertEquals("Comment1", commentList.get(0));
        assertEquals("Comment2", commentList.get(1));
        assertEquals("Comment3", commentList.get(2));

    }

    @Test
    public void PttConvertResponseToPostList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("PttResponseForPostList.html")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new PTTProcessor();
        List<ForumPost> postList = processor.convertResponseToPostList(response);

        /* verify */
        assertEquals(2, postList.size());
        assertEquals("Title1", postList.get(0).getTitle());
        assertEquals("Title2", postList.get(1).getTitle());
        assertEquals("User1", postList.get(0).getUser());
        assertEquals("User2", postList.get(1).getUser());
        assertEquals("1/20", postList.get(0).getTime());
        assertEquals("1/20", postList.get(1).getTime());
        assertEquals("/bbs/Soft_Job/M.1611138720.A.1BC.html", postList.get(0).getToken());
        assertEquals("/bbs/Soft_Job/M.1611137047.A.387.html", postList.get(1).getToken());

    }

    @Test
    public void PttGetUrlForPostList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("PttResponseForPostList.html")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new PTTProcessor();
        processor.updateTokenUsedToLoadMoreData(response);

        /* verify */
        assertNotEquals(processor.getUrlForPostList("token", false), processor.getUrlForPostList("token", true));
        assertFalse(processor.getUrlForPostList("token", true).contains("index.html"));

    }

    @Test
    public void PttUpdatePostUsingResponse() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("PttResponseForPostAndCommentList.html")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new PTTProcessor();
        ForumPost post = new ForumPost("Title", "User", "1/20", "", "/bbs/Soft_Job/M.1611138720.A.1BC.html");
        processor.updatePostUsingResponse(post, response);

        /* verify */
        assertEquals("Title", post.getTitle());
        assertEquals("User", post.getUser());
        assertEquals("1/20", post.getTime());
        assertTrue(post.getContent().contains("Content"));
        assertEquals("/bbs/Soft_Job/M.1611138720.A.1BC.html", post.getToken());

    }

    @Test
    public void PttConvertResponseToCommentList() {

        /* read sample response */
        Scanner scanner = new Scanner(getClass().getResourceAsStream("PttResponseForPostAndCommentList.html")).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        /* try to process */
        ForumProcessor processor = new PTTProcessor();
        List<String> commentList = processor.convertResponseToCommentList(response);

        /* verify */
        assertEquals(1, commentList.size());
        assertEquals("Comment", commentList.get(0));

    }

}