package com.bzapps.booksapp.modules.launcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bzapps.booksapp.data.model.response.ResponseRequired
import com.bzapps.booksapp.shared.base.BaseViewModel
import com.bzapps.booksapp.modules.launcher.usecase.AutoLoginUseCase
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val autoLoginUseCase: AutoLoginUseCase
) : BaseViewModel() {

    private var _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private var _savedUserSuccessState: MutableLiveData<Boolean> = MutableLiveData()
    val savedUserSuccessState: LiveData<Boolean>
        get() = _savedUserSuccessState

    fun autoLogin() {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = autoLoginUseCase.invoke()) {
                is ResponseRequired.Success -> {
                    val status = response.result
                    _savedUserSuccessState.postValue(status)
                    _loadingState.postValue(true)
                }
                is ResponseRequired.Error -> {
                    _loadingState.postValue(false)
                    _savedUserSuccessState.postValue(false)
                }
            }
        }
    }
}