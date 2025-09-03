package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostRepositoryInMemoryImpl : PostRepository {

    private var index: Long = 1L
    private var posts = listOf(
        Post(
            id = index++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likeCount = 999999,
            like = false,
            shareCount = 999,
            viewCount = 0,
            videoUrl = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
        ),
        Post(
            id = index++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "01 июля в 11:22",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likeCount = 99,
            like = false,
            shareCount = 99,
            viewCount = 0,
            videoUrl = "https://rutube.ru/video/371af050a70ac300f98d4ebc151edfc5/?r=plwd",
        ),

        )
    private val data = MutableLiveData(posts)

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
        data.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shareCount = post.shareCount + 1)
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = index++,
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
        data.value = posts
    }
}