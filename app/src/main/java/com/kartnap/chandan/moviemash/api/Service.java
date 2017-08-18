package com.kartnap.chandan.moviemash.api;

import com.kartnap.chandan.moviemash.model.Movie;
import com.kartnap.chandan.moviemash.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Chandan on 8/7/2017.
 */

public interface Service {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovie(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovie(@Query("api_key") String apikey);

}
