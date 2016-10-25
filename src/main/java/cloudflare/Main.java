package cloudflare;

import cloudflare.settings.ClientSettings;
import cloudflare.settings.Constants;
import cloudflare.settings.SystemSettings;

/**
 * Created by annguyen on 20/10/16.
 */
public class Main {
    public static void main(String[] args) {
        String cacheFile = "http://mapiii.com/index.html";

        CloudflareAuth cloudflareAuth = new CloudflareAuth(SystemSettings.KEY, SystemSettings.EMAIL);
        CloudflareRequest cloudflareRequest = new CloudflareRequest(cloudflareAuth, ClientSettings.ZONE_ID, Constants.ACTION_PURGE_CACHE);
        cloudflareRequest.setData(Constants.PURGE_FILE, cacheFile);
        cloudflareRequest.execute();
    }
}
