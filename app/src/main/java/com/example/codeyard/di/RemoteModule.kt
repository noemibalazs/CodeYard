package com.example.codeyard.di

import com.example.codeyard.network.RandomAPI
import com.example.codeyard.remotedatasource.RemoteDataSource
import com.example.codeyard.remotedatasource.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(randomAPI: RandomAPI): RemoteDataSource =
        RemoteDataSourceImpl(randomAPI)
}