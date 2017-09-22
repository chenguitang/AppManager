package com.greetty.appmanage.http;

import com.greetty.appmanage.http.util.RetrofitUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Greetty on 2017/9/22.
 * <p>
 * HttpClient
 */

public class HttpClient extends RetrofitUtil {

    private static HttpClient mHttpClient;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (mHttpClient == null) {
            mHttpClient = new HttpClient();
        }
        return mHttpClient;
    }

    public Call<ResponseBody> downloadSplashImage() {
        Call<ResponseBody> call = getApiStore().downloadSplashImage();
        return call;
    }


}
