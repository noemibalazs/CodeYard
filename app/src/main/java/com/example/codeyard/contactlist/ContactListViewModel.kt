package com.example.codeyard.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.codeyard.USER_RESULTS
import com.example.codeyard.User
import com.example.codeyard.base.BaseViewModel
import com.example.codeyard.repository.UserRepository
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun getUsers() {
        Single.just(true)
            .doOnSubscribe { setViewState(ViewState.UsersLoading) }
            .doOnSubscribe { Logger.d("User list is loading") }
            .subscribeOn(Schedulers.io())
            .flatMap { userRepository.getUsers(USER_RESULTS) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Logger.e("Getting users throws an error: ${it.message}") }
            .doOnSuccess { setViewState(ViewState.UsersLoaded) }
            .subscribe(_users::postValue) { setViewState(ViewState.UsersFailed) }
            .addToDisposable()
    }

    private fun setViewState(state: ViewState) {
        _viewState.postValue(state)
    }

    sealed class ViewState {
        object UsersLoading : ViewState()
        object UsersLoaded : ViewState()
        object UsersFailed : ViewState()
    }
}