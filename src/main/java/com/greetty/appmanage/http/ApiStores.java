package com.greetty.appmanage.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Greetty on 2017/9/22.
 */

public interface ApiStores {

    @GET("AppManage/Splash/splash_bg.jpg")
    Call<ResponseBody> downloadSplashImage();

}
