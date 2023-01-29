package com.example.codeyard.repository

import com.example.codeyard.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun getUsers(results: Int): Single<List<User>>
}