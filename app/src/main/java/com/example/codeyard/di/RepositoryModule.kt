package com.example.codeyard.di

import com.example.codeyard.remotedatasource.RemoteDataSource
import com.example.codeyard.repository.UserRepository
import com.example.codeyard.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: RemoteDataSource): UserRepository =
        UserRepositoryImpl(remoteDataSource)
}