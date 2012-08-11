package edu.dnu.fpm.schedule.parser;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.dnu.fpm.schedule.domain.ScheduleTable;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * @author Artem Loginov (logart) logart2007@gmail.com
 *         Date: 8/11/12
 *         Time: 1:17 AM
 */
public class RestClient {
    public static void main(String[] args) {

        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getClasses().add(JacksonJsonProvider.class);
            Client client = Client.create(clientConfig);

            WebResource webResource = client
                    .resource("http://localhost:8089/schedule-rest-server");

            ClientResponse response = webResource.path("schedules").type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, new ScheduleTable("pk"));

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
