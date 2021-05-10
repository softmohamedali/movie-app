package com.example.pophop.ui.fragments.movie

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pophop.R
import com.example.pophop.adapters.MoviePopularMainAdapter
import com.example.pophop.databinding.FragmentMoviesBinding
import com.example.pophop.utils.NetWorkListener
import com.example.pophop.utils.NetworkResult
import com.example.pophop.utils.observerOnce
import com.example.pophop.viewmodels.MainViewModels
import com.example.pophop.viewmodels.MovieViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding:FragmentMoviesBinding?=null

    private val binding get() = _binding!!

    private val mAdpterPopular by lazy {  MoviePopularMainAdapter()}


    private val args by navArgs<MoviesFragmentArgs>()

    lateinit var mainViewModel:MainViewModels

    lateinit var movieViewModel: MovieViewModel

    private var currentPage=1

    lateinit var netWorkListener: NetWorkListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModels::class.java)
        movieViewModel=ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMoviesBinding.inflate(inflater, container, false)
        binding.viewmodel=mainViewModel

        setHasOptionsMenu(true)

        setUpRecycle()

        readDataBase()

        lifecycleScope.launch {
            netWorkListener= NetWorkListener()
            netWorkListener.cheakIsConnect(requireContext()).collect {
                movieViewModel.connection=it
                movieViewModel.showSatausAction()
            }
        }

        binding.recyPopularHomeFrag.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(requireActivity(), "Last", Toast.LENGTH_LONG).show()
                    currentPage++
                    movieViewModel.appluQueri(currentPage.toString())
                    requestMovie()
                    

                }
            }
        })
        binding.fbSortMovieFrag.setOnClickListener {
            if (movieViewModel.connection)
            {
                findNavController().navigate(R.id.action_moviesFragment_to_discoverFragment)
            }
            else
            {
                movieViewModel.showSatausAction()
            }

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_menu,menu)

        val search=menu.findItem(R.id.search_menu_movie_frag)
        val searchView=search.actionView as SearchView

        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null && query.isNotBlank())
                {
                    requestSearchMovie(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


    fun readDataBase()
    {
        currentPage=1
        mainViewModel.readDataBase.observerOnce(viewLifecycleOwner, { db ->
            if (db.isNotEmpty() && !(args.backfromdiscover)) {
                Log.d("mylog", "request data from db")
                mAdpterPopular.setData(db[0].popHopMovie.results)
                hideShimmer(binding.recyPopularHomeFrag)
            } else {
                requestMovie()
            }
        })
    }


    fun requestMovie() {
        Log.d("mylog", "request data from api")
        mainViewModel.getMovies(movieViewModel.appluQueri(currentPage.toString()))
        mainViewModel.netWorkresponse.observe(viewLifecycleOwner, {
            if (it is NetworkResult.Error) {
                Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                hideShimmer(binding.recyPopularHomeFrag)
                loadcashMovieDB()
            } else if (it is NetworkResult.Loading) {
                showShimmer(binding.recyPopularHomeFrag)
            } else if (it is NetworkResult.OnSuccsess) {
                mAdpterPopular.setData(it.data!!.results)
                hideShimmer(binding.recyPopularHomeFrag)
            }
        })
    }

    fun requestSearchMovie(text:String)
    {
        currentPage=1
        Log.d("mylog", "request data from search")
        mainViewModel.getSearchMovie(movieViewModel.applySearchQuery(text,currentPage.toString()))
        mainViewModel.searchResponse.observe(viewLifecycleOwner,{
            if (it is NetworkResult.Error) {
                Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                hideShimmer(binding.recyPopularHomeFrag)
            } else if (it is NetworkResult.Loading) {
                showShimmer(binding.recyPopularHomeFrag)
            } else if (it is NetworkResult.OnSuccsess) {
                mAdpterPopular.setData(it.data!!.results)
                hideShimmer(binding.recyPopularHomeFrag)
            }
        })

    }


    fun loadcashMovieDB()
    {
        mainViewModel.readDataBase.observe(viewLifecycleOwner, { db ->
            if (db.isNotEmpty()) {
                Log.d("mylog", "request data from db")
                mAdpterPopular.setData(db[0].popHopMovie.results)
                hideShimmer(binding.recyPopularHomeFrag)
            }

        })
    }



    private fun setUpRecycle() {
        binding.recyPopularHomeFrag.adapter=mAdpterPopular
        val span =3
        binding.recyPopularHomeFrag.layoutManager=GridLayoutManager(requireActivity(), span)
        showShimmer(binding.recyPopularHomeFrag)
    }



    private fun showShimmer(recyclerView: ShimmerRecyclerView) {

        recyclerView.showShimmer()

    }

    private fun hideShimmer(recyclerView: ShimmerRecyclerView) {

        recyclerView.hideShimmer()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}