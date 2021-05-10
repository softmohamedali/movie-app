package com.example.pophop

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.pophop.utils.Constanse
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStroeRepo @Inject constructor(@ApplicationContext context: Context) {

    private object Prefrenceskey
    {
        var sortBy= stringPreferencesKey(Constanse.SORTBY_KEY)
        var sortById= intPreferencesKey(Constanse.SORTBY_IDKEY)
    }

    private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(Constanse.DATASTORE_NAME)
    val preferences=context.dataStore

   suspend fun saveDataStroe(sortby:String,sortbyid:Int)
    {
        preferences.edit {
            it[Prefrenceskey.sortBy]=sortby
            it[Prefrenceskey.sortById]=sortbyid
        }
    }

    var readDataStore:Flow<SortBy> = preferences.data
        .catch {it->
            if (it is IOException)
            {
                emit(emptyPreferences())
            }
            else{
                throw it
            }
        }
        .map {
            val sortby=it[Prefrenceskey.sortBy] ?:Constanse.DEAFULT_CHIP_
            val sortbyid=it[Prefrenceskey.sortById] ?:0
            SortBy(sortby,sortbyid)
        }
}

data class SortBy(
    var sortby:String,
    var sortbyid:Int
)