package edu.dnu.fpm.schedule.parser;

import edu.dnu.fpm.schedule.domain.*;
import junit.framework.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Anton Chernetskij
 */
public class ScheduleParserTest {
    @Test
    public void testParse() throws Exception {
        ScheduleParser parser = new ScheduleParser();
        FileInputStream page = new FileInputStream("src/main/resources/schedule.html");
        List<ScheduleTable> scheduleTables = parser.parse(page);
        assertTableEquals("table 0", expectedTable0(), scheduleTables.get(0));
    }

    public ScheduleTable expectedTable0() {
        ScheduleTable table = new ScheduleTable("ПК-12-1");
        table.setLesson(0, 0, "Архітектура обчислювальних систем (лк.)\n" +
                "        асист. Дзюба П.А.3/25\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(0, 1, "Математичний аналіз (пр.)\n" +
                "        доц. Алхімова В.М.3/37\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(0, 2, "Програмування (лк.)\n" +
                "        доц. Хижа О.Л.3/25\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(0, 3, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(0, 4, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);

        table.setLesson(1, 0, "Українська мова (за професійним спрямуванням) (пр.)\n" +
                "        ст. викл. Сінкевич Н.М.3/30\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(1, 1, "Фізичне виховання (пр.)\n" +
                "        асист. 2 корпус\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(1, 2, "Програмування (пр.)\n" +
                "        доц. Хижа О.Л.3/46\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ODD);
        table.setLesson(1, 2, "Українська мова (за професійним спрямуванням) (лк.)\n" +
                "        доц. Корольова В.В.3/62\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.EVEN);
        table.setLesson(1, 3, "Математичний аналіз (лк.)\n" +
                "        доц. Алхімова В.М.3/53\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(1, 4, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);

        table.setLesson(2, 0, "Дискретна математика (лк.)\n" +
                "        ст. викл. Довгай П.О.3/37\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(2, 1, "Дискретна математика (пр.)\n" +
                "        ст. викл. Довгай П.О.3/30\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(2, 2, "Іноземна мова (пр.)\n" +
                "        ст. викл. Комарова Л.В., ст. викл. Каліберда Н.І3/50\n" +
                "    ", SubgroupFlag.FIRST, EvenOddFlag.ODD);
        table.setLesson(2, 2, "&nbsp;", SubgroupFlag.SECOND, EvenOddFlag.ODD);
        table.setLesson(2, 2, "&nbsp;", SubgroupFlag.FIRST, EvenOddFlag.EVEN);
        table.setLesson(2, 2, "Іноземна мова (пр.)\n" +
                "        ст. викл. Комарова Л.В.3/50\n" +
                "    ", SubgroupFlag.SECOND, EvenOddFlag.EVEN);
        table.setLesson(2, 3, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(2, 4, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);

        table.setLesson(3, 0, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(3, 1, "Фізичне виховання (пр.)\n" +
                "        асист. 2 корпус\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(3, 2, "Іноземна мова (пр.)\n" +
                "        ст. викл. Комарова Л.В., ст. викл. Каліберда Н.І3/59\n" +
                "    ", SubgroupFlag.FIRST, EvenOddFlag.ALL);
        table.setLesson(3, 2, "Архітектура обчислювальних систем (лаб.)\n" +
                "        доц. Ясько Н.Н.4/40\n" +
                "    ", SubgroupFlag.SECOND, EvenOddFlag.ALL);
        table.setLesson(3, 3, "Архітектура обчислювальних систем (лаб.)\n" +
                "        доц. Ясько Н.Н., асист. Дзюба П.А.4/40\n" +
                "    ", SubgroupFlag.FIRST, EvenOddFlag.ALL);
        table.setLesson(3, 3, "Іноземна мова (пр.)\n" +
                "        ст. викл. Комарова Л.В.3/62\n" +
                "    ", SubgroupFlag.SECOND, EvenOddFlag.ALL);
        table.setLesson(3, 4, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);

        table.setLesson(4, 0, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ODD);
        table.setLesson(4, 0, "Програмування (лаб.)\n" +
                "        ст. викл. Верба О.В., ст. викл. Косолап А.І.4/40\n" +
                "    ", SubgroupFlag.FIRST, EvenOddFlag.EVEN);
        table.setLesson(4, 0, "Програмування (лаб.)\n" +
                "        ст. викл. Степанова Н.І.4/43\n" +
                "    ", SubgroupFlag.SECOND, EvenOddFlag.EVEN);
        table.setLesson(4, 1, "Алгебра та геометрія (лк.)\n" +
                "        доц. Сердюк М.Є.3/30\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(4, 2, "Алгебра та геометрія (пр.)\n" +
                "        доц. Сердюк М.Є.3/31\n" +
                "    ", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(4, 3, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);
        table.setLesson(4, 4, "&nbsp;", SubgroupFlag.ALL, EvenOddFlag.ALL);
        return table;
    }

    public void assertTableEquals(String message, ScheduleTable expected, ScheduleTable actual) {
        Assert.assertEquals(expected.getGroupName(), actual.getGroupName());

        Day[] expectedDays = expected.getScheduleContent();
        Assert.assertEquals(5, expectedDays.length);
        Day[] actualDays = actual.getScheduleContent();
        Assert.assertEquals(5, actualDays.length);

        for (int i = 0; i < 5; i++) {
            assertDaysEquals(message + ", day " + i, expectedDays[i], actualDays[i]);
        }
    }

    public void assertDaysEquals(String message, Day expected, Day actual) {
        Pair[] expectedPairs = expected.getPairs();
        Pair[] actualPairs = actual.getPairs();
        Assert.assertEquals(message, expectedPairs.length, actualPairs.length);

        for (int i = 0; i < expectedPairs.length; i++) {
            assertPairsEquals(message + ", pair " + i, expectedPairs[i], actualPairs[i]);
        }
    }

    public void assertPairsEquals(String message, Pair expected, Pair actual) {
        List<Lesson> expectedLessons = expected.getLessons();
        List<Lesson> actualLessons = actual.getLessons();
        Assert.assertEquals(message, expectedLessons.size(), actualLessons.size());

        for (int i = 0; i < expectedLessons.size(); i++) {
            assertLessonEquals(message + ", lesson " + i, expectedLessons.get(i), actualLessons.get(i));
        }
    }

    public void assertLessonEquals(String message, Lesson expected, Lesson actual) {
        Assert.assertNotNull(message, expected);
        Assert.assertNotNull(message, actual);
        Assert.assertEquals(message + ", name", expected.getName(), actual.getName());
        Assert.assertEquals(message + ", EvenOddFlag", expected.getEvenOddFlag(), actual.getEvenOddFlag());
        Assert.assertEquals(message + ", SubgroupFlag", expected.getSubgroupFlag(), actual.getSubgroupFlag());
    }


}
//        table.setLesson(2,0,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(2,1,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(2,2,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(2,3,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(2,4,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//
//        table.setLesson(3,0,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(3,1,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(3,2,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(3,3,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(3,4,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//
//        table.setLesson(4,0,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(4,1,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(4,2,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(4,3,"", SubgroupFlag.ALL, EvenOddFlag.ALL);
//        table.setLesson(4,4,"", SubgroupFlag.ALL, EvenOddFlag.ALL);