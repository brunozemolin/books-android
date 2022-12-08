package com.bzapps.booksapp.modules.home.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bzapps.booksapp.data.local.database.FirebaseInstance
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.data.model.favorite.FavoriteResponseDto
import com.bzapps.booksapp.data.model.response.ResponseRequired
import com.bzapps.booksapp.modules.home.usecase.*
import com.bzapps.booksapp.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
    private val includeBookUseCase: IncludeBookUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val removeFavorite: DeleteFavoriteUseCase,
    private val clearUserUseCase: ClearUserUseCase,
    private val firebaseInstance: FirebaseInstance
) : BaseViewModel() {

    private var favoriteBookList: List<BookResponseDto> = arrayListOf()
    private var favoriteBook: FavoriteResponseDto? = null
    var bookList: List<BookResponseDto> = arrayListOf()

    private var _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private var _booksState: MutableLiveData<List<BookResponseDto>> = MutableLiveData()
    val booksState: LiveData<List<BookResponseDto>>
        get() = _booksState

    private var _favoriteBookListState: MutableLiveData<List<BookResponseDto>> = MutableLiveData()
    val favoriteBookListState: LiveData<List<BookResponseDto>>
        get() = _favoriteBookListState

    private var _setFavoriteState: MutableLiveData<List<BookResponseDto>> = MutableLiveData()
    val setFavoriteState: LiveData<List<BookResponseDto>>
        get() = _setFavoriteState

    private var _removeFavoriteState: MutableLiveData<Boolean> = MutableLiveData()
    val removeFavoriteState: LiveData<Boolean>
        get() = _removeFavoriteState

    private var _includeBookSuccessState: MutableLiveData<Boolean> = MutableLiveData()
    val includeBookSuccessState: LiveData<Boolean>
        get() = _includeBookSuccessState

    private var _bookErrorState: MutableLiveData<Throwable> = MutableLiveData()
    val bookErrorState: LiveData<Throwable>
        get() = _bookErrorState

    private var _favoriteErrorState: MutableLiveData<Throwable> = MutableLiveData()
    val favoriteErrorState: LiveData<Throwable>
        get() = _favoriteErrorState

    fun getBooks(name: String? = "") {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = getBooksUseCase.invoke(name ?: "")) {
                is ResponseRequired.Success -> {
                    bookList = response.result
                    _booksState.postValue(bookList)
                    _loadingState.postValue(false)
                }
                is ResponseRequired.Error -> {
                    _loadingState.postValue(false)
                    _bookErrorState.postValue(response.throwable)
                }
            }
        }
    }

    fun setFavorite(bookResponseDto: BookResponseDto) {
        viewModelScope.launch {
            bookResponseDto.uuid?.let {
                when (val response = setFavoriteUseCase.invoke(it)) {
                    is ResponseRequired.Success -> {
                        favoriteBook = response.result
                        bookList.forEach { book ->
                            if (book.uuid == bookResponseDto.uuid) {
                                book.isFavorite = true
                                _setFavoriteState.postValue(bookList)
                            }
                        }
                    }
                    is ResponseRequired.Error -> {
                        _favoriteErrorState.postValue(response.throwable)
                    }
                }
            }

        }
    }

    fun removeFavorite(bookResponseDto: BookResponseDto, isFavoriteList: Boolean = false) {
        viewModelScope.launch {
            bookResponseDto.uuid?.let {
                when (val response = removeFavorite.invoke(it)) {
                    is ResponseRequired.Success -> {
                        removeFavoriteBookInList(isFavoriteList, bookResponseDto)
                    }
                    is ResponseRequired.Error -> {
                        _favoriteErrorState.postValue(response.throwable)
                    }
                }
            }

        }
    }

    private fun removeFavoriteBookInList(favoriteList: Boolean, bookResponseDto: BookResponseDto) {
        if (favoriteList) removeFromFavoriteList(bookResponseDto)
        else removeFromBookList(bookResponseDto)
    }

    private fun removeFromBookList(bookResponseDto: BookResponseDto) {
        bookList.forEach { book ->
            if (book.uuid == bookResponseDto.uuid) {
                book.isFavorite = false
                _setFavoriteState.postValue(bookList)
            }
        }
    }

    private fun removeFromFavoriteList(bookResponseDto: BookResponseDto) {
        getFavoriteBooks()
        bookList.forEach { book ->
            if (book.uuid == bookResponseDto.uuid) {
                book.isFavorite = false
            }
        }
    }

    fun getFavoriteBooks() {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = getFavoriteBooksUseCase.invoke()) {
                is ResponseRequired.Success -> {
                    favoriteBookList = response.result.map { it.book }
                    _favoriteBookListState.postValue(favoriteBookList)
                    _loadingState.postValue(false)
                }
                is ResponseRequired.Error -> {
                    _loadingState.postValue(false)
                    _bookErrorState.postValue(response.throwable)
                }
            }
        }
    }

    fun clearUser() {
        viewModelScope.launch {
            clearUserUseCase.invoke()
        }
    }

    fun includeBook(name: String, author: String, genre: String, filePath: Uri?) {
        viewModelScope.launch {
            _loadingState.postValue(true)
            filePath?.let {
                firebaseInstance.uploadImageToFirebase(it) { image ->
                    insertBook(image, name, author, genre)
                }
            }
        }
    }

    private fun insertBook(imageUrl: String, name: String, author: String, genre: String) {
        viewModelScope.launch {
            when (val response = includeBookUseCase.invoke(name, author, genre, imageUrl)) {
                is ResponseRequired.Success -> {
                    _includeBookSuccessState.postValue(true)
                    _loadingState.postValue(false)
                }
                is ResponseRequired.Error -> {
                    _loadingState.postValue(false)
                    _bookErrorState.postValue(response.throwable)
                }
            }
        }
    }
}