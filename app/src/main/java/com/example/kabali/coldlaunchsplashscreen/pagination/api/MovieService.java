package com.example.kabali.coldlaunchsplashscreen.pagination.api;

import com.example.kabali.coldlaunchsplashscreen.pagination.models.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {


    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int pageIndex);
}
