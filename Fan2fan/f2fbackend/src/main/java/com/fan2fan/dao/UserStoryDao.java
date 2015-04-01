package com.fan2fan.dao;

import com.fan2fan.model.content.UserStory;

import java.util.List;

/**
 * @author huangsz
 */
public interface UserStoryDao {

    /**
     * insert a new UserStory
     * @param story
     * @return
     */
    public long insertUserStory(UserStory story);

    /**
     * get userStory's all cols by storyId
     * @param storyId
     * @return
     */
    public UserStory getStoryById(long storyId);

    /**
     * get userStory's information (content excluded) by storyId
     * @param storyId
     * @return
     */
    public UserStory getStoryInfo(long storyId);

    /**
     * update all cols of userStory
     * @param story
     */
    public void updateAll(UserStory story);

    /**
     * update info cols (namely content excluded) of userStory
     * @param story
     */
    public void updateInfo(UserStory story);

    /**
     * get story infos by example and by limit
     * @param example
     * @param offset
     * @param length
     * @return
     */
    public List<UserStory> getStoryInfosByExample(final UserStory example, final int offset, final int length);
}
