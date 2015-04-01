package com.fan2fan.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time related util functions
 * Created by huangsz on 2014/5/24.
 */
public class TimeUtils {

    /**
     * get the current timestamp which the millis part is aborted
     * @return
     */
    public static Timestamp getCurrentTimeAbortMillis() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(df.format(new Date()));
    }
}
