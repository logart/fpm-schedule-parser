package org.logart.schedule.domain;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 7/26/12
 *         Time: 6:58 PM
 */
public class ScheduleTable {
    Day[] scheduleContent;
    String groupName;

    public ScheduleTable(String groupName) {
        this.groupName = groupName;
        scheduleContent = new Day[5];
        for (int i = 0, scheduleContentLength = scheduleContent.length; i < scheduleContentLength; i++) {
            scheduleContent[i] = new Day();
        }
    }

    public void setLesson(int dayNumber, int positionOfLessonInDay, String name, SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag) {
        scheduleContent[dayNumber].setLesson(positionOfLessonInDay, name, subgroupFlag, evenOddFlag);
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        text.append(groupName).append("\n\n");
        for (int i = 0, scheduleContentLength = scheduleContent.length; i < scheduleContentLength; i++) {
            Day day = scheduleContent[i];
            text.append("Day ").append(i + 1).append("\n");
            Pair[] pairs = day.getPairs();
            for (int i1 = 0, pairsLength = pairs.length; i1 < pairsLength; i1++) {
                Pair pair = pairs[i1];
                text.append("Pair ").append(i1 + 1).append("\n");
                for (Lesson lesson : pair.getLessons()) {
                    text.append(lesson.toString());
                }
            }
            text.append("\n--------------------------------------------------------------------------------------\n");
        }
        return text.toString();
    }
}
