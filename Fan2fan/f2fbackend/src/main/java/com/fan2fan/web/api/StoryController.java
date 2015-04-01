package com.fan2fan.web.api;

import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.team.StoryService;
import com.fan2fan.web.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for UserStory
 *
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap createUserStory(@RequestBody UserStory story) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, storyService.createUserStory(story).toString());
        map.put(Message.KEY_OBJECT, story);
        return map;
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap updateUserStory(@RequestBody UserStory story, @PathVariable long storyId) {
        ModelMap map = new ModelMap();
        story.setId(storyId);
        // the service will judge update views, ups or the content,
        // and call different DAO methods
        map.put(Message.KEY_RESULT, storyService.updateUserStory(story).toString());
        return map;
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.GET, produces = "application/json")
    public UserStory getUserStory(@PathVariable long storyId) {
        return storyService.getUserStory(storyId);
    }

}
