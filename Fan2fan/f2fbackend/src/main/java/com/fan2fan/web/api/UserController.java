package com.fan2fan.web.api;

import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.team.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "/{userId}/stories", method = RequestMethod.GET, produces = "application/json")
    public List<UserStory> getStories(@PathVariable long userId, @RequestParam String filter,
                                      @RequestParam int offset, @RequestParam int length) {
        // filter is 'published' or 'draft'
        if (filter.equals("draft")) {
            return storyService.getDraftStories(userId, offset, length);
        } else if (filter.equals("published")) {
            return storyService.getPublishedStories(userId, offset, length);
        }
        return new ArrayList<>();
    }
}
