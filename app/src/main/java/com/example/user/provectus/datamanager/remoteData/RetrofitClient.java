package com.example.user.provectus.datamanager.remoteData;

import com.example.user.provectus.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitClient {
    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if(ourInstance == null){
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        Timber.d("retrofit clien is created");
        return ourInstance;
    }

    private RetrofitClient() {
    }
}
