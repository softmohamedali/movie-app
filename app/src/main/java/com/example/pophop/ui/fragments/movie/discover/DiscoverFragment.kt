package com.example.pophop.ui.fragments.movie.discover

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.pophop.R
import com.example.pophop.SortBy
import com.example.pophop.ui.fragments.movie.MoviesFragmentDirections
import com.example.pophop.utils.Constanse
import com.example.pophop.viewmodels.MovieViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_discover.view.*
import java.lang.Exception

@AndroidEntryPoint
class DiscoverFragment : BottomSheetDialogFragment() {

    lateinit var moviewViewModel: MovieViewModel
    private var sortby=Constanse.DEAFULT_CHIP_
    private var sortbyid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviewViewModel=ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_discover, container, false)

        moviewViewModel.readDataStore.asLiveData().observe(viewLifecycleOwner,{
            updatechip(it,view.chipgroup_info_frag)
        })

        view.chipgroup_info_frag.setOnCheckedChangeListener{ group,id ->
            val chip=group.findViewById<Chip>(id)
            sortby=chip.resources.getResourceEntryName(id)
            Toast.makeText(requireActivity(),sortby,Toast.LENGTH_SHORT).show()
            sortbyid=id
        }

        view.btn_apply_info_frag.setOnClickListener {
            moviewViewModel.saveDataStore(sortby,sortbyid)
            val action=DiscoverFragmentDirections.actionDiscoverFragmentToMoviesFragment(true)
            findNavController().navigate(action)
        }

        return view
    }

    private fun updatechip(it: SortBy?, chipgroupInfoFrag: ChipGroup?) {
        try {
            if(it?.sortbyid !=0)
            {
                chipgroupInfoFrag?.findViewById<Chip>(it!!.sortbyid)?.isChecked=true

            }
        }catch (e:Exception)
        {
            Log.d("my",e.message.toString())
        }

    }


}