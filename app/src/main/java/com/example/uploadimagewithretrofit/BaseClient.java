package com.example.uploadimagewithretrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {

    private final static String BaseUrl = "https://joshnawebtech.000webhostapp.com/api1/";

    private static Retrofit retrofitEndPoint = null;


    public static Retrofit getBaseClient() {
        if (retrofitEndPoint == null) {
//
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();

            retrofitEndPoint = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitEndPoint;
    }
}
