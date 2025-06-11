package ru.netology.nmedia.data

data class Post (
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    var likeCount: Int = 0,
    var like: Boolean = false,
    var shareCount: Int = 0,
    var viewCount: Int = 0,
)