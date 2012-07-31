package org.logart.schedule.domain;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 7/26/12
 *         Time: 6:59 PM
 */
public class Day {
    Pair[] dayContent;

    public Day() {
        this.dayContent = new Pair[5];
        for (int i = 0, dayContentLength = dayContent.length; i < dayContentLength; i++) {
            dayContent[i] = new Pair();
        }
    }

    public void setLesson(int positionOfLessonInDay, String name, SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag) {
        dayContent[positionOfLessonInDay].setLesson(name, subgroupFlag, evenOddFlag);
    }

    public Pair[] getPairs() {
        return dayContent;
    }
}
