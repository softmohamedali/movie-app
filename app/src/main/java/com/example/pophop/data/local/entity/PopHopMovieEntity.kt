package com.example.pophop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pophop.models.PopHopMovie
import com.example.pophop.utils.Constanse

@Entity(tableName = Constanse.POP_HOP_MOVIE_TABLR_NAME)
class PopHopMovieEntity (var popHopMovie:PopHopMovie){
    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}