package cloudflare.api;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by annguyen on 19/10/16.
 */
public class CloudflareAuth {
    private String key;
    private String email;
    private HttpClient client = new DefaultHttpClient();

    public CloudflareAuth(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public String getKey() {
        return key;
    }


    public String getEmail() {
        return email;
    }

    public HttpClient getClient() {
        return client;
    }
}
