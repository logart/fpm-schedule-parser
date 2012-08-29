import java.io.IOException;
import java.util.List;

import edu.dnu.fpm.schedule.domain.ScheduleTable;
import edu.dnu.fpm.schedule.parser.*;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 7/22/12
 *         Time: 7:23 PM
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ScheduleParser parser = new ScheduleParser();
        ScheduleGetter getter = new ScheduleGetter();
        RestClient client = new RestClient("http://localhost:8089/schedule-rest-server");
        ScheduleBuilderImpl scheduleBuilder = (ScheduleBuilderImpl) parser.parse(getter.get(), new ScheduleBuilderImpl());
        List<ScheduleTable> scheduleTables = scheduleBuilder.build();
        for (ScheduleTable scheduleTable : scheduleTables) {
            client.put(scheduleTable);
        }

    }

}
