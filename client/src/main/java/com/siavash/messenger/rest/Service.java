package com.siavash.messenger.rest;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sia on 6/25/16.
 */
public class Service {
    private static OkHttpClient createClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(loggingInterceptor);
        return client.build();
    }

    public static <T> T createService(Class<T> serviceClass, String baseUrl) {
        OkHttpClient client = createClient();

        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(serviceClass);
    }

    public static <T> T createService(Class<T> serviceClass, HttpUrl baseUrl) {
        OkHttpClient client = createClient();

        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(serviceClass);
    }
}
