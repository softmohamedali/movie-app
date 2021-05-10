package com.example.pophop.ui.fragments.info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pophop.R
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.Result
import com.example.pophop.utils.Constanse
import com.example.pophop.viewmodels.MainViewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*
import java.lang.Exception

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private var isSave=false
    private var movieid=0

    private val navargs by navArgs<InfoFragmentArgs>()

    private val mainViewModel by viewModels<MainViewModels>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val vieww= inflater.inflate(R.layout.fragment_info, container, false)

        setHasOptionsMenu(true)
        val result=navargs.result


        cheakIfMovieSaved(vieww.img_addfav_info_frag)

        vieww.img_backposter_info_frag.load(Constanse.BASE_IMG_URL+result.backdropPath)
        vieww.img_poster_info_frag.load(Constanse.BASE_IMG_URL+result.posterPath)
        vieww.tv_title_info_frag.text=result.title
        vieww.tv_adult_info_frag.text=result.adult.toString()
        vieww.tv_date_info_frag.text= result.releaseDate.toString()
        vieww.tv_overView_info_frag.text=result.overview
        vieww.rating_vote_info_frag.rating=((result.voteAverage)/2).toFloat()
        vieww.tv_rating_info_frag.text=(result.voteAverage).toString()
        vieww.img_addfav_info_frag.setOnClickListener {
            if (isSave)
            {
                deleteMovie(vieww.img_addfav_info_frag)
            }
            else
            {
                saveMovie(vieww.img_addfav_info_frag)
            }

        }

        return vieww
    }

    private fun deleteMovie(img: ImageView?) {
        val movieEntity=ResultEntity(movieid,navargs.result)
        mainViewModel.deleteFavMovie(movieEntity)
        changeItemColor(img,R.color.white)
        showSnakbar("success delete")
        isSave=false
    }

    private fun cheakIfMovieSaved(img: ImageView?) {
        mainViewModel.readFavRecepe.observe(viewLifecycleOwner,{
            try {
                for (movie in it){
                    if (movie.result.id==navargs.result.id)
                    {
                        isSave=true
                        movieid=movie.id
                        changeItemColor(img,R.color.red)
                    }
                    else{
                        changeItemColor(img,R.color.white)
                    }
                }
            }catch (e:Exception){
                Log.d("my",e.message.toString())
            }
        })
    }

    private fun saveMovie(img: ImageView?) {
        val movieEntity=ResultEntity(0,navargs.result)
        mainViewModel.insertFavMovie(movieEntity)
        changeItemColor(img,R.color.red)
        showSnakbar("success save")
        isSave=true
    }

    private fun changeItemColor(img: ImageView?,color:Int) {
        img?.setColorFilter(ContextCompat.getColor(requireActivity(),color))
    }

    private fun showSnakbar(text:String)
    {
        Snackbar.make(info_layout,text,Snackbar.LENGTH_SHORT).setAction("ok"){}.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home )
        {
            findNavController().navigate(R.id.action_infoFragment_to_moviesFragment)


        }
        return super.onOptionsItemSelected(item)
    }


}