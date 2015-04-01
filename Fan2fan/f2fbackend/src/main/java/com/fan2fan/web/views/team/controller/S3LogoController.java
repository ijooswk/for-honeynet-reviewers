package com.fan2fan.web.views.team.controller;

import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.service.team.TeamService;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
import com.fan2fan.web.session.SessionManager;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.PathParam;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/teams/{teamId}")
public class S3LogoController {

    public static final Logger logger = LoggerFactory.getLogger(S3LogoController.class);

    private static final String STATE = "state";
    private static final String URL = "url";  // the complate fileName
    private static final String FILE_NAME = "fileName"; // the fileName on the S3, not including the path

    private static final Map<String, String> FAIL = ImmutableMap.of(STATE, "fail");

    @Value("${S3.link}")
    private String s3link;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/logo/", method = RequestMethod.POST)
    @UserTypeAuth(User.USER_TYPE.PARTNER_FAN)
    @LoginRequired
    public Map<String, String> uploadImage(
            @PathVariable("teamId") long teamId,
            @RequestParam("logo") MultipartFile file) {
        if (file.isEmpty()) {
            return FAIL;
        }
        String filename = repositoryService.createRandomFileName(file.getOriginalFilename());
        try {
            Result result = teamService.uploadTeamLogo(teamId,
                    new ByteArrayInputStream(file.getBytes()), filename);
            if (!result.isSuccess()) {
                return FAIL;
            }
            return ImmutableMap.of(STATE, "success",
                    URL, teamService.getCompleteLogoUrl(teamId, filename),
                    FILE_NAME, filename);
        } catch (IOException e) {
            logger.error(e.toString(), e);
            return FAIL;
        }
    }

}
