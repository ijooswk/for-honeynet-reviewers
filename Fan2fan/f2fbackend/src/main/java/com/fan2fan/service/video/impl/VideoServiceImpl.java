package com.fan2fan.service.video.impl;

import com.fan2fan.service.video.VideoService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangsz
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public String getIdFromLink(String link, Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return getYoukuVideoId(link);
        } else {
            return getYoukuVideoId(link);  // all youku currently
        }
    }

    @Override
    public String getIdFromLink(String link) {
        return getIdFromLink(link, Locale.CHINESE);
    }

    private String getYoukuVideoId(String link) {
        Pattern p = Pattern.compile(".*id_([\\w\\d]+)\\.html");
        Matcher matcher = p.matcher(link);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
