package com.bzapps.booksapp.modules.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bzapps.booksapp.data.model.response.ResponseRequired
import com.bzapps.booksapp.modules.login.usecase.LoginUseCase
import com.bzapps.booksapp.modules.login.usecase.SetUserIdPreferenceUseCase
import com.bzapps.booksapp.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val setUserIdPreferenceUseCase: SetUserIdPreferenceUseCase,
) : BaseViewModel() {

    private var _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private var _loginSuccessState: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccessState: LiveData<Boolean>
        get() = _loginSuccessState

    private var _loginErrorState: MutableLiveData<Throwable> = MutableLiveData()
    val loginErrorState: LiveData<Throwable>
        get() = _loginErrorState

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = loginUseCase.invoke(login, password)) {
                is ResponseRequired.Success -> {
                    val userId = response.result.userId
                    setUserIdPreferenceUseCase.invoke(userId)
                    _loginSuccessState.postValue(true)
                    _loadingState.postValue(true)
                }
                is ResponseRequired.Error -> {
                    _loadingState.postValue(false)
                    _loginErrorState.postValue(response.throwable)
                }
            }
        }
    }
}