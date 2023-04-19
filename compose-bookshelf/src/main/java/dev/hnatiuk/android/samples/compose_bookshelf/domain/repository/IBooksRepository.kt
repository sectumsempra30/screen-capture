package dev.hnatiuk.android.samples.compose_bookshelf.domain.repository;

import dev.hnatiuk.android.samples.compose_bookshelf.domain.entity.BookEntity

interface IBooksRepository {

    suspend fun getBooks(query: String, maxResults: Int): Result<List<BookEntity>>
}