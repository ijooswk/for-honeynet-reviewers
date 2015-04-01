package com.fan2fan.dao;

import com.fan2fan.model.reward.NumberRule;
import com.fan2fan.model.reward.RewardHistory;
import com.fan2fan.model.reward.SimpleRule;

import java.util.List;

/**
 * Data Access to all kinds of reward processor tables
 *
 * @author huangsz
 */
public interface RewardDao {

    /**
     * create a rewarding history
     * @param history
     * @return the new inserted id
     */
    public long createRewardHistory(RewardHistory history);

    /**
     * count how many times does a user receive a kind of reward
     * @param userId
     * @param identifier
     * @return
     */
    public int countReceivedReward(long userId, String identifier);

    /**
     * get all simple rules from simple_rule_table
     * @return
     */
    List<SimpleRule> getAllSimpleRules();

    /**
     * get all number rules from number_rule_table
     * @return
     */
    List<NumberRule> getAllNumberRules();
}
