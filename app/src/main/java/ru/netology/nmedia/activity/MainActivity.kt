package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.logicFun.getViewFormInt
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
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
            }

            binding.favoriteBorder.setOnClickListener {
                viewModel.like()
            }
            binding.share.setOnClickListener {
                viewModel.share()
            }
        }
    }
}
