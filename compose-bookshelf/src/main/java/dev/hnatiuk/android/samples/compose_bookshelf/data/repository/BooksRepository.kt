package dev.hnatiuk.android.samples.compose_bookshelf.data.repository;

import dev.hnatiuk.android.samples.compose_bookshelf.data.response.book.BookNT
import dev.hnatiuk.android.samples.compose_bookshelf.data.services.GoogleBooksApi
import dev.hnatiuk.android.samples.compose_bookshelf.domain.entity.BookEntity
import dev.hnatiuk.android.samples.compose_bookshelf.domain.repository.IBooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(
    private val booksApiService: GoogleBooksApi
) : IBooksRepository {

    override suspend fun getBooks(query: String, maxResults: Int) = withContext(Dispatchers.IO) {
        try {
            val result = booksApiService.getBooks(query, maxResults)
                .books
                .orEmpty()
                .map { mapToEntity(it) }

            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun mapToEntity(model: BookNT) = BookEntity(
        id = model.id.orEmpty(),
        title = model.volumeInfo?.title.orEmpty(),
        publishDate = model.volumeInfo?.publishedDate.orEmpty(),
        imageLink = model.volumeInfo?.imageLinks?.thumbnail
    )
}