package org.crayzer.nio;

import okhttp3.*;

import java.io.IOException;

public class OkHttpHelper {

    private static OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static void main(String[] args) throws IOException {
        String url = "https://www.baidu.com/";
        String text = OkHttpHelper.get(url);
        System.out.println("url: " + url + " ; response: \n" + text);

        // 清理资源
        OkHttpHelper.client = null;
    }

    private static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



}
