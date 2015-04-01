package com.fan2fan.service.team;

import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.Result;

import java.util.List;

/**
 * @author huangsz
 */
public interface StoryService {

    /**
     * create user story
     * @param story
     * @return
     */
    public Result createUserStory(UserStory story);

    /**
     * update story, only update the attrs which are not null
     * @param story
     * @return
     */
    public Result updateUserStory(UserStory story);

    /**
     * get user story by id
     * @param storyId
     * @return
     */
    public UserStory getUserStory(long storyId);

    /**
     * get the drafts of a user by page
     * @param userId
     * @return
     */
    public List<UserStory> getDraftStories(long userId, int offset, int size);

    /**
     * get user's published stories by page.
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    public List<UserStory> getPublishedStories(long userId, int offset, int size);

    /**
     * get user's contributed stories by page, no matter approved or not.
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    public List<UserStory> getContributedStories(long userId, int offset, int size);
}
