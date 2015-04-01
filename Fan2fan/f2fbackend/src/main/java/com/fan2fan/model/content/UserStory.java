package com.fan2fan.model.content;

/**
 * The story shared by user.
 *
 *  Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 *
 * @author huangsz
 */
public class UserStory extends ContentModel {

    public enum STORY_TYPE {TEAM, PACKAGE}

    /**
     * 0 for team story, 1 for package story
     */
    private Integer type;

    /**
     * the html content
     */
    private String content;

    public void setType(STORY_TYPE type) {
        this.type = type == null ? null : type.ordinal();
    }

    public STORY_TYPE getType() {
        return type == null ? null : STORY_TYPE.values()[type];
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
