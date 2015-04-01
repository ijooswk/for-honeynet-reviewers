package com.fan2fan.service.reward;

import com.fan2fan.model.User;

/**
 * The Rewarding service: manage rule processors
 * and calculating user points
 *
 * @author huangsz
 */
public interface RewardService {

    /**
     * try if the user can get the reward
     * @param trigger: trigger of the reward, such as SIGNUP
     * @param userId
     * @param userType
     * @return
     */
    public int tryReward(Trigger trigger, long userId, User.USER_TYPE userType);

    /**
     * jtry if the user can get this reward
     * @param trigger
     * @param userId
     * @param userType
     * @param number: the number this kind of requires, such as the page views
     * @return
     */
    public int tryReward(Trigger trigger, long userId, User.USER_TYPE userType, int number);
}
