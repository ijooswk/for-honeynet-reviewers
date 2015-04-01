package com.fan2fan.web.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fan2fan.model.Team;
import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.TeamService;
import com.fan2fan.util.Helper;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * Test class for {@link com.fan2fan.web.api.TeamController}
 *
 * @author huangsz
 */
public class TeamControllerTest extends BaseResourceControllerTest {

    private Team club;

    private Team national;

    private User commonUser;

    private User countryManager;

    @Autowired
    private TeamService teamService;

    @Before
    public void setUp() {
        super.setUp();
        this.commonUser = createCommonUser();
        this.countryManager = createCountryManager();
        club = new Team();
        club.setName("Real");
        club.setDesc("Legend");
        club.setCity("Madrid");
        club.setCountry("Spain");
        club.setLeagueId(1L);
        club.setType(Team.TEAM_TYPE.CLUB);
        club.setCreatorId(countryManager.getId());
        national = new Team();
        national.setName("Brazil National Team");
        national.setCountry("Brazil");
        national.setDesc("Samba");
        national.setType(Team.TEAM_TYPE.NATIONAL);
        national.setCreatorId(countryManager.getId());
    }

    @Test
    @Rollback(true)
    public void testCreateTeam() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/team");
        Helper.setRequestAllJsonType(request);

        club.setCreatorId(commonUser.getId());
        request.content(Helper.jsonMapper.writeValueAsString(club));
        basicTest(request, Result.PERMISSION_DENIED);

        club.setCreatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(club));
        basicTest(request, Result.SUCCESS);

        request.content(Helper.jsonMapper.writeValueAsString(national));
        basicTest(request, Result.SUCCESS);

        national.setDesc("another");
        request.content(Helper.jsonMapper.writeValueAsString(national));
        basicTest(request, Result.OCCUPIED);

        club.setStadium("Stadium");
        request.content(Helper.jsonMapper.writeValueAsString(club));
        basicTest(request, Result.OCCUPIED);

        club.setLeagueId(2L);
        request.content(Helper.jsonMapper.writeValueAsString(club));
        String result = mockMvc.perform(request)
                .andExpect(jsonPath(Helper.JSON_OBJ_KEY+".leagueId").value(2))
                .andReturn().getResponse().getContentAsString();
        Team rtObj = Helper.getReturnJsonObject(result, Team.class);
        assertThat(rtObj.getStadium(), equalTo("Stadium"));
    }

    @Test
    @Rollback(true)
    public void testGetTeam() throws Exception {
        createTeam(national);
        createTeam(club);
        MockHttpServletRequestBuilder request = get("/api/team/" + club.getId())
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.name").value("Real"))
                .andExpect(jsonPath("$.desc").value("Legend"))
                .andExpect(jsonPath("$.city").value("Madrid"))
                .andExpect(jsonPath("$.country").value("Spain"))
                .andExpect(jsonPath("$.leagueId").value(1))
                .andExpect(jsonPath("$.type").value("CLUB"));

        request = get("/api/team/" + national.getId())
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request)
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath("$.name").value("Brazil National Team"))
                .andExpect(jsonPath("$.desc").value("Samba"))
                .andExpect(jsonPath("$.country").value("Brazil"))
                .andExpect(jsonPath("$.type").value("NATIONAL"));
    }

    @Test
    @Rollback(true)
    public void testUpdateTeam() throws Exception{
        createTeam(club);
        createTeam(national);

        MockHttpServletRequestBuilder request = post(String.format("/api/team/%d", club.getId()));
        Helper.setRequestAllJsonType(request);

        // update success denied
        club.setStadium("Estadio Santiago Bernabéu");
        club.setOperatorId(countryManager.getId());
        request.content(Helper.jsonMapper.writeValueAsString(club));
        basicTest(request, Result.SUCCESS);

        // check update result
        request = get(String.format("/api/team/%d", club.getId()))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request)
                .andExpect(jsonPath("$.stadium").value("Estadio Santiago Bernabéu"));

        // simple test of national team, too
        national.setDesc("nothing");
        national.setOperatorId(countryManager.getId());
        request = post(String.format("/api/team/%d", national.getId()));
        Helper.setRequestAllJsonType(request);
        request.content(Helper.jsonMapper.writeValueAsString(national));
        basicTest(request, Result.SUCCESS);
    }

    private long createTeam(Team team) {
        teamService.createTeam(team);
        return team.getId();
    }

}
