package com.fan2fan.web.api;

import com.fan2fan.model.User;
import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.StoryService;
import com.fan2fan.util.Helper;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * {@link com.fan2fan.web.api.StoryController}
 *
 * @author huangsz
 */
public class StoryControllerTest extends BaseResourceControllerTest {

    @Autowired
    private StoryService storyService;

    private UserStory story;

    private User user;

    @Before
    public void setUp() {
        super.setUp();
        user = createCommonUser();

        story = new UserStory();
        story.setTitle("The beautiful night");
        story.setContent("Raz and Fang together");
        story.setLanguage("English");
        story.setSummary("summary");
        story.setPublished(false);
        story.setCreatorId(user.getId());
        story.setCreatorName(user.getUserName());
        story.setType(UserStory.STORY_TYPE.TEAM);
    }

    @Test
    @Rollback(true)
    public void testCreateUserStory() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/story");
        Helper.setRequestAllJsonType(request);

        request.content(Helper.jsonMapper.writeValueAsString(story));
        basicTest(request, Result.SUCCESS);
    }

    @Test
    @Rollback(true)
    public void testGetAndUpdateUserStory() throws Exception {
        storyService.createUserStory(story);
        MockHttpServletRequestBuilder request = get(String.format(
                "/api/story/%d", story.getId()
        )).accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        // test get
        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.title").value("The beautiful night"))
                .andExpect(jsonPath("$.content").value("Raz and Fang together"))
                .andExpect(jsonPath("$.creatorName").value(user.getUserName()))
                .andExpect(jsonPath("$.language").value("English"))
                .andExpect(jsonPath("$.published").value(false))
                .andExpect(jsonPath("$.type").value("TEAM"))
                .andExpect(jsonPath("$.creatorId").value(user.getId().intValue()))
                .andExpect(jsonPath("$.summary").value("summary"))
                .andExpect(jsonPath("$.views").value(0));

        request = post(String.format("/api/story/%d", story.getId()));
        Helper.setRequestAllJsonType(request);

        // test update story info
        story.setViews(1);
        story.setTitle("High");
        story.setOperatorId(user.getId() + 1);
        request.content(Helper.jsonMapper.writeValueAsString(story));
        basicTest(request, Result.PERMISSION_DENIED);  // modifier should be the author itself

        story.setOperatorId(user.getId());
        request.content(Helper.jsonMapper.writeValueAsString(story));
        basicTest(request, Result.SUCCESS);

        // test update all story cols
        story.setContent("Beyond description");
        request.content(Helper.jsonMapper.writeValueAsString(story));
        basicTest(request, Result.SUCCESS);

        // get story to test the update result
        request = get(String.format(
                "/api/story/%d", story.getId()
        )).accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.title").value("High"))
                .andExpect(jsonPath("$.content").value("Beyond description"))
                .andExpect(jsonPath("$.creatorName").value(user.getUserName()))
                .andExpect(jsonPath("$.language").value("English"))
                .andExpect(jsonPath("$.published").value(false))
                .andExpect(jsonPath("$.type").value("TEAM"))
                .andExpect(jsonPath("$.creatorId").value(user.getId().intValue()))
                .andExpect(jsonPath("$.summary").value("summary"))
                .andExpect(jsonPath("$.views").value(1));
    }


}
