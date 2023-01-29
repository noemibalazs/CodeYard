package com.example.codeyard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.codeyard.contactlist.ContactListViewModel
import com.example.codeyard.contactlist.ContactListViewModel.ViewState
import com.example.codeyard.repository.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @get:Rule
    var testScheduler = RxJavaSchedulerImmediateRule()

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var stateObserver: Observer<ViewState>

    @MockK
    private lateinit var usersObserver: Observer<List<User>>

    private lateinit var viewModel: ContactListViewModel

    @Before
    fun setUp() {
        viewModel = ContactListViewModel(userRepository)
        viewModel.viewState.observeForever(stateObserver)
        viewModel.users.observeForever(usersObserver)
    }

    @Test
    fun `test get users should be successful`() {
        val user: User = mockk()
        val users = listOf(user)

        val stateCaptor = slot<ViewState>()
        val usersCaptor = slot<List<User>>()

        every { userRepository.getUsers(any()) } returns Single.just(users)

        every { stateObserver.onChanged(capture(stateCaptor)) } just runs
        every { usersObserver.onChanged(capture(usersCaptor)) } just runs

        viewModel.getUsers()

        verify(exactly = 1) { userRepository.getUsers(any()) }
        verify(exactly = 1) { stateObserver.onChanged(ViewState.UsersLoading) }
        verify(exactly = 1) { usersObserver.onChanged(users) }
        verify(exactly = 1) { stateObserver.onChanged(ViewState.UsersLoaded) }
    }

    @Test
    fun `test get users should throw exception`() {
        val user: User = mockk()
        val users = listOf(user)
        val exception = mockk<Exception>()

        val stateCaptor = slot<ViewState>()
        val usersCaptor = slot<List<User>>()

        every { userRepository.getUsers(any()) } returns Single.error(exception)

        every { stateObserver.onChanged(capture(stateCaptor)) } just runs
        every { usersObserver.onChanged(capture(usersCaptor)) } just runs

        viewModel.getUsers()

        verify(exactly = 1) { userRepository.getUsers(any()) }
        verify(exactly = 1) { stateObserver.onChanged(ViewState.UsersLoading) }
        verify(exactly = 0) { usersObserver.onChanged(users) }
        verify(exactly = 1) { stateObserver.onChanged(ViewState.UsersFailed) }
    }
}