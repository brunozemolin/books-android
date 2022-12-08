package com.bzapps.booksapp.shared.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.bzapps.booksapp.shared.components.closeKeyboard
import com.bzapps.booksapp.shared.components.makeLoading
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

abstract class BaseFragment : Fragment() {

    protected open lateinit var loading: View
    private lateinit var manager: SplitInstallManager

    lateinit var navController: NavController

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): View?
    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return getBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SplitCompat.install(view.context)
        manager = SplitInstallManagerFactory.create(view.context)

        setupTransitionReturnAnimation()
        initView()
        initLoading()
    }

    private fun setupTransitionReturnAnimation() {
        postponeEnterTransition()
        requireView().doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onStop() {
        super.onStop()
        context?.closeKeyboard(this.requireView())
    }

    private fun initLoading() {
        loading = makeLoading(requireContext())
        requireActivity().addContentView(loading, LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

}