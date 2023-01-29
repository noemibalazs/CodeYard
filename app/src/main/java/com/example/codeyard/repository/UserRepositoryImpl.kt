package com.example.codeyard.repository

import com.example.codeyard.User
import com.example.codeyard.remotedatasource.RemoteDataSource
import com.example.codeyard.toUser
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {


    override fun getUsers(results: Int): Single<List<User>> =
        Single.just(true)
            .flatMap {
                remoteDataSource.getRandomUsers(results)
            }
            .map { remoteUsers -> remoteUsers.map { it.toUser() } }
}
