package com.greetty.appmanage.http.util;

import com.greetty.appmanage.app.Constants;
import com.greetty.appmanage.http.ApiStores;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Greetty on 2017/9/22.
 */

public class RetrofitUtil {

    private static final String BASE_URL = Constants.BASE_URL;

    private static Retrofit mRetrofit;
    private static ApiStores mApiStore;

    /**
     * 获取Retrofit示列
     *
     * @return Retrofit
     */
    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static ApiStores getApiStore(){
        if (mApiStore == null){
            mApiStore = getRetrofit().create(ApiStores.class);
        }
        return mApiStore;
    }

}
