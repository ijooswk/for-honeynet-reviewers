package com.fan2fan.service.reward.impl;

import com.fan2fan.dao.RewardDao;
import com.fan2fan.dao.UserDao;
import com.fan2fan.model.User;
import com.fan2fan.model.reward.NumberRule;
import com.fan2fan.model.reward.SimpleRule;
import com.fan2fan.service.reward.RewardService;
import com.fan2fan.service.reward.Trigger;
import com.fan2fan.service.reward.processor.NumberRuleProcessor;
import com.fan2fan.service.reward.processor.SimpleRuleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Set up a timer to load all reward rules from database at the start of the server,
 * and store them in maps with trigger as the key.
 *
 * @author huangsz
 */
@Repository
public class RewardServiceImpl implements RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

    private static final long LOAD_RULE_INTERVAL = 600000; // 1000*60*10

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private UserDao userDao;

    private Map<String, List<SimpleRuleProcessor>> simpleProcessorsMap;

    private Map<String, List<NumberRuleProcessor>> numberProcessorsMap;

    @Override
    public int tryReward(Trigger trigger, long userId, User.USER_TYPE userType) {
        int points = 0;
        List<SimpleRuleProcessor> processors = simpleProcessorsMap.get(trigger.toString());
        for (SimpleRuleProcessor processor : processors) {
            points += processor.process(userId, userType);
        }
        return points;
    }

    @Override
    public int tryReward(Trigger trigger, long userId, User.USER_TYPE userType, int number) {
        int points = 0;
        List<NumberRuleProcessor> processors = numberProcessorsMap.get(trigger.toString());
        for (NumberRuleProcessor processor : processors) {
            points += processor.process(userId, userType, number);
        }
        return points;
    }

    @Scheduled(fixedDelay = LOAD_RULE_INTERVAL)
    public void loadRules() {
        try {
            simpleProcessorsMap = loadSimpleProcessors();
            numberProcessorsMap = loadNumberProcessors();
            logger.info("Load SimpleRewardRules, number: " + simpleProcessorsMap.size());
            logger.info("Load NumberRewardRules, number: " + numberProcessorsMap.size());
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    private Map<String, List<SimpleRuleProcessor>> loadSimpleProcessors() {
        List<SimpleRule> rules = rewardDao.getAllSimpleRules();
        Map<String, List<SimpleRuleProcessor>> processorMap = new HashMap<>();
        for (SimpleRule rule : rules) {
            SimpleRuleProcessor processor = new SimpleRuleProcessor(rule, userDao, rewardDao);
            if (processorMap.containsKey(rule.getTrigger())) {
                processorMap.get(rule.getTrigger()).add(processor);
            } else {
                List<SimpleRuleProcessor> processorList = new ArrayList<>();
                processorList.add(processor);
                processorMap.put(rule.getTrigger(), processorList);
            }
        }
        return processorMap;
    }

    private Map<String, List<NumberRuleProcessor>> loadNumberProcessors() {
        Map<String, List<NumberRuleProcessor>> processorMap = new HashMap<>();
        List<NumberRule> rules = rewardDao.getAllNumberRules();
        for (NumberRule rule : rules) {
            NumberRuleProcessor processor = new NumberRuleProcessor(rule, userDao, rewardDao);
            if (processorMap.containsKey(rule.getTrigger())) {
                processorMap.get(rule.getTrigger()).add(processor);
            } else {
                List<NumberRuleProcessor> processorList = new ArrayList<>();
                processorList.add(processor);
                processorMap.put(rule.getTrigger(), processorList);
            }
        }
        return processorMap;
    }
}
