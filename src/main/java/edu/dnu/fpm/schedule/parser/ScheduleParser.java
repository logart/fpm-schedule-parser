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

        TagNode[] tableRows = tableBody.get(0).getAllElements(false);

        TagNode groupsRow = tableRows[0];
        List<ScheduleTable> schedules = createSchedules(groupsRow);

        int line = 0;
        boolean isEvenLineExist;
        boolean evenLine = false;
        Queue<Integer> evenLessonIndexes = new LinkedList<Integer>();

        for (int rowNumber = 1, rowCount = tableRows.length; rowNumber < rowCount; rowNumber++) {
            TagNode row = tableRows[rowNumber];

            TagNode[] rowElements = row.getAllElements(false);

            int groupNumber = 0;
            boolean isAdditionalGroupInfoAvailable = false;

            isEvenLineExist = false;
            boolean startsWithHeader = "10".equals(rowElements[0].getAttributeByName("rowspan"));
            for (int elementNumber = startsWithHeader ? 1 : 0, elementsCount = rowElements.length; elementNumber < elementsCount; elementNumber++) {
                TagNode cell = rowElements[elementNumber];

                int cellWidth = getCellWidth(cell);
//                assert "1".equals(cell.getAttributeByName("rowspan")) || "0".equals(cell.getAttributeByName("rowspan")) || cell.getAttributeByName("rowspan") == null;
                boolean rowspan = cell.getAttributeByName("rowspan") != null;

                isEvenLineExist = isEvenLineExist || rowspan && !evenLine;

                //parsing line of schedule
                final SubgroupFlag subgroupFlag;
                if (cellWidth == 0) {
                    subgroupFlag = isAdditionalGroupInfoAvailable ? SubgroupFlag.SECOND : SubgroupFlag.FIRST;
                    isAdditionalGroupInfoAvailable = !isAdditionalGroupInfoAvailable;
                } else {
                    //this lesson is for all group(not for group parts)
                    subgroupFlag = SubgroupFlag.ALL;
                }


                final EvenOddFlag evenOddFlag = evenOddFlag(rowspan, evenLine, subgroupFlag, evenLessonIndexes, groupNumber, cellWidth);

                if (evenLine && subgroupFlag.equals(SubgroupFlag.FIRST)) {
                    groupNumber = evenLessonIndexes.remove();
                }

                if (cellWidth == 0) {
                    schedules.get(groupNumber).setLesson(line / 5, line % 5, cell.getText().toString(), subgroupFlag, evenOddFlag);
                }

                if (evenLine) {
                    for (int j = 0; j < cellWidth; j++) {
                        groupNumber = evenLessonIndexes.remove();
                        schedules.get(groupNumber).setLesson(line / 5, line % 5, cell.getText().toString(), subgroupFlag, evenOddFlag);
                    }
                } else {
                    for (int j = 0; j < cellWidth; ++j) {
                        schedules.get(groupNumber + j).setLesson(line / 5, line % 5, cell.getText().toString(), subgroupFlag, evenOddFlag);
                    }
                    groupNumber += cellWidth;
                    if (cellWidth == 0 && !isAdditionalGroupInfoAvailable) {
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

    private List<ScheduleTable> createSchedules(TagNode groupsRow) {
        List<ScheduleTable> schedules = new ArrayList<ScheduleTable>(groupsRow.getAllElements(false).length);

        TagNode[] allElements1 = groupsRow.getAllElements(false);
        for (int i2 = 1, allElements1Length = allElements1.length; i2 < allElements1Length; i2++) {
            TagNode group = allElements1[i2];
            schedules.add(new ScheduleTable(group.getText().toString()));
        }
        return schedules;
    }

    private int getCellWidth(TagNode element) {
        return element.getAttributeByName("colspan") == null ? 0 : Integer.parseInt(element.getAttributeByName("colspan")) / 2;
    }

    private EvenOddFlag evenOddFlag(boolean rowspan, boolean evenLine, SubgroupFlag subgroupFlag, Queue<Integer> evenLessonIndexes, int groupNumber, int colspan){
        EvenOddFlag evenOddFlag;
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
        return evenOddFlag;
    }
}