package com.bzapps.booksapp.modules.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.databinding.FragmentFavoriteBinding
import com.bzapps.booksapp.modules.home.adapter.BooksAdapter
import com.bzapps.booksapp.modules.home.viewmodel.HomeViewModel
import com.bzapps.booksapp.shared.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : BaseFragment() {
    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }

    private val homeViewModel by sharedViewModel<HomeViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        initObserver()
        getFavoriteBooks()
    }

    private fun getFavoriteBooks() {
        homeViewModel.getFavoriteBooks()
    }

    private fun initObserver() {
        homeViewModel.favoriteBookListState.observe(::getLifecycle, ::setFavoriteBooks)
        homeViewModel.setFavoriteState.observe(::getLifecycle, ::setFavoriteBooks)
    }

    private fun setFavoriteBooks(bookList: List<BookResponseDto>) {
        binding.tvEmptyList.visibility = if (bookList.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.rvBooks.apply {
            visibility = View.VISIBLE
            adapter = BooksAdapter(bookList, context, ::setFavorite, ::removeFavorite)
        }
    }

    private fun setFavorite(bookResponseDto: BookResponseDto) {
        homeViewModel.setFavorite(bookResponseDto)
    }

    private fun removeFavorite(bookResponseDto: BookResponseDto) {
        homeViewModel.removeFavorite(bookResponseDto, true)
    }

}