package com.moxydemo.data.network;

import com.moxydemo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String ACCEPT = "application/json";

    public static final int TIMEOUT = 30;

    public static final String DEBUG_API_BASE_URL = "https://api.myjson.com/bins/";

    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder builder = getBuilder();

    public <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(logging);

        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }

    public Retrofit getRetrofit() {
        if (retrofit != null)
            return retrofit;
        return builder.client(httpClient.build()).build();
    }

    private Retrofit.Builder getBuilder() {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (BuildConfig.DEBUG) {
            builder.baseUrl(DEBUG_API_BASE_URL);
        }
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }
}
