package com.example.pophop.data.remote

import com.example.pophop.models.PopHopMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovies(
            @QueryMap query:Map<String,String>
    ):Response<PopHopMovie>

    @GET("search/movie")
    suspend fun searchMovies(
            @QueryMap searchquery:Map<String,String>
    ):Response<PopHopMovie>


}