package com.fan2fan.service.reward.processor;

import com.fan2fan.dao.RewardDao;
import com.fan2fan.dao.UserDao;
import com.fan2fan.model.User;
import com.fan2fan.model.reward.NumberRule;

/**
 * @author huangsz
 */
public class NumberRuleProcessor extends RuleProcessor {

    /**
     * the number user should satisfy
     */
    protected Integer number;

    public NumberRuleProcessor(NumberRule rule, UserDao userDao, RewardDao rewardDao)  {
        super(rule, userDao, rewardDao);
        this.ruleTable = "reward_number_rule";
        this.number = rule.getNumber();
    }

    /**
     * test if the user can achieve the reward or not,
     * if yes, then update the point
     * @param userId
     * @param userType
     * @param number
     * @return the points user gets
     */
    public int process(long userId, User.USER_TYPE userType, int number) {
        if (satisfyCondition(userType, number)
                && !receivedReward(userId)) {
            updatePoints(userId);
            return points;
        }
        return 0;
    }

    private boolean satisfyCondition(User.USER_TYPE userType, int number) {
        return userTypePermit(userType) && this.number >= number;
    }
}
