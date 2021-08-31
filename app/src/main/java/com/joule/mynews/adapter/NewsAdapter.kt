package com.joule.mynews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joule.mynews.R
import com.joule.mynews.data.model.Article
import com.joule.mynews.databinding.ItemNewsBinding
import com.joule.mynews.ui.NewsActivity

class NewsAdapter(val items: ArrayList<Article>) : RecyclerView.Adapter<NewsAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemNewsBinding.bind(itemView)
        fun onbind(article: Article) {
            Glide.with(itemView)
                .load(article.urlToImage)
                .centerCrop()
                .into(binding.img)

            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.cardParent.setOnClickListener {
                val intent = Intent(it.context, NewsActivity::class.java)
                intent.putExtra(NewsActivity.EXTRA_DATA, article)
                it.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.onbind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}