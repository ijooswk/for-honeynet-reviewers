package com.fan2fan.dao.impl;

import com.fan2fan.dao.UserStoryDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.content.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huangsz
 */
@Repository
public class UserStoryDaoImpl implements UserStoryDao {

    private static final String TABLE = "userStory";

    @Autowired
    private RowMapperFactory factory;

    @Autowired
    private ConnManager cm;

    private final String[] inserted = new String[] {"title", "content", "creatorId", "summary",
            "creatorName", "type", "language", "published", "views", "ups", "downs"};

    private String[] infoUpdated;

    private String[] allUpdated;

    public UserStoryDaoImpl() {
        final List<String> infoList = new ArrayList<>(Arrays.asList("title", "language", "published",
                "summary", "views", "ups", "downs"));
        final List<String> allList;
        allList = new ArrayList<>(infoList);
        allList.add("content");

        infoUpdated = new String[infoList.size()];
        infoList.toArray(infoUpdated);
        allUpdated = new String[allList.size()];
        allList.toArray(allUpdated);
    }

    @Override
    public long insertUserStory(UserStory story) {
        return cm.insertReturnId(story, inserted, TABLE);
    }

    @Override
    public UserStory getStoryById(long storyId) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select * from %s where id = ?", TABLE),
                new Object[]{storyId}, factory.getFullStoryRowMapper()
        );
    }

    @Override
    public UserStory getStoryInfo(long storyId) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select * from %s where id = ?", TABLE),
                new Object[]{storyId}, factory.getStoryInfoRowMapper()
        );
    }

    @Override
    public void updateAll(UserStory story) {
        final String sql = cm.createUpdateSql(allUpdated, TABLE, story.getId());
        cm.getJdbcTemplate().update(sql, story.getTitle(), story.getLanguage(),
                story.getPublished(), story.getSummary(),
                story.getViews(), story.getUps(), story.getDowns(),
                story.getContent());
    }

    @Override
    public void updateInfo(UserStory story) {
        final String sql = cm.createUpdateSql(infoUpdated, TABLE, story.getId());
        cm.getJdbcTemplate().update(sql, story.getTitle(), story.getLanguage(),
                story.getPublished(), story.getSummary(),
                story.getViews(), story.getUps(), story.getDowns());
    }

    @Override
    public List<UserStory> getStoryInfosByExample(UserStory example, int offset, int length) {
        return cm.selectByExample(TABLE, example, true, factory.getStoryInfoRowMapper(), offset, length);
    }
}
