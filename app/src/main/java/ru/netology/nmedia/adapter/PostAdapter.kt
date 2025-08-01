package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.logicFun.getViewFormInt
import kotlin.toString


interface OnInteractorListener{
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onVideoPlay(post: Post)
}

class PostAdapter(
    private val onInteractorListener: OnInteractorListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractorListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractorListener: OnInteractorListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        content.text = post.content
        published.text = post.published
        viewCount.text = post.viewCount.toString()
        heart.apply {
            isChecked = post.like
            text = getViewFormInt(post.likeCount)
        }
        heart.setOnClickListener {
            onInteractorListener.onLike(post)
        }
        share.text = getViewFormInt(post.shareCount)

        share.setOnClickListener {
            onInteractorListener.onShare(post)
        }
        more.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_options)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            onInteractorListener.onRemove(post)
                            true
                        }
                        R.id.edit -> {
                            onInteractorListener.onEdit(post)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        videoUrl.setOnClickListener{
            onInteractorListener.onVideoPlay(post)
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