package com.fan2fan.web.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fan2fan.model.League;
import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.LeagueService;
import com.fan2fan.util.Helper;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * Integration test for {@link com.fan2fan.web.api.LeagueController}
 *
 * @author huangsz
 */
public class LeagueControllerTest extends BaseResourceControllerTest {

    @Autowired
    private LeagueService leagueService;

    private League league;

    private User commonUser;

    private User countryManager;

    private final String LEAGUE_NAME = "EPL";

    @Before
    public void setUp() {
        super.setUp();
        this.commonUser = createCommonUser();
        this.countryManager = createCountryManager();
        this.league = new League();
        league.setName(LEAGUE_NAME);
        league.setDesc("xxx");
        league.setLogoUrl("league/2.png");
        league.setCreatorId(countryManager.getId());
    }

    @Test
    @Rollback(true)
    public void testCreateLeague() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/league");
        Helper.setRequestAllJsonType(request);

        league.setCreatorId(commonUser.getId());
        request.content(Helper.jsonMapper.writeValueAsString(league));
        basicTest(request, Result.PERMISSION_DENIED);

        league.setCreatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(league));
        String result = mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(Result.SUCCESS.toString()))
                .andReturn().getResponse().getContentAsString();
        League returned = Helper.getReturnJsonObject(result, League.class);
        assertThat(returned.getCreatorId(), equalTo(countryManager.getId()));
        assertThat(returned.getName(), equalTo(LEAGUE_NAME));
        assertThat(returned.getDesc(), equalTo("xxx"));
        assertThat(returned.getLogoUrl(), equalTo("league/2.png"));

        // can not create again with the same name
        basicTest(request, Result.OCCUPIED);
    }

    @Test
    @Rollback(true)
    public void testGetLeague() throws Exception {
        leagueService.createLeague(league);
        MockHttpServletRequestBuilder request = get("/api/league/" + league.getId())
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.name").value(LEAGUE_NAME))
                .andExpect(jsonPath("$.desc").value("xxx"))
                .andExpect(jsonPath("$.logoUrl").value("league/2.png"));
    }

    @Test
    @Rollback(true)
    public void testUpdateLeague() throws Exception {
        leagueService.createLeague(league);
        MockHttpServletRequestBuilder request = post(String.format("/api/league/%d", league.getId()));
        Helper.setRequestAllJsonType(request);

        // permission denied
        league.setDesc("Spanish League");
        league.setOperatorId(commonUser.getId());
        request.content(Helper.jsonMapper.writeValueAsString(league));
        basicTest(request, Result.PERMISSION_DENIED);

        // success
        league.setOperatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(league));
        basicTest(request, Result.SUCCESS);

        // check update result
        request = get(String.format("/api/league/%d", league.getId()))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request)
                .andExpect(jsonPath("$.desc").value("Spanish League"));
    }
}
