package com.example.pophop.data.local

import androidx.room.TypeConverter
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.PopHopMovie
import com.example.pophop.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PopHopMovieTypeConverter {
    val json=Gson()

    @TypeConverter
    fun popHopMoviewToJson(popHopMovie: PopHopMovie):String
    {
        return json.toJson(popHopMovie)
    }

    @TypeConverter
    fun jsonToPopHopMovie(string: String):PopHopMovie{
        val typeList=object :TypeToken<PopHopMovie>(){}.type
        return json.fromJson(string,typeList)
    }

    @TypeConverter
    fun resultToJson(Movie: Result):String
    {
        return json.toJson(Movie)
    }

    @TypeConverter
    fun jsonToResult(string: String):Result{
        val typeList=object :TypeToken<Result>(){}.type
        return json.fromJson(string,typeList)
    }

}