package com.fan2fan.model.reward;

import com.fan2fan.model.BaseModel;

import java.sql.Timestamp;

/**
 * The history of get rewarding
 * @author huangsz
 */
public class RewardHistory extends BaseModel {

    private Long id;

    /**
     * the user get rewarded
     */
    private Long userId;

    /**
     * the rewardIdentifier of the reward
     */
    private String rewardIdentifier;

    /**
     * the points the user gets
     */
    private Integer points;

    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRewardIdentifier() {
        return rewardIdentifier;
    }

    public void setRewardIdentifier(String rewardIdentifier) {
        this.rewardIdentifier = rewardIdentifier;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
