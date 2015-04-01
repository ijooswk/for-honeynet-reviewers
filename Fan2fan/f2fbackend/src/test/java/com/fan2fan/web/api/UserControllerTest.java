package com.fan2fan.web.api;

import com.fan2fan.model.User;
import com.fan2fan.model.content.UserStory;
import com.fan2fan.service.team.StoryService;
import com.fan2fan.util.Helper;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * {@link com.fan2fan.web.api.UserController}
 * 
 * @author huangsz 
 */
public class UserControllerTest extends BaseResourceControllerTest {

    @Autowired
    private StoryService storyService;
    
    private User user;
    
    @Before
    public void setUp() {
        super.setUp();
        user = createCommonUser();
    }
    
    @Test
    @Rollback(true)
    public void testGetStories() throws Exception {
        for (int i = 0; i < 5; i++) {
            UserStory story1 = new UserStory();
            story1.setTitle("The beautiful night");
            story1.setContent("Raz and Fang together");
            story1.setSummary("They're happy");
            story1.setLanguage("English");
            story1.setPublished(i%2 == 0); // 3 published, 2 draft
            story1.setCreatorId(user.getId());
            story1.setCreatorName(user.getUserName());
            story1.setType(UserStory.STORY_TYPE.TEAM);

            storyService.createUserStory(story1);
        }

        MockHttpServletRequestBuilder request = get(String.format("/api/user/%d/stories?filter=draft&offset=0&length=5", user.getId()));
        Helper.setRequestAllJsonType(request);
        String result = mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andReturn().getResponse().getContentAsString();
        List<UserStory> stories = Helper.getReturnJsonArray(result, UserStory.class);
        assertThat(stories.size(), equalTo(2));
        assertThat(stories.get(0).getTitle(), equalTo("The beautiful night"));
        assertThat(stories.get(0).getContent(), equalTo(null));

        request = get(String.format("/api/user/%d/stories?filter=published&offset=0&length=1", user.getId()));
        Helper.setRequestAllJsonType(request);
        result = mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andReturn().getResponse().getContentAsString();
        stories = Helper.getReturnJsonArray(result, UserStory.class);
        UserStory story = stories.get(0);
        assertThat(stories.size(), equalTo(1));
        assertThat(story.getPublished(), equalTo(true));
        assertThat(story.getSummary(), equalTo("They're happy"));
        assertThat(story.getCreatorId(), equalTo(user.getId()));
    }
}
