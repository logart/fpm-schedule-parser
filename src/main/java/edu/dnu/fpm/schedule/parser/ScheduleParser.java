package edu.dnu.fpm.schedule.parser;

import edu.dnu.fpm.schedule.domain.EvenOddFlag;
import edu.dnu.fpm.schedule.domain.SubgroupFlag;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 8/11/12
 *         Time: 4:25 PM
 */
public class ScheduleParser {

    @SuppressWarnings("unchecked")
    public ScheduleBuilder parse(InputStream scheduleRawData, ScheduleBuilder scheduleBuilder) throws IOException {

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode clean = cleaner.clean(scheduleRawData);

        List<TagNode> tableBody = clean.getElementListByName("tbody", true);

        TagNode[] tableRows = tableBody.get(0).getAllElements(false);

        TagNode groupsRow = tableRows[0];
        String[] groupNames = getGroupNames(groupsRow);
        for (String groupName : groupNames) {
            scheduleBuilder.addGroup(groupName);
        }

        int line = 0;
        boolean isNextLineEven;
        boolean evenLine = false;
        Queue<Integer> evenLessonGroupsIndexes = new LinkedList<Integer>();

        for (int rowNumber = 1, rowCount = tableRows.length; rowNumber < rowCount; rowNumber++) {
            TagNode row = tableRows[rowNumber];

            TagNode[] rowElements = row.getAllElements(false);

            int groupNumber = 0;
            boolean isAdditionalGroupInfoAvailable = false;
            isNextLineEven = false;
            boolean startsWithHeader = "10".equals(rowElements[0].getAttributeByName("rowspan"));

            for (int elementNumber = startsWithHeader ? 1 : 0, elementsCount = rowElements.length; elementNumber < elementsCount; elementNumber++) {
                TagNode cell = rowElements[elementNumber];

                int cellWidth = getCellWidth(cell);
//                assert "1".equals(cell.getAttributeByName("hasRowspan")) || "0".equals(cell.getAttributeByName("hasRowspan")) || cell.getAttributeByName("hasRowspan") == null;
                boolean highCell = cell.getAttributeByName("rowspan") != null;

                isNextLineEven = isNextLineEven || highCell && !evenLine;

                //parsing line of schedule
                final SubgroupFlag subgroupFlag = subgroup(cellWidth, isAdditionalGroupInfoAvailable);

                if (!subgroupFlag.equals(SubgroupFlag.ALL)) {
                    isAdditionalGroupInfoAvailable = !isAdditionalGroupInfoAvailable;
                }

                final EvenOddFlag evenOddFlag = evenOdd(highCell, evenLine);

                evenLessonGroupsIndexes = enqueueGroupIndex(evenLessonGroupsIndexes, evenOddFlag, subgroupFlag, groupNumber, cellWidth);
                // odd lessons will be in the next row

                if (evenLine && subgroupFlag.equals(SubgroupFlag.FIRST)) {
                    groupNumber = evenLessonGroupsIndexes.remove();
                }

                final int dayNumber = line / 5;
                final int lessonNumber = line % 5;
                final String lessonName = cell.getText().toString();
                if (cellWidth == 0) {
                    final String groupName = groupNames[groupNumber];
                    scheduleBuilder.addLesson(groupName, dayNumber, lessonNumber, lessonName, subgroupFlag, evenOddFlag);
                }

                if (evenLine) {
                    for (int j = 0; j < cellWidth; j++) {
                        groupNumber = evenLessonGroupsIndexes.remove();
                        final String groupName = groupNames[groupNumber];
                        scheduleBuilder.addLesson(groupName, dayNumber, lessonNumber, lessonName, subgroupFlag, evenOddFlag);
                    }
                } else {
                    for (int j = 0; j < cellWidth; ++j) {
                        final String groupName = groupNames[groupNumber + j];
                        scheduleBuilder.addLesson(groupName, dayNumber, lessonNumber, lessonName, subgroupFlag, evenOddFlag);
                    }
                    groupNumber += cellWidth;
                    if (cellWidth == 0 && !isAdditionalGroupInfoAvailable) {
                        groupNumber++;
                    }
                }
            }

            if (!isNextLineEven) {
                line++;
                evenLessonGroupsIndexes.clear();
                evenLine = false;
            } else {
                evenLine = true;
            }

            if (line >= 25) {
                break;
            }
        }
        return scheduleBuilder;
    }

    private String[] getGroupNames(TagNode groupsRow) {
        String[] groupNames = new String[groupsRow.getAllElements(false).length - 1];
        TagNode[] allElements1 = groupsRow.getAllElements(false);
        for (int elementNumber = 1, allElements1Length = allElements1.length; elementNumber < allElements1Length; elementNumber++) {
            TagNode group = allElements1[elementNumber];
            groupNames[elementNumber - 1] = group.getText().toString();
        }
        return groupNames;
    }

    private int getCellWidth(TagNode element) {
        return element.getAttributeByName("colspan") == null ? 0 : Integer.parseInt(element.getAttributeByName("colspan")) / 2;
    }

    private SubgroupFlag subgroup(int cellWidth, boolean isAdditionalGroupInfoAvailable) {
        SubgroupFlag subgroupFlag;
        if (cellWidth == 0) {
            subgroupFlag = isAdditionalGroupInfoAvailable ? SubgroupFlag.SECOND : SubgroupFlag.FIRST;
        } else {
            //this lesson is for all group(not for group parts)
            subgroupFlag = SubgroupFlag.ALL;
        }
        return subgroupFlag;
    }

    private EvenOddFlag evenOdd(boolean rowspan, boolean evenLine) {
        EvenOddFlag evenOddFlag;
        if (!rowspan) {
            if (evenLine) {
                evenOddFlag = EvenOddFlag.EVEN;
            } else {
                evenOddFlag = EvenOddFlag.ODD;
            }
        } else {
            evenOddFlag = EvenOddFlag.ALL;
        }
        return evenOddFlag;
    }

    private Queue<Integer> enqueueGroupIndex(Queue<Integer> evenLessonGroupsIndexes,
                                             EvenOddFlag evenOddFlag, SubgroupFlag subgroupFlag,
                                             int groupNumber, int cellWidth) {
        if (evenOddFlag.equals(EvenOddFlag.ODD)) {
            if (subgroupFlag.equals(SubgroupFlag.SECOND)) {
                evenLessonGroupsIndexes.add(groupNumber);
            }
            if (subgroupFlag.equals(SubgroupFlag.ALL)) {
                for (int k = 0; k < cellWidth; ++k) {
                    evenLessonGroupsIndexes.add(groupNumber + k);
                }
            }
        }
        return evenLessonGroupsIndexes;
    }
}