package com.example.codeyard

import com.example.codeyard.network.RandomAPI
import com.example.codeyard.remotedatasource.RemoteDataSource
import com.example.codeyard.remotedatasource.RemoteDataSourceImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceImplTest {

    @Mock
    private lateinit var api: RandomAPI

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSourceImpl(api)
    }

    @Test(expected = NullPointerException::class)
    fun `test get remote users should be successful`() {
        val results = 12
        val result: RemoteResults = mock()
        val users: List<RemoteUser> = mock()

        whenever(api.getRandomUser(results)).thenReturn(Single.just(result))

        remoteDataSource.getRandomUsers(results)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == users }

        verify(api, times(1)).getRandomUser(results)
    }

    @Test
    fun `test get remote users should failed`() {
        val results = 12
        val exception: Exception = mock()

        whenever(api.getRandomUser(results)).thenReturn(Single.error(exception))

        remoteDataSource.getRandomUsers(results)
            .test()
            .assertNotComplete()
            .assertNoValues()
            .assertError(exception)

        verify(api, times(1)).getRandomUser(results)
    }
}