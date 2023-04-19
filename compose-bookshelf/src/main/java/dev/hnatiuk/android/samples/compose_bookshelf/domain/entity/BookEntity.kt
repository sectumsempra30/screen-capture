package dev.hnatiuk.android.samples.compose_bookshelf.domain.entity;

data class BookEntity(
    val id: String,
    val title: String,
    val publishDate: String,
    val imageLink: String?
)