package com.example.pophop.data

import com.example.pophop.data.local.PopHopMovieDAO
import com.example.pophop.data.local.entity.PopHopMovieEntity
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
        var dao: PopHopMovieDAO
) {

    suspend fun insertPopHopMovies(popHopMovieEntitiy: PopHopMovieEntity) {
        dao.insertPopHopMovie(popHopMovieEntitiy)
    }


    fun getPopHopMovie():Flow<List<PopHopMovieEntity>> {
        return dao.getPopHopMovie()
    }

    suspend fun  insertFavMovie(movie:ResultEntity)
    {
        dao.insertFavoritMovie(movie)
    }

    fun getFavMovie():Flow<List<ResultEntity>>
    {
        return dao.getFavMovies()
    }

    suspend fun deletFavMovie(movie: ResultEntity)
    {
        dao.deletefavMovie(movie)
    }

    suspend fun deleteAllFav()
    {
        dao.deleteAllFav()
    }
}