package com.fan2fan.dao.impl.mapper;

import com.fan2fan.model.*;
import com.fan2fan.model.content.PackageTrip;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.model.content.UserStory;
import com.fan2fan.model.reward.NumberRule;
import com.fan2fan.model.reward.SimpleRule;
import com.fan2fan.util.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Fast construction for some simple RowMappers
 * Created by huangsz on 2014/5/19.
 */
@Repository
public class RowMapperFactory {

    public static final Logger logger = LoggerFactory.getLogger(RowMapperFactory.class);

    private RowMapper<UserToken> userTokenRowMapper = (rs, rowNum) -> new UserToken(
            rs.getLong("id"), rs.getLong("userId"), rs.getString("token"),
            rs.getTimestamp("expires"), rs.getBoolean("used"));

    private RowMapper<League> leagueRowMapper = (rs, rowNum) -> {
        League league = new League();
        league.setId(rs.getLong("id"));
        league.setName(rs.getString("name"));
        league.setDesc(rs.getString("desc"));
        league.setCreateTime(rs.getTimestamp("createTime"));
        league.setCreatorId(rs.getLong("creatorId"));
        league.setLogoUrl(rs.getString("logoUrl"));
        return league;
    };

    private RowMapper<Location> locationRowMapper = (rs, rowNum) -> {
        Location location = new Location();
        location.setName(rs.getString("name"));
        return location;
    };

    private RowMapper<Team> teamRowMapper = createTeamMapper();

    private RowMapper<Player> playerRowMapper = createPlayerMapper();

    private RowMapper<InviteCode> inviteCodeRowMapper = createInviteCodeRowMapper();

    private RowMapper<UserStory> storyInfoMapper = createUserStoryInfoRowMapper();

    private RowMapper<UserStory> fullStoryMapper = createFullUserStoryRowMapper();

    private RowMapper<UserPackage> packageRowMapper = createPackageRowMapper();

    private RowMapper<SimpleRule> simpleRuleMapper = createSimpleRuleMapper();

    private RowMapper<NumberRule> numberRuleMapper = createNumberRuleMapper();

    private RowMapper<UserRecord> userRecordMapper = createUserRecordMapper();

    private RowMapper<PackageTrip> tripRowMapper = createTripMapper();

    public RowMapper<UserToken> getUserTokenMapper() {
        return this.userTokenRowMapper;
    }

    public RowMapper<League> getLeagueRowMapper() {
        return leagueRowMapper;
    }

    public RowMapper<Location> getLocationRowMapper() { return this.locationRowMapper; }

    /**
     * get RowMapper for Team
     *
     * @return
     */
    public RowMapper<Team> getTeamRowMapper() {
        return this.teamRowMapper;
    }

    /**
     * get RowMapper for Player
     *
     * @return
     */
    public RowMapper<Player> getPlayerRowMapper() {
        return playerRowMapper;
    }

    /**
     * get RowMapper for InviteCode
     *
     * @return
     */
    public RowMapper<InviteCode> getInviteCodeRowMapper() {
        return inviteCodeRowMapper;
    }

    /**
     * get the userStory's info (namely excluding content)'s row mapper
     * @return
     */
    public RowMapper<UserStory> getStoryInfoRowMapper() {
        return storyInfoMapper;
    }

    /**
     * get all columns of userStory's row mapper
     * @return
     */
    public RowMapper<UserStory> getFullStoryRowMapper() {
        return fullStoryMapper;
    }

    /**
     * get package without content
     * @return
     */
    public RowMapper<UserPackage> getPackageRowMapper() { return this.packageRowMapper; }

    /**
     * get all columns of Reward Simple Rule
     * @return
     */
    public RowMapper<SimpleRule> getSimpleRuleRowMapper() {
        return simpleRuleMapper;
    }

    /**
     * get all columns of Number Reward Rule
     * @return
     */
    public RowMapper<NumberRule> getNumberRuleRowMapper() {
        return numberRuleMapper;
    }

    public RowMapper<PackageTrip> getPackageTripRowMapper() { return tripRowMapper; }

    public RowMapper<UserRecord> getUserRecordRowMapper() { return userRecordMapper; }

