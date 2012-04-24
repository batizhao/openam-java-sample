package me.batizhao;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: batizhao
 * @since: 12-4-23 下午4:02
 */
public class AllUsers {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://openam.example.com:9090/openidm/managed/user/?_query-id=query-all-ids");

        get.addHeader("X-OpenIDM-Username", "openidm-admin");
        get.addHeader("X-OpenIDM-Password", "openidm-admin");

        HttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
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
