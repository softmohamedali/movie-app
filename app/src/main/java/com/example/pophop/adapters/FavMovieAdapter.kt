package com.example.pophop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.databinding.FavMovieRecyRowBinding
import com.example.pophop.utils.MyDiffUtil

class FavMovieAdapter:RecyclerView.Adapter<FavMovieAdapter.MyfavVH>() {

    var favMovie= emptyList<ResultEntity>()
    class MyfavVH(var binding:FavMovieRecyRowBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(favEntity:ResultEntity)
        {
            binding.resultEntity=favEntity
            binding.executePendingBindings()
        }

        companion object
        {
            fun from(view:ViewGroup):MyfavVH
            {
                val bind=FavMovieRecyRowBinding.inflate(LayoutInflater.from(view.context),view,false)
                return MyfavVH(bind)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyfavVH {
        return MyfavVH.from(parent)
    }

    override fun onBindViewHolder(holder: MyfavVH, position: Int) {
        holder.bind(favMovie[position])
    }

    override fun getItemCount(): Int {
        return favMovie.size
    }

    fun setData(result:List<ResultEntity>)
    {
        val myDiffUtil=MyDiffUtil<ResultEntity>(favMovie,result)
        val res=DiffUtil.calculateDiff(myDiffUtil)
        favMovie=result
        res.dispatchUpdatesTo(this)

    }
}