package com.fan2fan.service.video;

import java.util.Locale;

/**
 * @author huangsz
 */
public interface VideoService {

    /**
     * get video Id from a link.
     * @param link: the url user typed in
     * @param locale: Chinese users prefer Youku
     * @return
     */
    public String getIdFromLink(String link, Locale locale);

    /**
     * get video id from a link
     * @param link
     * @return
     */
    public String getIdFromLink(String link);
}
