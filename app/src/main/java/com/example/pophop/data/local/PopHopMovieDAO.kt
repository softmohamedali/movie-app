package com.example.pophop.data.local

import androidx.room.*
import com.example.pophop.data.local.entity.PopHopMovieEntity
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface PopHopMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopHopMovie(popHopMovieEntity: PopHopMovieEntity)

    @Query("SELECT * FROM popHopMovieTable ORDER BY id ASC")
    fun getPopHopMovie():Flow<List<PopHopMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritMovie(movie:ResultEntity)

    @Query("SELECT * FROM favmovie ORDER BY id ASC")
    fun getFavMovies():Flow<List<ResultEntity>>

    @Delete
    suspend fun deletefavMovie(result: ResultEntity)

    @Query("DELETE FROM favmovie")
    suspend fun deleteAllFav()
}