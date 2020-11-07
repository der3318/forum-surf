package com.der3318.forumsurf;

public class ForumBoard {

    private String name;
    private String type;

    public ForumBoard(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

}
