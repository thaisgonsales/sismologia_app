package com.aiep.simologia_app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gael.cloud/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
