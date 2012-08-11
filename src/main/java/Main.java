import java.io.IOException;
import java.util.List;

import edu.dnu.fpm.schedule.domain.ScheduleTable;
import edu.dnu.fpm.schedule.parser.RestClient;
import edu.dnu.fpm.schedule.parser.ScheduleGetter;
import edu.dnu.fpm.schedule.parser.ScheduleParser;

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
        List<ScheduleTable> scheduleTables = parser.parse(getter.get());
        for (ScheduleTable scheduleTable : scheduleTables) {
            client.put(scheduleTable);
        }

    }

}
