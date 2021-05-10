package com.example.pophop.adapterpinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pophop.data.local.entity.PopHopMovieEntity
import com.example.pophop.models.PopHopMovie
import com.example.pophop.utils.NetworkResult

class MoviePindingAdpter {
    companion object
    {
        @BindingAdapter("imgvisibilityapi","imgvisibilitedb",requireAll = true)
        @JvmStatic
        fun imgErrorVisibility(
                imgview:ImageView,
                apiResponse:NetworkResult<PopHopMovie>?,
                dbresponse:List<PopHopMovieEntity>?
        ) {

            if (apiResponse is NetworkResult.Error && dbresponse.isNullOrEmpty() )
            {
                imgview.visibility=View.VISIBLE
            }
            else if (apiResponse is NetworkResult.Loading)
            {
                imgview.visibility=View.INVISIBLE
            }
            else if (apiResponse is NetworkResult.OnSuccsess)
            {
                imgview.visibility=View.INVISIBLE
            }
        }

        @BindingAdapter("textvisibilityapi","textvisibilitedb",requireAll = true)
        @JvmStatic
        fun textErrorVisibility(
                textView: TextView,
                apiResponse:NetworkResult<PopHopMovie>?,
                dbresponse:List<PopHopMovieEntity>?
        ) {

            if (apiResponse is NetworkResult.Error && dbresponse.isNullOrEmpty() )
            {
                textView.text=apiResponse.massage
                textView.visibility=View.VISIBLE
            }
            else if (apiResponse is NetworkResult.Loading)
            {
                textView.visibility=View.INVISIBLE
            }
            else if (apiResponse is NetworkResult.OnSuccsess)
            {
                textView.visibility=View.INVISIBLE
            }
        }
    }
}