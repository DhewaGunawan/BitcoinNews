package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListNewsBinding
import com.example.core.domain.model.News

class NewsAdapter : ListAdapter<News, NewsAdapter.ListViewHolder>(NewsDiffCallback()) {

    var onItemClick: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListViewHolder(private val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            with(binding) {
                if (data.urlToImage.isNullOrEmpty()) {
                    Glide.with(itemView.context)
                        .load(R.drawable.no_image)
                        .into(ivItemImage)
                } else {
                    Glide.with(itemView.context)
                        .load(data.urlToImage)
                        .into(ivItemImage)
                }
                tvItemTitle.text = data.title
                tvItemSubtitle.text = data.publishedAt
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(getItem(position))
                }
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }
}