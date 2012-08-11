package edu.dnu.fpm.schedule.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.dnu.fpm.schedule.domain.EvenOddFlag;
import edu.dnu.fpm.schedule.domain.ScheduleTable;
import edu.dnu.fpm.schedule.domain.SubgroupFlag;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 8/11/12
 *         Time: 4:25 PM
 */
public class ScheduleParser {

    @SuppressWarnings("unchecked")
    public List<ScheduleTable> parse(InputStream scheduleRawData) throws IOException {

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode clean = cleaner.clean(scheduleRawData);

        List<TagNode> tableBody = clean.getElementListByName("tbody", true);

        TagNode[] allElements = tableBody.get(0).getAllElements(false);

        TagNode groups = allElements[0];

        List<ScheduleTable> schedules = new ArrayList<ScheduleTable>(groups.getAllElements(false).length);

        TagNode[] allElements1 = groups.getAllElements(false);
        for (int i2 = 1, allElements1Length = allElements1.length; i2 < allElements1Length; i2++) {
            TagNode group = allElements1[i2];
            schedules.add(new ScheduleTable(group.getText().toString()));
        }

        int line = 0;
        boolean isEvenLineExist;
        boolean evenLine = false;
        Queue<Integer> evenLessonIndexes = new LinkedList<Integer>();

        for (int i = 1, allElementsLength = allElements.length; i < allElementsLength; i++) {
            TagNode node = allElements[i];

            TagNode[] elements = node.getAllElements(false);

            int groupNumber = 0;
            boolean isAdditionalGroupInfoAvailable = false;

            isEvenLineExist = false;
            for (int i1 = "10".equals(elements[0].getAttributeByName("rowspan")) ? 1 : 0, elementsLength = elements.length; i1 < elementsLength; i1++) {
                TagNode element = elements[i1];

                int colspan = element.getAttributeByName("colspan") == null ? 0 : Integer.parseInt(element.getAttributeByName("colspan")) / 2;
                assert "1".equals(element.getAttributeByName("rowspan")) || "0".equals(element.getAttributeByName("rowspan")) || element.getAttributeByName("rowspan") == null;
                boolean rowspan = element.getAttributeByName("rowspan") != null;

                if (rowspan && !evenLine) {
                    isEvenLineExist = true;
                }

                //parsing line of schedule
                final SubgroupFlag subgroupFlag;
                if (colspan == 0) {
                    if (isAdditionalGroupInfoAvailable) {
                        isAdditionalGroupInfoAvailable = false;
                        subgroupFlag = SubgroupFlag.SECOND;
                    } else {
                        isAdditionalGroupInfoAvailable = true;
                        subgroupFlag = SubgroupFlag.FIRST;
                    }

                } else {
                    //this lesson is for all group(not for group parts)
                    subgroupFlag = SubgroupFlag.ALL;
                }


                final EvenOddFlag evenOddFlag;
                if (!rowspan) {
                    if (evenLine) {
                        evenOddFlag = EvenOddFlag.EVEN;
                    } else {
                        evenOddFlag = EvenOddFlag.ODD;
                        if (subgroupFlag.equals(SubgroupFlag.SECOND)) {
                            evenLessonIndexes.add(groupNumber);

                        }
                        if (subgroupFlag.equals(SubgroupFlag.ALL)) {
                            for (int k = 0; k < colspan; ++k) {
                                evenLessonIndexes.add(groupNumber + k);
                            }
                        }
                    }
                } else {
                    evenOddFlag = EvenOddFlag.ALL;
                }

                if (evenLine && subgroupFlag.equals(SubgroupFlag.FIRST)) {
                    groupNumber = evenLessonIndexes.remove();
                }

                if (colspan == 0) {
                    schedules.get(groupNumber).setLesson(line / 5, line % 5, element.getText().toString(), subgroupFlag, evenOddFlag);
                }

                if (evenLine) {
                    for (int j = 0; j < colspan; j++) {
                        groupNumber = evenLessonIndexes.remove();
                        schedules.get(groupNumber).setLesson(line / 5, line % 5, element.getText().toString(), subgroupFlag, evenOddFlag);
                    }
                } else {
                    for (int j = 0; j < colspan; ++j) {
                        schedules.get(groupNumber + j).setLesson(line / 5, line % 5, element.getText().toString(), subgroupFlag, evenOddFlag);
                    }
                }

                if (!evenLine) {
                    groupNumber += colspan;
                    if (colspan == 0 && !isAdditionalGroupInfoAvailable) {
                        groupNumber++;
                    }
                }
            }

            if (!isEvenLineExist) {
                line++;
                evenLessonIndexes.clear();
                evenLine = false;
            } else {
                evenLine = true;
            }

            if (line >= 25) {
                break;
            }
        }
        return schedules;
    }
}