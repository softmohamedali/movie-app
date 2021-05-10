package com.example.pophop.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesRepo @Inject constructor(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
) {

    var remote=remoteDataSource
    var local=localDataSource
}