package kr.ac.kaist.mobile.attable;

import android.os.Build;

import com.squareup.okhttp.OkHttpClient;

import kr.ac.kaist.mobile.attable.api.Api;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {

    private static Api REST_CLIENT;
    private static String ROOT;

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static Api get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        // Set ip to localhost if started from phone emulator and to real host if started from real phone
        if(Build.FINGERPRINT.startsWith("generic")) {
            ROOT = "http://10.0.2.2:9000/api";
        }
        else {
            ROOT = "http://attable-inch.rhcloud.com/api";
        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(Api.class);
    }
}