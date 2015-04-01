package com.fan2fan.model;

/**
 * Not suppose to be viewed by others easily
 * @author huangsz
 */
public class UserRecord extends BaseModel {

    private Long id;

    /**
     * the story number
     */
    private Integer stories;

    /**
     * the package number
     */
    private Integer packs;

    /**
     * the personal page view number
     */
    private Integer views;

    /**
     * the user number invited by this user
     */
    private Integer invites;

    /**
     * the stories contributed to teams
     */
    private Integer contribStories;

    /**
     * package number selled
     */
    private Integer sellPacks;

    /**
     * package number buyed
     */
    private Integer buyPacks;

    /**
     * the review number received
     */
    private Integer reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStories() {
        return stories;
    }

    public void setStories(Integer stories) {
        this.stories = stories;
    }

    public Integer getPacks() {
        return packs;
    }

    public void setPacks(Integer packs) {
        this.packs = packs;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getInvites() {
        return invites;
    }

    public void setInvites(Integer invites) {
        this.invites = invites;
    }

    public Integer getContribStories() {
        return contribStories;
    }

    public void setContribStories(Integer contribStories) {
        this.contribStories = contribStories;
    }

    public Integer getSellPacks() {
        return sellPacks;
    }

    public void setSellPacks(Integer sellPacks) {
        this.sellPacks = sellPacks;
    }

    public Integer getBuyPacks() {
        return buyPacks;
    }

    public void setBuyPacks(Integer buyPacks) {
        this.buyPacks = buyPacks;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }
}
