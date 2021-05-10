package com.example.pophop.ui.fragments.favorit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pophop.R
import com.example.pophop.adapters.FavMovieAdapter
import com.example.pophop.adapters.MoviePopularMainAdapter
import com.example.pophop.databinding.FragmentFavoritBinding
import com.example.pophop.models.Result
import com.example.pophop.viewmodels.MainViewModels
import com.example.pophop.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModels>()

    private var _binding:FragmentFavoritBinding?=null
    private val binding get() = _binding!!

    val mAdapter by lazy { FavMovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFavoritBinding.inflate(inflater)
        binding.lifecycleOwner=viewLifecycleOwner

        setUpRecy()
        getFavMovie()

        return binding.root
    }

    private fun getFavMovie()
    {
        val list=ArrayList<Result>()
        mainViewModel.readFavRecepe.observe(viewLifecycleOwner,{

            mAdapter.setData(it)

        })

    }

    private fun setUpRecy()
    {
        binding.favoritRecy.adapter=mAdapter
        binding.favoritRecy.layoutManager=GridLayoutManager(requireActivity(),3)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}