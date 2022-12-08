package com.bzapps.booksapp.modules.home

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import com.bzapps.booksapp.R
import com.bzapps.booksapp.databinding.ActivityHomeBinding
import com.bzapps.booksapp.shared.base.BaseActivity
import com.bzapps.booksapp.shared.components.DialogUtil
import com.bzapps.booksapp.modules.home.viewmodel.HomeViewModel
import com.bzapps.booksapp.modules.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private var currentFragment: Int = 0
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private var navGraph: NavGraph? = null
    private var navController: NavController? = null
    private var navHostFragment: Fragment? = null
    private var bottomNav: BottomNavigationView? = null

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun getBinding(): View {
        return binding.root
    }

    override fun initView() {
        setToolbar()
        initNavController()
        initBottomNavigation()
    }

    private fun setToolbar() {
        binding.toolbar.showOverflowMenu()
        buildMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initBottomNavigation() {
        bottomNav = binding.bottomNav
        bottomNav?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.books -> {
                    navController?.navigate(R.id.action_favoriteFragment_to_booksFragment)
                }
                R.id.favorite -> {
                    navController?.navigate(R.id.action_booksFragment_to_favoriteFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            currentFragment = destination.id
            binding.toolbar.menu.clear()
            when(destination.id) {
                R.id.booksFragment -> buildMenu()
            }
            bottomNav?.visibility = View.VISIBLE
        }
    }

    private fun buildMenu() {
        binding.toolbar.inflateMenu(R.menu.nav_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.books -> {
                    navController?.navigate(R.id.action_booksFragment_to_includeBookFragment)
                    bottomNav?.visibility = View.GONE
                }
            }
            true
        }
    }

    private fun initNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navController = navHostFragment!!.findNavController()
        navGraph = navController?.navInflater?.inflate(R.navigation.main_nav_graph)
        navGraph?.let { navController?.setGraph(it, null) }
    }

    override fun onBackPressed() {
        if (currentFragment == R.id.booksFragment) {
            showToolbarLogout()
        }
        else {
            navController?.navigate(R.id.action_includeBookFragment_to_booksFragment)
        }
    }

    private fun showToolbarLogout() {
        DialogUtil.showAlertDialog(
            context = binding.root.context,
            title = getString(R.string.logout_app),
            message = getString(R.string.message_logout),
            positiveBtnText = getString(R.string.confirm),
            positiveBtnFunction = { logout() },
            negativeBtnText = getString(R.string.cancel),
            negativeBtnFunction = { }
        )
    }

    private fun logout() {
        homeViewModel.clearUser()
        val loginIntent = LoginActivity.newIntent(context = this)
        startActivity(loginIntent)
        finishAffinity()
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

}