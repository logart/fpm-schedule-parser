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
    private String serverAddress;

    public RestClient(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void put(ScheduleTable schedule) {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        Client client = Client.create(clientConfig);

        WebResource webResource = client
                .resource(serverAddress);

        ClientResponse response = webResource.path("schedules").type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, schedule);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

    }
}
