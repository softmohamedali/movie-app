package com.example.pophop.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pophop.data.local.PopHopMovieDataMovie
import com.example.pophop.utils.Constanse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDataBase(
            @ApplicationContext context:Context
    )=Room.databaseBuilder(
            context,
            PopHopMovieDataMovie::class.java,
            Constanse.DATA_BASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(popHopMovieDataMovie: PopHopMovieDataMovie)=
            popHopMovieDataMovie.getDao()

}