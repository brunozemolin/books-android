package com.bzapps.booksapp.shared.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.bzapps.booksapp.R
import com.bzapps.booksapp.shared.components.*
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory


abstract class BaseActivity : AppCompatActivity(),
    NetworkStateReceiver.NetworkStateReceiverListener {

    protected open lateinit var loading: View
    private var noConnectionBottomSheetDialog: NoConnectionBottomSheetDialog? = null
    private lateinit var manager: SplitInstallManager

    private lateinit var networkStateReceiver: NetworkStateReceiver

    protected abstract fun getBinding(): View
    protected abstract fun initView()

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        SplitCompat.install(context)
        manager = SplitInstallManagerFactory.create(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(getBinding())

        networkStateReceiver = NetworkStateReceiver()
        if (noConnectionBottomSheetDialog == null) {
            noConnectionBottomSheetDialog = NoConnectionBottomSheetDialog()
        }
        initView()
        initLoading()
    }

    override fun onStart() {
        super.onStart()
        registerNetwork()
    }

    private fun registerNetwork() {
        networkStateReceiver.addListener(this);
        this.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterNetwork()
    }

    private fun unregisterNetwork() {
        networkStateReceiver.removeListener(this)
        try {
            this.unregisterReceiver(networkStateReceiver)
        } catch (_: Exception) {
        }
    }

    open fun removeNetworkListener() {
        networkStateReceiver.removeListener(this)
    }

    override fun networkAvailable() {
        if (noConnectionBottomSheetDialog?.isVisible!!) {
            noConnectionBottomSheetDialog?.dismiss()
        }
    }

    override fun networkUnavailable() {
        if (!noConnectionBottomSheetDialog?.isAdded!!) {
            noConnectionBottomSheetDialog?.show(supportFragmentManager, "noconnection")
        }
    }

    private fun initLoading() {
        loading = makeLoading(this)
        addContentView(loading, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

    companion object {
        const val TAG_ERROR = "Error"
    }
}