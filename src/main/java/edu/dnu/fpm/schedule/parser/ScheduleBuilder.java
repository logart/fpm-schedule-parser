package edu.dnu.fpm.schedule.parser;

import edu.dnu.fpm.schedule.domain.EvenOddFlag;
import edu.dnu.fpm.schedule.domain.SubgroupFlag;

/**
 * Created by Anton Chernetskij
 */
public interface ScheduleBuilder {

    public void addGroup(String name);

    public void addLesson(String groupName, int dayNumber, int lessonNumber, String lessonName,
                          SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag);
}
