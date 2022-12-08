package com.bzapps.booksapp.modules.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bzapps.booksapp.R
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.databinding.ItemBooksBinding

class BooksAdapter(
    private val bookList: List<BookResponseDto>,
    private val context: Context,
    private val setFavorite:  (book: BookResponseDto) -> Unit?,
    private val remoteFavorite:  (book: BookResponseDto) -> Unit,
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var _binding: ItemBooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        _binding = ItemBooksBinding.inflate(layoutInflater, parent, false)
        return BooksViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = bookList[position]
        holder.bindItemView(book, position)
    }

    override fun getItemCount(): Int = bookList.size

    inner class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItemView(book: BookResponseDto, position: Int) {
            binding.clBooks.tag = book.uuid
            binding.tvBookName.text = book.name
            binding.tvAuthorName.text = book.author
            binding.tvGenre.text = book.genre
            binding.ivFavorite.setImageDrawable(getImageFavorite(book.isFavorite))
            book.url?.let { setImageBook(context, binding.ivItem, it) }
            if (position == itemCount - 1) {
                binding.vDivider.visibility = View.GONE
            }
            binding.ivFavorite.setOnClickListener {
                if (book.isFavorite){
                    remoteFavorite(book)
                } else {
                    setFavorite(book)
                }
            }
        }
    }

    private fun setImageBook(context: Context, ivItem: ImageView, urlImage: String) {
        Glide.with(context.applicationContext).load(urlImage).into(ivItem)
    }

    private fun getImageFavorite(isFavorite: Boolean): Drawable? {
        return if (isFavorite) {
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite_on)
        } else {
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite_off)
        }
    }
}

