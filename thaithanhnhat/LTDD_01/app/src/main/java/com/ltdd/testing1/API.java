package com.ltdd.testing1;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String mHost;
    private final OkHttpClient mClient;
    API(String host){
        mHost = host;
        mClient = new OkHttpClient();
        Log.d("API",mHost);
        Log.d("API",mClient.toString());
    }

    public String GET(String subUrl) throws IOException {
        String url = mHost + subUrl;
        Log.d("GET url:",url);
        Request request = new Request.Builder()
                                     .url(url)
                                     .build();
        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public String POST(String subUrl) throws IOException {
        String url = mHost + subUrl;
        Log.d("POST url:",url);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", null))
                .build();
        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public String POST(String subUrl,String jsonBody) throws IOException {
        String url = mHost + subUrl;
        Log.d("POST url:",url);
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public String POST(String subUrl, Map<String, String> mapHeader) throws IOException {
        String url = mHost + subUrl;
        Log.d("POST url:",url);
        Headers head = Headers.of(mapHeader);
        Request request = new Request.Builder()
                .url(url)
                .headers(head)
                .post(RequestBody.create("", null))
                .build();
        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String POST(String subUrl,String jsonBody, Map<String, String> mapHeader) throws IOException {
        String url = mHost + subUrl;
        Log.d("POST url:",url);
        Headers head = Headers.of(mapHeader);
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(url)
                .headers(head)
                .post(body)
                .build();
        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
}//public class API {
