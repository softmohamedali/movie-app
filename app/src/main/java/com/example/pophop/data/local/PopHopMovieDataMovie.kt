package com.example.pophop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pophop.data.local.entity.PopHopMovieEntity
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.Result

@Database(
        entities = [PopHopMovieEntity::class,ResultEntity::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(PopHopMovieTypeConverter::class)
abstract class PopHopMovieDataMovie :RoomDatabase(){
    abstract fun  getDao():PopHopMovieDAO
}