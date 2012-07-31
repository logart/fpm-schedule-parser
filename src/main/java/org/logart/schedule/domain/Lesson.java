package org.logart.schedule.domain;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 7/26/12
 *         Time: 6:49 PM
 */
public class Lesson {
    String name;
    EvenOddFlag evenOddFlag;
    SubgroupFlag subgroupFlag;

    public Lesson(String name, SubgroupFlag subgroupFlag, EvenOddFlag evenOddFlag) {
        this.name = name;
        this.subgroupFlag = subgroupFlag;
        this.evenOddFlag = evenOddFlag;
    }

    @Override
    public String toString() {
        return name + " " + subgroupFlag + " " + evenOddFlag + "\n";
    }
}
