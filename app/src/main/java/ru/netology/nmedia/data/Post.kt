package ru.netology.nmedia.data

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likeCount: Int = 0,
    val like: Boolean = false,
    val shareCount: Int = 0,
    val viewCount: Int = 0,
    val videoUrl: String? = null,
)