package cloudflare.api;

import cloudflare.api.constants.Action;
import cloudflare.api.constants.Auth;
import cloudflare.api.constants.CacheType;

/**
 * Created by annguyen on 20/10/16.
 */
public class Main {
    public static void main(String[] args) {
        String zoneId = "f5ddfa8712f656b042ef8c15abe8b642";
        String cacheFile = "http://mapiii.com/";

        CloudflareAuth cloudflareAuth = new CloudflareAuth(Auth.KEY, Auth.EMAIL);
        CloudflareRequest cloudflareRequest = new CloudflareRequest(cloudflareAuth, zoneId, Action.PURGE_CACHE);
        cloudflareRequest.setData(CacheType.FILE, cacheFile);
        cloudflareRequest.execute();
    }
}
