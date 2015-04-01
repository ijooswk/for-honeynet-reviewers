package com.fan2fan.service.reward.processor;

import com.fan2fan.dao.RewardDao;
import com.fan2fan.dao.UserDao;
import com.fan2fan.model.User;
import com.fan2fan.model.reward.SimpleRule;

/**
 * @author huangsz
 */
public class SimpleRuleProcessor extends RuleProcessor {

    public SimpleRuleProcessor(SimpleRule rule, UserDao userDao, RewardDao rewardDao)  {
        super(rule, userDao, rewardDao);
        this.ruleTable = "reward_simple_rule";
    }

    /**
     * check if satisfy the condition to achieve the reward/
     * if yes, then add the points to the user
     * @param userId
     * @param userType
     * @return the points user gets
     */
    public int process(long userId, User.USER_TYPE userType) {
        if (satisfyCondition(userType)) {
            updatePoints(userId);
            return points;
        }
        return 0;
    }

    /**
     * simple rules are always satisfied
     * @return
     */
    private boolean satisfyCondition(User.USER_TYPE userType) {
        return userTypePermit(userType);
    }
}
