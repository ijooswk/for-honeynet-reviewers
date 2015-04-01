package com.fan2fan.service.team.impl;

import com.fan2fan.dao.UserStoryDao;
import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.StoryService;
import com.fan2fan.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author huangsz
 */
@Repository
public class StoryServiceImpl implements StoryService {

    @Autowired
    private UserStoryDao userStoryDao;

    @Override
    @Transactional
    public Result createUserStory(UserStory story) {
//        story.setViews(0);
//        story.setUps(0);
//        story.setDowns(0);
        story.setId(userStoryDao.insertUserStory(story));
        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public Result updateUserStory(UserStory story) {
        UserStory original = userStoryDao.getStoryInfo(story.getId());
        if (story.getOperatorId()==null ||
                !story.getOperatorId().equals(original.getCreatorId())) {
            return Result.PERMISSION_DENIED;
        }
        ReflectionUtils.fillPropertiesWithNull(original, story);
        if (includeContent(story)) {
            userStoryDao.updateAll(story);  // TODO: take a look at PackageDaoImpl, there's a better solution
        } else {
            userStoryDao.updateInfo(story);
        }
        return Result.SUCCESS;
    }

    @Override
    public UserStory getUserStory(long storyId) {
        return userStoryDao.getStoryById(storyId);
    }

    @Override
    public List<UserStory> getDraftStories(long userId, int offset, int size) {
        UserStory example = new UserStory();
        example.setPublished(false);
        example.setCreatorId(userId);
        return userStoryDao.getStoryInfosByExample(example, offset, size);
    }

    @Override
    public List<UserStory> getPublishedStories(long userId, int offset, int size) {
        UserStory example = new UserStory();
        example.setPublished(true);
        example.setCreatorId(userId);
        return userStoryDao.getStoryInfosByExample(example, offset, size);
    }

    @Override
    public List<UserStory> getContributedStories(long userId, int offset, int size) {
        return null;
    }

    private boolean includeContent(UserStory story) {
        return story.getContent() != null;
    }
}
