package org.logart.schedule.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 7/26/12
 *         Time: 6:56 PM
 */
public class Pair {
    List<Lesson> pairContent;

    public Pair() {
        pairContent = new ArrayList<Lesson>(4);
    }

    public void setLesson(String name, SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag) {
        pairContent.add(new Lesson(name, subgroupFlag, evenOddFlag));
    }

    public List<Lesson> getLessons() {
        return pairContent;
    }
}
