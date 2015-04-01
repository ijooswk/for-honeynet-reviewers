package com.fan2fan.service.reward.processor;

import com.fan2fan.dao.RewardDao;
import com.fan2fan.dao.UserDao;
import com.fan2fan.model.User;
import com.fan2fan.model.reward.RewardHistory;
import com.fan2fan.model.reward.SimpleRule;
import org.springframework.transaction.annotation.Transactional;

/**
 * The base class of reward policies
 * @author huangsz
 */
public abstract class RuleProcessor {

    protected String trigger;

    protected Integer userType;

    protected Integer points;

    protected String ruleTable;

    protected Long ruleId;

    protected UserDao userDao;

    protected RewardDao rewardDao;

    public RuleProcessor(SimpleRule rule, UserDao userDao, RewardDao rewardDao) {
        this.trigger = rule.getTrigger();
        this.points = rule.getPoints();
        this.userType = rule.getUserType();
        this.ruleId = rule.getId();
        this.userDao = userDao;
        this.rewardDao = rewardDao;
    }

    @Transactional
    protected void updatePoints(long userId) {
        // change points of user
        userDao.changePoints(userId, points);

        // create reward history
        RewardHistory history = new RewardHistory();
        history.setPoints(points);
        history.setRewardIdentifier(getRewardIdentifier());
        history.setUserId(userId);
        rewardDao.createRewardHistory(history);
    }

    /**
     * ruleTable#ruleId
     * @return
     */
    protected String getRewardIdentifier() {
        return String.format("%s#%d", ruleTable, ruleId);
    }

    /**
     * if the user received this reward already or not.
     * For example, if the user invites more than xx user, and he gets rewarded, than there would be a history in rewardHistory.
     * Then if he invites more people, though his invitation number exceeds and rule required number, but since he received the
     * reward already, so he cannot receive it again.
     * @param userId
     * @return
     */
    protected boolean receivedReward(long userId) {
        return rewardDao.countReceivedReward(userId,
                getRewardIdentifier()) > 0;
    }

    /**
     * the type is permitted in userType or not.
     * For example: userType = 0x110(namely 6).
     * type = CF or PF is permitted, END_USER not permitted
     * @param type
     * @return
     */
    protected boolean userTypePermit(User.USER_TYPE type) {
        return (userType & (0x1 << type.ordinal())) > 0;
    }

}
