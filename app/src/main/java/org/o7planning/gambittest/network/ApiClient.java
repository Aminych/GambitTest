package org.o7planning.gambittest.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://api.gambit-app.ru/category/";

    public static Retrofit retrofit = null;

    public static final String API_KEY = "3";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static ApiClient getApiService() {
        return getClient().create(ApiClient.class);
    }
}