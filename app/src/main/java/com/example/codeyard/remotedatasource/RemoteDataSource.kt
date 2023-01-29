package com.example.codeyard.remotedatasource

import com.example.codeyard.RemoteUser
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun getRandomUsers(results: Int): Single<List<RemoteUser>>
}