    private RowMapper<UserRecord> createUserRecordMapper() {
        String[] attrs = new String[] {"id", "stories", "packs", "views",
                "invites", "contribStories", "sellPacks", "buyPacks", "reviews"};
        return createRowMapper(attrs, UserRecord.class);
    }

    private RowMapper<Player> createPlayerMapper() {
        String[] attrs = new String[]{"id", "name", "desc", "country",
                "number", "position", "imageUrl", "teamId", "creatorId"};
        return createRowMapper(attrs, Player.class);
    }

    private RowMapper<InviteCode> createInviteCodeRowMapper() {
        String[] attrs = new String[]{"id", "type", "code", "email", "invitorId"};
        return createRowMapper(attrs, InviteCode.class);
    }

    private RowMapper<UserStory> createUserStoryInfoRowMapper() {
        String[] attrs = new String[]{"id", "title", "creatorId", "creatorName", "createTime",
                "summary", "type", "language", "published", "views", "ups", "downs"};
        return createRowMapper(attrs, UserStory.class);
    }

    private RowMapper<UserStory> createFullUserStoryRowMapper() {
        String[] attrs = new String[]{"id", "title", "creatorId", "creatorName", "createTime",
                "summary", "type", "language", "published", "views", "ups", "downs", "content"};
        return createRowMapper(attrs, UserStory.class);
    }

    private RowMapper<UserPackage> createPackageRowMapper() {
        String[] attrs = new String[]{"id", "title", "creatorId", "creatorName", "createTime",
                "summary", "language", "published", "views", "ups", "downs", "startTime", "endTime",
                "currencyId", "price", "minPeople", "maxPeople", "desc", "items", "youkuVideoId"};
        return createRowMapper(attrs, UserPackage.class);
    }

    private RowMapper<Team> createTeamMapper() {
        String[] attrs = new String[]{"id", "name", "desc", "city",
                "country", "creatorId", "createTime", "stadium", "type", "leagueId",
                "logoUrl"};
        return createRowMapper(attrs, Team.class);
    }

    private RowMapper<SimpleRule> createSimpleRuleMapper() {
        String[] attrs = new String[] {"id", "trigger", "points", "userType"};
        return createRowMapper(attrs, SimpleRule.class);
    }

    private RowMapper<NumberRule> createNumberRuleMapper() {
        String[] attrs = new String[] {"id", "trigger", "points",
                "userType", "number"};
        return createRowMapper(attrs, NumberRule.class);
    }

    private RowMapper<PackageTrip> createTripMapper() {
        String[] attrs = new String[] {"id", "place", "summary", "desc",
                "imageUrl", "packageId"};
        return createRowMapper(attrs, PackageTrip.class);
    }

    /**
     * create a rowMapper using reflection, it will save a lot of time
     *
     * @param attrs: the attrs you're going to retrieve from query result
     * @param objClazz: the return type model's clazz
     * @param <T>: the type of the obj
     * @return
     */
    private <T extends BaseModel> RowMapper<T> createRowMapper(String[] attrs, Class<T> objClazz) {
        return (rs, rowNum) -> {
            T obj;
            try {
                obj = objClazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("newInstance failed on createRowMapper", e);
            }
            final BeanWrapper wrapper = new BeanWrapperImpl(obj);
            for (String attr : attrs) {
                Class<?> clazz = wrapper.getPropertyType(attr);
                if (clazz == Long.class) {
                    wrapper.setPropertyValue(attr, rs.getLong(attr));
                } else if (clazz.isEnum()) {
                    wrapper.setPropertyValue(attr, EnumUtils.getEnumByIndex(rs.getInt(attr), clazz));
                } else if (clazz == Integer.class) {
                    wrapper.setPropertyValue(attr, rs.getInt(attr));
                } else if (clazz == Timestamp.class) {
                    wrapper.setPropertyValue(attr, rs.getTimestamp(attr));
                } else if (clazz == Date.class) {
                    wrapper.setPropertyValue(attr, rs.getDate(attr));
                } else if (clazz == String.class) {
                    wrapper.setPropertyValue(attr, rs.getString(attr));
                } else if (clazz == Boolean.class) {
                    wrapper.setPropertyValue(attr, rs.getBoolean(attr));
                } else {
                    throw new RuntimeException("Unexpected type to create rowMapper for attr: "
                            + attr + " type: " + clazz);
                }
            }
            return obj;
        };
    }
}
