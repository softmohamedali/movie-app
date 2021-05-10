package com.example.pophop.data

import com.example.pophop.data.remote.MovieApi
import com.example.pophop.models.PopHopMovie
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val movieApi: MovieApi
) {

    suspend fun getMovies(queri:Map<String,String>):Response<PopHopMovie>
    {
        return movieApi.getMovies(queri)
    }


    suspend fun searchMovies(searchQueri:Map<String,String>):Response<PopHopMovie>
    {
        return movieApi.searchMovies(searchQueri)
    }
}