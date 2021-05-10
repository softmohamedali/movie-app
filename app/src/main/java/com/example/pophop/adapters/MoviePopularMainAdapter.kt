package com.example.pophop.adapters

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pophop.databinding.MovieTopRatedRowRecyBinding
import com.example.pophop.models.PopHopMovie
import com.example.pophop.models.Result
import com.example.pophop.utils.MyDiffUtil

class MoviePopularMainAdapter:RecyclerView.Adapter<MoviePopularMainAdapter.MyViewHolder>() {

    var movies= ArrayList<Result>()

    class MyViewHolder(private val viewBinding:MovieTopRatedRowRecyBinding):RecyclerView.ViewHolder(viewBinding.root) {

        fun binding(result: Result)
        {
            viewBinding.result=result
            viewBinding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder
            {
                val view=MovieTopRatedRowRecyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return MyViewHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    fun setData(result: List<Result>)
    {
        val diffUtil = MyDiffUtil<Result>(movies,result)
        val diffresult=DiffUtil.calculateDiff(diffUtil)
        movies.addAll(result)
        diffresult.dispatchUpdatesTo(this)
    }

}