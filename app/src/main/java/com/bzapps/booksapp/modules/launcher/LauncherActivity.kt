package com.bzapps.booksapp.modules.launcher

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bzapps.booksapp.databinding.ActivityLauncherBinding
import com.bzapps.booksapp.modules.home.HomeActivity
import com.bzapps.booksapp.modules.launcher.viewmodel.LauncherViewModel
import com.bzapps.booksapp.modules.login.LoginActivity
import com.bzapps.booksapp.shared.base.BaseActivity
import com.bzapps.booksapp.shared.components.closeKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : BaseActivity() {

    private val binding by lazy { ActivityLauncherBinding.inflate(layoutInflater) }
    private val launcherViewModel by viewModel<LauncherViewModel>()

    override fun getBinding(): View {
        return binding.root
    }

    override fun initView() {
        initObserver()
    }

    private fun initObserver() {
        launcherViewModel.loadingState.observe(::getLifecycle, ::loadingLogin)
        launcherViewModel.savedUserSuccessState.observe(::getLifecycle, ::successAutoLogin)
    }

    private fun loadingLogin(status: Boolean) {
        closeKeyboard(binding.root)
        loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed(this::checkAutoLogin, DELAY_TIME)
        removeNetworkListener()
    }

    private fun checkAutoLogin() {
        launcherViewModel.autoLogin()
    }

    private fun successAutoLogin(status: Boolean) {
        if (status) {
            openNextActivity(HomeActivity.newIntent(this))
        } else {
            openNextActivity(LoginActivity.newIntent(this))
        }
    }

    private fun openNextActivity(nextIntent: Intent) {
        startActivity(nextIntent)
        finish()
    }

    companion object {
        private const val DELAY_TIME = 1000L
    }

}