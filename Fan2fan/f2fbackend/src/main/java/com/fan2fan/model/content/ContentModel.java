package com.fan2fan.model.content;

import com.fan2fan.model.BaseResource;
import java.sql.Timestamp;

/**
 * Content models' base class
 * @author huangsz
 */
public abstract class ContentModel extends BaseResource {

    private String title;

    private Long creatorId;

    /**
     * creator's name, just the userName of the creator when he created the story
     */
    private String creatorName;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Boolean published;

    /**
     * the original language of the text
     */
    private String language;

    /**
     * The view number of the story
     */
    private Integer views;

    /**
     * the number of ups
     */
    private Integer ups;

    /**
     * the number of downs
     */
    private Integer downs;

    /**
     * the summary of the story
     */
    private String summary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
