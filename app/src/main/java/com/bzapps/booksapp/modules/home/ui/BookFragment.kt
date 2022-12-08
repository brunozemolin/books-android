package com.bzapps.booksapp.modules.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.databinding.FragmentBookBinding
import com.bzapps.booksapp.modules.home.adapter.BooksAdapter
import com.bzapps.booksapp.modules.home.viewmodel.HomeViewModel
import com.bzapps.booksapp.shared.base.BaseFragment
import com.bzapps.booksapp.shared.components.closeKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookFragment : BaseFragment() {
    private val binding by lazy { FragmentBookBinding.inflate(layoutInflater) }

    private val homeViewModel by sharedViewModel<HomeViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        initObserver()
        initListener()
        setBooks(homeViewModel.bookList)
    }

    private fun initListener() {
        binding.ivSearch.setOnClickListener {
            val nameBookAuthor = binding.etSearch.text.toString()
            homeViewModel.getBooks(nameBookAuthor)
        }
    }

    private fun initObserver() {
        homeViewModel.booksState.observe(::getLifecycle, ::setBooks)
        homeViewModel.setFavoriteState.observe(::getLifecycle, ::setBooks)
        homeViewModel.loadingState.observe(::getLifecycle, ::loading)
    }

    private fun loading(status: Boolean) {
        requireActivity().closeKeyboard(binding.root)
        loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun setBooks(bookList: List<BookResponseDto>) {
        binding.tvEmptyList.visibility = if (bookList.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.rvBooks.apply {
            visibility = View.VISIBLE
            adapter = BooksAdapter(bookList, requireContext(), ::setFavorite, ::removeFavorite)
        }
    }

    private fun setFavorite(bookResponseDto: BookResponseDto) {
        homeViewModel.setFavorite(bookResponseDto)
    }

    private fun removeFavorite(bookResponseDto: BookResponseDto) {
        homeViewModel.removeFavorite(bookResponseDto)
    }

}