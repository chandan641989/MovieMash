package com.kartnap.chandan.moviemash.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chandan on 8/7/2017.
 */

public class Client {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static Retrofit retrofit;
    public static  Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
