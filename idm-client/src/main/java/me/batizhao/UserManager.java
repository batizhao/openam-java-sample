package me.batizhao;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: batizhao
 * @since: 12-4-23 下午4:14
 */
public class UserManager {

    public static void main(String[] args) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPut put = new HttpPut("http://openam.example.com:9090/openidm/managed/user/james");

        put.addHeader("X-OpenIDM-Username", "openidm-admin");
        put.addHeader("X-OpenIDM-Password", "openidm-admin");

        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setUserName("james");
        user.setGivenName("Berg");
        user.setFamilyName("James");
        user.setEmail("zhaobati@gmail.com");
        user.setDescription("I'm Programmer.");
        System.out.println(mapper.writeValueAsString(user));

        //StringEntity input = new StringEntity("{\"userName\":\"bati\",\"givenName\":\"bati\",\"familyName\":\"smith\",\"email\":[\"joe@example.com\"],\"description\":\"My first user\"}");
        StringEntity input = new StringEntity(mapper.writeValueAsString(user));
        input.setContentType("application/json");
        put.setEntity(input);

        HttpResponse response = httpClient.execute(put);

        if (response.getStatusLine().getStatusCode() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = reader.readLine()) != null) {
            System.out.println(output);
        }

        httpClient.getConnectionManager().shutdown();
    }
}
