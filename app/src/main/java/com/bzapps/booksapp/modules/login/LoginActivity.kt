package com.bzapps.booksapp.modules.login

import android.content.Context
import android.content.Intent
import android.view.View
import com.bzapps.booksapp.data.repository.mapper.LoginMapper.ERROR_MAP_LOGIN
import com.bzapps.booksapp.databinding.ActivityLoginBinding
import com.bzapps.booksapp.shared.base.BaseActivity
import com.bzapps.booksapp.shared.components.closeKeyboard
import com.bzapps.booksapp.shared.extensions.showSnackBar
import com.bzapps.booksapp.modules.home.HomeActivity
import com.bzapps.booksapp.modules.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun getBinding(): View {
        return binding.root
    }

    override fun initView() {
        initObserver()
        initListener()
    }

    private fun initObserver() {
        loginViewModel.loadingState.observe(::getLifecycle, ::loadingLogin)
        loginViewModel.loginSuccessState.observe(::getLifecycle, ::successLogin)
        loginViewModel.loginErrorState.observe(::getLifecycle, ::errorLogin)
    }

    private fun initListener() {
        binding.btLogin.setOnClickListener {
                requestLogin()
        }
    }

    private fun requestLogin() {
        loginViewModel.login(getLogin(), getPassword())
        binding.btLogin.isClickable = false
    }

    private fun getPassword(): String {
        return binding.etPassword.text.toString()
    }

    private fun getLogin(): String {
        return binding.etLogin.text.toString()
    }

    private fun loadingLogin(status: Boolean) {
        closeKeyboard(binding.root)
        loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun successLogin(status: Boolean) {
        if (status) {
            binding.btLogin.isClickable = true
            openHome()
        }
    }

    private fun errorLogin(throwable: Throwable) {
        binding.btLogin.isClickable = true
        binding.root.showSnackBar(this, ERROR_MAP_LOGIN)
    }

    private fun openHome() {
        startActivity(HomeActivity.newIntent(this))
        finish()
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

}