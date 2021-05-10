package com.example.pophop.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pophop.DataStroeRepo
import com.example.pophop.utils.Constanse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    application: Application,
    var datastoreRepo:DataStroeRepo
):AndroidViewModel(application) {

    var connection=false

    var sortby=Constanse.DEAFULT_CHIP_
    var sortbyid=0
    var readDataStore=datastoreRepo.readDataStore

    fun  saveDataStore(sortby:String,sortbyid:Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepo.saveDataStroe(sortby, sortbyid)
        }
    }

    fun appluQueri(page:String):HashMap<String,String> {

        viewModelScope.launch(Dispatchers.IO) {
            readDataStore.collect {
                sortby=it.sortby
                sortbyid=it.sortbyid
            }
        }

        //?sort_by=popularity.desc&api_key=eda7f4950b80d3db76c7866cde21acae
        val map=HashMap<String,String>()
        map[Constanse.QUERY_API_KEY]= Constanse.API_KEY
        map[Constanse.QUERY_SORT_BY]=sortby
        map["page"]=page

        Log.d("my","-----${sortby}-----${page}")

        return map
    }

    fun applySearchQuery(text:String,page:String):HashMap<String,String>
    {
        val query=HashMap<String,String>()
        query[Constanse.QUERY_API_KEY]=Constanse.API_KEY
        query[Constanse.QUERY_SEARCH_QUERY]=text
        query["page"]=page
        return query
    }


    fun showSatausAction()
    {
        if (!connection)
        {
            Toast.makeText(getApplication(),"no internet Connection",Toast.LENGTH_SHORT).show()
        }
    }


}