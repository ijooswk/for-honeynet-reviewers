package com.fan2fan.dao.impl;

import com.fan2fan.dao.RewardDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.reward.NumberRule;
import com.fan2fan.model.reward.RewardHistory;
import com.fan2fan.model.reward.SimpleRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangsz
 *
 */
@Repository
public class RewardDaoImpl implements RewardDao {

    private final String SIMPLE_RULE_TABLE = "reward_simple_rule";

    private final String NUMBER_RULE_TABLE = "reward_number_rule";

    private final String REWARD_HISTORY = "reward_history";

    @Autowired
    private ConnManager cm;

    @Autowired
    private RowMapperFactory factory;

    @Override
    public long createRewardHistory(RewardHistory history) {
        String[] attrs = new String[] {"userId", "rewardIdentifier", "points"};
        return cm.insertReturnId(history, attrs, REWARD_HISTORY);
    }

    @Override
    public int countReceivedReward(long userId, String identifier) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select count(*) from %s where userId = ? and rewardIdentifier = ?", REWARD_HISTORY),
                new Object[]{userId, identifier},
                Integer.class
        );
    }

    @Override
    public List<SimpleRule> getAllSimpleRules() {
        return cm.getJdbcTemplate().query(
                String.format("select * from %s", SIMPLE_RULE_TABLE),
                factory.getSimpleRuleRowMapper()
        );
    }

    @Override
    public List<NumberRule> getAllNumberRules() {
        return cm.getJdbcTemplate().query(
                String.format("select * from %s", NUMBER_RULE_TABLE),
                factory.getNumberRuleRowMapper()
        );
    }
}
