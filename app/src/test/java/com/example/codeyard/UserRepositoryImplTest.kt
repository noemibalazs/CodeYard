package com.example.codeyard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.codeyard.remotedatasource.RemoteDataSource
import com.example.codeyard.repository.UserRepository
import com.example.codeyard.repository.UserRepositoryImpl
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRepositoryImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test get users and should be successful`() {
        val remoteUser: RemoteUser = mockk()
        val user: User = mockk()
        val users = listOf(user)
        val userName: UserName = mockk()
        val userStreet: UserStreet = mockk()
        val userPicture: UserPicture = mockk()
        val userLocation: UserLocation = mockk()
        val email = "email"
        val phoneNumber = "1234"
        val mobileNumber = "1234"

        every { remoteUser.email } returns email
        every { remoteUser.phoneNumber } returns phoneNumber
        every { remoteUser.mobileNumber } returns mobileNumber
        mockkStatic(RemoteUserName::toUserName)
        every { any<RemoteUserName>().toUserName() } returns userName
        mockkStatic(RemoteUserLocation::toUserLocation)
        every { any<RemoteUserLocation>().toUserLocation() } returns userLocation
        mockkStatic(RemoteUserStreet::toUserStreet)
        every { any<RemoteUserStreet>().toUserStreet() } returns userStreet
        mockkStatic(RemoteUserPicture::toUUserPicture)
        every { any<RemoteUserPicture>().toUUserPicture() } returns userPicture
        mockkStatic(RemoteUser::toUser)
        every { any<RemoteUser>().toUser() } returns user

        every { remoteDataSource.getRandomUsers(any()) } returns Single.just(listOf(remoteUser))

        userRepository.getUsers(12)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == users }

        verify(exactly = 1) { remoteDataSource.getRandomUsers(any()) }
    }

    @Test
    fun `test get users and should failed`() {
        val remoteUser: RemoteUser = mockk()
        val user: User = mockk()
        val users = listOf(user)
        val userName: UserName = mockk()
        val userStreet: UserStreet = mockk()
        val userPicture: UserPicture = mockk()
        val userLocation: UserLocation = mockk()
        val email = "email"
        val phoneNumber = "1234"
        val mobileNumber = "1234"

        val exception: Exception = mockk()

        every { remoteUser.email } returns email
        every { remoteUser.phoneNumber } returns phoneNumber
        every { remoteUser.mobileNumber } returns mobileNumber
        mockkStatic(RemoteUserName::toUserName)
        every { any<RemoteUserName>().toUserName() } returns userName
        mockkStatic(RemoteUserLocation::toUserLocation)
        every { any<RemoteUserLocation>().toUserLocation() } returns userLocation
        mockkStatic(RemoteUserStreet::toUserStreet)
        every { any<RemoteUserStreet>().toUserStreet() } returns userStreet
        mockkStatic(RemoteUserPicture::toUUserPicture)
        every { any<RemoteUserPicture>().toUUserPicture() } returns userPicture
        mockkStatic(RemoteUser::toUser)
        every { any<RemoteUser>().toUser() } returns user

        every { remoteDataSource.getRandomUsers(any()) } returns Single.error(exception)

        userRepository.getUsers(12)
            .test()
            .assertNoValues()
            .assertNotComplete()
            .assertError(exception)

        verify(exactly = 1) { remoteDataSource.getRandomUsers(any()) }
    }
}