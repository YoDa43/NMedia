package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.logicFun.getViewFormInt


typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        content.text = post.content
        published.text = post.published
        likeCount.text = getViewFormInt(post.likeCount)
        shareCount.text = getViewFormInt(post.shareCount)
        viewCount.text = post.viewCount.toString()
        favoriteBorder.setImageResource(
            if (post.like) {
                R.drawable.red_heart
            } else {
                R.drawable.empty_heart
            }
        )
        favoriteBorder.setOnClickListener {
            onLikeListener(post)
        }
        share.setOnClickListener {
            onShareListener(post)
        }
    }
}

object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}