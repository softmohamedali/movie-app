package com.example.pophop.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class PopHopMovie(
    @SerializedName("results")
    val results: List<Result>
)