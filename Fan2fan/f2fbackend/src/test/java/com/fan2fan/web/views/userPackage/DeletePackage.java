package com.fan2fan.web.views.userPackage;

import com.fan2fan.application.Application;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class DeletePackage {

    public static final Logger logger = LoggerFactory.getLogger(DeletePackage.class);

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;


    @Autowired
    private PackageService packageService;

    private UserPackage userPackage;

    private UserPackage createPackage() {
        UserPackage userPackage = new UserPackage();
        userPackage.setTitle("jdfskjfsfjd");
        userPackage.setSummary("summary");
        userPackage.setCreatorId(1L);
        userPackage.setCreatorName("luoruici");
        userPackage.setLanguage("English");
        userPackage.setCurrencyId(1L);
        userPackage.setItems("");
        return userPackage;
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        userPackage = createPackage();
        packageService.createPackage(userPackage);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deletePackage() throws Exception {
        Long id = userPackage.getId();
        Assert.assertNotNull(id);

        mockMvc.perform(delete("/packages/{id}/", id))
                    .andExpect(status().isNoContent());

        packageService.getPackage(id);
    }

    @Test
    public void deleteNonexistPackage() throws Exception {
        mockMvc.perform(delete("/package/{id}/", new Long(0)))
                .andExpect(status().isNotFound());
    }
}
