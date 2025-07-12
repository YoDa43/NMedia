package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractorListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.logicFun.AndroidUtils
//import ru.netology.nmedia.logicFun.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.viewmodel.empty

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.groupEdit.visibility = View.GONE
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractorListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val isNew = posts.size > adapter.itemCount
            adapter.submitList(posts) {
                if (isNew) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                binding.groupEdit.visibility = View.VISIBLE
                binding.editMessageTextContent.text = post.content
                with(binding.content) {
                    requestFocus()
//                    focusAndShowKeyboard()
                    setText(post.content)
                }
            }
        }

        with(binding) {
            save.setOnClickListener {
                if (content.text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.error_empty_content,
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(content.text.toString())
                viewModel.save()
                content.setText("")
                content.clearFocus()
                binding.groupEdit.visibility = View.GONE
                AndroidUtils.hideKeyboard(it)
            }
            closeEditButton.setOnClickListener {
                viewModel.setPostToEdited()
                content.setText("")
                content.clearFocus()
                binding.groupEdit.visibility = View.GONE
                AndroidUtils.hideKeyboard(it)
            }
        }
    }
}
