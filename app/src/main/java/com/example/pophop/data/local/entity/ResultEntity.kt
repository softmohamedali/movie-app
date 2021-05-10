package com.example.pophop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pophop.models.Result
import com.example.pophop.utils.Constanse

@Entity(tableName = Constanse.FAV_MOVIE_TABLR_NAME)
class ResultEntity(
        @PrimaryKey(autoGenerate = true)
        var id:Int,
        var result:Result
) {
}