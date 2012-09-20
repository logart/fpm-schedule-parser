package edu.dnu.fpm.schedule.parser;

import edu.dnu.fpm.schedule.domain.EvenOddFlag;
import edu.dnu.fpm.schedule.domain.ScheduleTable;
import edu.dnu.fpm.schedule.domain.SubgroupFlag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public class ScheduleBuilderImpl implements ScheduleBuilder {

    private LinkedHashMap<String,ScheduleTable> schedule = new LinkedHashMap<String, ScheduleTable>();

    @Override
    public void addGroup(String groupName) {
        schedule.put(groupName, new ScheduleTable(groupName));
    }

    @Override
    public void addLesson(String groupName, int dayNumber, int lessonNumber, String lessonName, SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag) {
        ScheduleTable scheduleTable = schedule.get(groupName);
        if (scheduleTable == null) {
            throw new RuntimeException("Unknown group");
        }
        scheduleTable.setLesson(dayNumber, lessonNumber, lessonName, subgroupFlag, evenOddFlag);
    }

    public List<ScheduleTable> build(){
        return new ArrayList<ScheduleTable>(schedule.values());
    }
}
