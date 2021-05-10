package com.example.pophop.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.pophop.utils.NetworkResult
import com.example.pophop.data.MoviesRepo
import com.example.pophop.data.local.entity.PopHopMovieEntity
import com.example.pophop.data.local.entity.ResultEntity
import com.example.pophop.models.PopHopMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
        private val moviesRepo: MoviesRepo,
        application: Application
):AndroidViewModel(application) {


    //local
    var readDataBase:LiveData<List<PopHopMovieEntity>> =moviesRepo.local.getPopHopMovie().asLiveData()

    var readFavRecepe:LiveData<List<ResultEntity>> =moviesRepo.local.getFavMovie().asLiveData()

    fun insertMoviesdb(popohopMovieEntity: PopHopMovieEntity){
        viewModelScope.launch (Dispatchers.IO){
            moviesRepo.local.insertPopHopMovies(popohopMovieEntity)
        }

    }

    fun insertFavMovie(movie:ResultEntity)
    {
        viewModelScope.launch (Dispatchers.IO){
            moviesRepo.local.insertFavMovie(movie)
        }
    }

    fun deleteFavMovie(movie:ResultEntity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepo.local.deletFavMovie(movie)
        }
    }
    fun deleteAllFavMovie(){
        viewModelScope.launch (Dispatchers.IO){
            moviesRepo.local.deleteAllFav()
        }
    }

    

    //remote
    var netWorkresponse:MutableLiveData<NetworkResult<PopHopMovie>> = MutableLiveData()

    var searchResponse:MutableLiveData<NetworkResult<PopHopMovie>> = MutableLiveData()

    fun getMovies(query:Map<String,String>)
    {
        viewModelScope.launch {
            safeCallGetMovies(query)
        }
    }


    fun getSearchMovie(searchQurer:HashMap<String,String>)
    {
        viewModelScope.launch {
           safeCallGetSearchMovies(searchQurer)
        }
    }

    private suspend fun safeCallGetSearchMovies(searchQurer: java.util.HashMap<String, String>) {
        searchResponse.value=NetworkResult.Loading()
        if (hasInternetConnection())
        {
            try {
                val response=moviesRepo.remote.searchMovies(searchQurer)
                searchResponse.value=hundleResponse(response)
            }catch (e:Exception)
            {
                searchResponse.value=NetworkResult.Error(e.message.toString())
            }
        }else{
            searchResponse.value=NetworkResult.Error("no internet connection")
        }

    }


    private suspend fun safeCallGetMovies(query: Map<String, String>) {
        netWorkresponse.value= NetworkResult.Loading()
        if (hasInternetConnection())
        {
            try {
                val response=moviesRepo.remote.getMovies(query)
                netWorkresponse.value=hundleResponse(response)
                
                val entityresponse=netWorkresponse.value!!.data
                if (entityresponse!=null)
                {
                    insertMoviesdb(PopHopMovieEntity(entityresponse))
                }
            }catch (e:Exception)
            {
                netWorkresponse.value= NetworkResult.Error(e.message.toString())
            }
        }
        else{
            netWorkresponse.value= NetworkResult.Error("No Internet Connection")
        }
    }


    private fun hundleResponse(response: Response<PopHopMovie>): NetworkResult<PopHopMovie> {
        if (response.isSuccessful) {
            return NetworkResult.OnSuccsess(response.body()!!)
        }else if(response.code()==404)
        {
            return NetworkResult.Error("Api key Is Limited")
        }else if (response.body()?.results.isNullOrEmpty()){
            return NetworkResult.Error("Movies Not found")
        }else if(response.message().contains("Time out"))
        {
            return NetworkResult.Error("time out ")
        } else{
            return NetworkResult.Error("Movies Not founds")
        }

    }

    private fun hasInternetConnection():Boolean
    {
        val connectivityManger=getApplication<Application>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?:return false
        val networkCapability=connectivityManger.getNetworkCapabilities(netWorkActive) ?:return false
        when{
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else->return false
        }

    }
}