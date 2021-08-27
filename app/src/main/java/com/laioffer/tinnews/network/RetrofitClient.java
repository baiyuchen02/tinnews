package com.laioffer.tinnews.network;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_KEY = "4a9bc1419fcd44cf8d9eeef65adf2515";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    // API generation
    public static Retrofit newInstance() {
        // OkHttpClient is exactly the same as the ApacheClient in the job recommendation project
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
