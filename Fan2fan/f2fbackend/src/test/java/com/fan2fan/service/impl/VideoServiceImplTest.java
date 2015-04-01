package com.fan2fan.service.impl;

import com.fan2fan.service.video.VideoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author huangsz
 */
public class VideoServiceImplTest extends BaseServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void getIdFromLink() {
        String link = "http://v.youku.com/v_show/id_XNzMyNjAzMDI4.html?f=22476364&ev=3&from=y1.3-idx-grid-1519-9909.86808-86807.3-1";
        assertThat(videoService.getIdFromLink(link), equalTo("XNzMyNjAzMDI4"));
    }
}
