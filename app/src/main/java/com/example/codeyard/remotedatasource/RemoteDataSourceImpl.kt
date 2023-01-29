package com.example.codeyard.remotedatasource

import com.example.codeyard.network.RandomAPI
import com.example.codeyard.RemoteUser
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val randomAPI: RandomAPI
) : RemoteDataSource {

    override fun getRandomUsers(results: Int): Single<List<RemoteUser>> =
        Single.just(true)
            .flatMap {
                randomAPI.getRandomUser(results)
            }
            .map { it.results }

}