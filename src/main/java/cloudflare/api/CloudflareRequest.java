package cloudflare.api;

import cloudflare.api.constants.Auth;
import cloudflare.api.utils.HttpDeleteWithBody;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by annguyen on 19/10/16.
 */
public class CloudflareRequest {
    private static CloudflareAuth cloudflareAuth;
    private static String requestURL;
    private static String postData = "";
    final static Logger logger = Logger.getLogger(CloudflareRequest.class);

    public CloudflareRequest(CloudflareAuth cloudflareAuth, String zoneId, String action) {
        this.cloudflareAuth = cloudflareAuth;
        requestURL = String.format("%s/%s/%s", Auth.API_ENDPOINT, zoneId, action);
    }

    public static void setData(String key, String value) {
        JsonArray arr = new JsonArray();
        arr.add(value);
        JsonObject obj = new JsonObject();
        obj.add(key, arr);
        postData = obj.toString();
    }

    public static void execute() {
        HttpDeleteWithBody deleteReq = new HttpDeleteWithBody(requestURL);
        deleteReq.setHeader("X-Auth-Email", cloudflareAuth.getEmail());
        deleteReq.setHeader("X-Auth-Key", cloudflareAuth.getKey());
        deleteReq.setHeader("Content-Type", "application/json");
        deleteReq.setEntity(new StringEntity(postData, "UTF-8"));
        try {
            StringBuilder sb = new StringBuilder();
            HttpResponse response = cloudflareAuth.getClient().execute(deleteReq);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            JsonObject objResult = new JsonParser().parse(sb.toString()).getAsJsonObject();
            if (!objResult.has("success")) {
                logger.info("Server error");
            }
            String result = objResult.get("success").toString();
            switch (result) {
                case "true":
                    logger.info(objResult.get("result").toString());
                    break;
                case "false":
                    logger.error(objResult.get("errors").toString());
                    break;
                default:
                    logger.error("Internal error");
                    break;
            }
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }
}
