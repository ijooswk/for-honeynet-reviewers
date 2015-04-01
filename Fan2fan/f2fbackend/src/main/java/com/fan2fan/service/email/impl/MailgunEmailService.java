package com.fan2fan.service.email.impl;

import com.fan2fan.service.email.EmailService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class MailgunEmailService implements EmailService {

    public static final Logger logger = LoggerFactory.getLogger(MailgunEmailService.class);

    @Value("${mailgun.url}")
    private String url;

    @Value("${mailgun.apikey}")
    private String apikey;

    @Value("${mailgun.from}")
    private String from;

    private WebResource messageWebResource;

    @PostConstruct
    public void init() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", this.apikey));
        this.messageWebResource = client.resource(this.url);
    }

    private MultivaluedMapImpl getFormData(String to, String subject, String content) {
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", this.from);
        formData.add("to", to);
        formData.add("subject", subject);
        formData.add("html", content);

        return formData;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String content) {
        MultivaluedMapImpl formData = getFormData(to, subject, content);
        ClientResponse clientResponse = messageWebResource
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .post(ClientResponse.class, formData);

        InputStream in = clientResponse.getEntityInputStream();
        String message;
        try {
            message = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
        } catch (IOException e) {
            message = "IOException when reading from response inputstream";
            logger.error(message, e);
        }
        logger.debug("status code is:{}", clientResponse.getStatus());
        if (Strings.isNullOrEmpty(message)) {
            logger.debug("response message is : {}", message);
        }
    }
}
