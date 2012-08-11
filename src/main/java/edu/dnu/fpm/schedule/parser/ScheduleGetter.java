package edu.dnu.fpm.schedule.parser;

import java.io.InputStream;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 8/11/12
 *         Time: 4:30 PM
 */
public class ScheduleGetter {
    public InputStream get() {
        return ScheduleGetter.class.getClassLoader().getResourceAsStream("schedule.html");
    }
}
