package com.fan2fan.web.views.users.controller;

import com.fan2fan.service.Result;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.Message;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
import com.fan2fan.web.session.SessionManager;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/users/avatar/")
public class S3AvatarController {

    public static final Logger logger = LoggerFactory.getLogger(S3AvatarController.class);

    private static final String URL = "url";  // the complate fileName
    private static final String FILENAME = "filename"; //the actual file name

    private static final Map<String, String> FAIL = ImmutableMap.of(Message.KEY_STATE, "fail");

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @UserTypeAuth
    @LoginRequired
    public Map<String, String> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return FAIL;
        }
        String filename = repositoryService.createRandomFileName(file.getOriginalFilename());
        try {
            Result result = userService.uploadAvatar(SessionManager.getUserId(),
                    new ByteArrayInputStream(file.getBytes()), filename);
            if (!result.isSuccess()) {
                return FAIL;
            }
            return ImmutableMap.of(Message.KEY_STATE, "success",
                    URL, userService.getCompleteAvatarUrl(SessionManager.getUserId(), filename),
                    FILENAME, filename);
        } catch (IOException e) {
            logger.error(e.toString(), e);
            return FAIL;
        }
    }
}
