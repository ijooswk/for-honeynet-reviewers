package com.fan2fan.web.api;

import com.fan2fan.model.Email;
import com.fan2fan.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    public static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/email", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void send(@RequestBody Email email) {
        logger.debug("{} {} {}", email.getTo(), email.getSubject(), email.getContent());
        emailService.sendEmail(email.getTo(), email.getSubject(), email.getContent());
    }
}
