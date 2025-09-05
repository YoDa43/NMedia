package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostRepositoryFileImpl(private val context: Context) : PostRepository {

    private var nextId = 1L
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            data.value = posts
            sync()
        }

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(FILE_NAME)
        if (file.exists()) {
            context.openFileInput(FILE_NAME).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
            }
        } else {
            sync()
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    like = !post.like,
                    likeCount = if (post.like) post.likeCount - 1 else post.likeCount + 1
                )
            } else {
                post
            }
        }
    }

    override fun share(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    shareCount = post.shareCount + 1
                )
            } else {
                post
            }
        }
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    like = false,
                    published = LocalDateTime.now()
                        .format(
                            DateTimeFormatter
                                .ofPattern("d MMMM" + " в" + " HH:mm")
                        )
                )
            ) + posts
        } else {
            posts.map {
                if (post.id == it.id) {
                    it.copy(content = post.content)
                } else it
            }
        }
    }

    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    companion object {
        private const val FILE_NAME = "posts.json"
        private val gson = Gson()
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    }
}