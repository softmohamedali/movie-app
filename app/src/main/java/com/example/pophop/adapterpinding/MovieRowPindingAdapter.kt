package com.example.pophop.adapterpinding

import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.pophop.R
import com.example.pophop.models.Result
import com.example.pophop.ui.fragments.favorit.FavoritFragmentDirections
import com.example.pophop.ui.fragments.movie.MoviesFragmentDirections
import com.example.pophop.utils.Constanse
import java.lang.Exception

class MovieRowPindingAdapter {

    companion object{

        @BindingAdapter("setresult",requireAll = true)
        @JvmStatic
        fun setonclickRow(movieRow:ConstraintLayout,result:Result)
        {
            try {
                movieRow.setOnClickListener {
                    val action=MoviesFragmentDirections.actionMoviesFragmentToInfoFragment(result)
                    movieRow.findNavController().navigate(action)

                }
            }catch (e:Exception)
            {
                Log.d("mylog",e.message.toString())
            }
        }

        @BindingAdapter("setresultfav",requireAll = true)
        @JvmStatic
        fun setonclickRowfav(movieRow:ConstraintLayout,result:Result)
        {
            try {
                movieRow.setOnClickListener {
                    val action=FavoritFragmentDirections.actionFavoritFragmentToInfoFragment(result)
                    movieRow.findNavController().navigate(action)

                }
            }catch (e:Exception)
            {
                Log.d("mylog",e.message.toString())
            }
        }


        @BindingAdapter("setvote",requireAll = true)
        @JvmStatic
        fun setvote(textview:TextView,vote:Double)
        {
            textview.text=vote.toString()
        }

        @BindingAdapter("setstarsnum",requireAll = true)
        @JvmStatic
        fun setStarsNum(rating:RatingBar,vote:Double)
        {
            rating.rating=(vote/2).toFloat()
        }

        @BindingAdapter("setimg",requireAll = true)
        @JvmStatic
        fun setImg(img:ImageView,url:String?)
        {
            if(!url.isNullOrEmpty())
            {
                img.load(Constanse.BASE_IMG_URL+url)
                {
                    this.crossfade(600)
                    this.error(R.drawable.ic_imgerror)
                }
            }
            if(url.isNullOrEmpty())
            {
                img.load(R.drawable.ic_imgerror)
                {
                    this.crossfade(600)
                    this.error(R.drawable.ic_imgerror)
                }
            }

        }
    }
}