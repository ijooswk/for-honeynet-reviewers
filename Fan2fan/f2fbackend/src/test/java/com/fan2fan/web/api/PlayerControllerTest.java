package com.fan2fan.web.api;

import com.fan2fan.model.Player;
import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.PlayerService;
import com.fan2fan.util.Helper;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link com.fan2fan.web.api.PlayerController}
 * @author huangsz
 */
public class PlayerControllerTest extends BaseResourceControllerTest {

    private Player player;

    private User commonUser;

    private User countryManager;

    @Autowired
    private PlayerService playerService;

    @Before
    public void setUp() {
        super.setUp();
        this.commonUser = createCommonUser();
        this.countryManager = createCountryManager();
        player = new Player();
        player.setName("Fang Yedun");
        player.setDesc("He can score in 5 min");
        player.setCountry("China");
        player.setNumber(4);
        player.setPosition("left back");
        player.setImageUrl("player/3.png");
        player.setTeamId(1L);
        player.setCreatorId(countryManager.getId());
    }

    @Test
    @Rollback(true)
    public void testCreatePlayer() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/player");
        Helper.setRequestAllJsonType(request);

        player.setCreatorId(commonUser.getId());
        request.content(Helper.jsonMapper.writeValueAsString(player));
        basicTest(request, Result.PERMISSION_DENIED);

        player.setCreatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(player));
        String result = mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(Result.SUCCESS.toString()))
                .andReturn().getResponse().getContentAsString();
        Player returned = Helper.getReturnJsonObject(result, Player.class);
        assertThat(returned.getCreatorId(), equalTo(countryManager.getId()));
        assertThat(returned.getName(), equalTo("Fang Yedun"));
        assertThat(returned.getPosition(), equalTo("left back"));
    }

    @Test
    @Rollback(true)
    public void testGetPlayer() throws Exception {
        playerService.createPlayer(player);
        MockHttpServletRequestBuilder request = get("/api/player/" + player.getId())
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.name").value("Fang Yedun"))
                .andExpect(jsonPath("$.position").value("left back"))
                .andExpect(jsonPath("$.imageUrl").value("player/3.png"));
    }

    @Test
    @Rollback(true)
    public void testUpdatePlayer() throws Exception {
        playerService.createPlayer(player);
        MockHttpServletRequestBuilder request = post(String.format("/api/player/%d", player.getId()));
        Helper.setRequestAllJsonType(request);

        // permission denied
        player.setDesc("Chinese Player");
        player.setOperatorId(commonUser.getId());
        request.content(Helper.jsonMapper.writeValueAsString(player));
        basicTest(request, Result.PERMISSION_DENIED);

        // success
        player.setOperatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(player));
        basicTest(request, Result.SUCCESS);

        // check update result
        request = get(String.format("/api/player/%d", player.getId()))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request)
                .andExpect(jsonPath("$.desc").value("Chinese Player"));
    }
}
