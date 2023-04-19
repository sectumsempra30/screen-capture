package dev.hnatiuk.android.samples.compose_bookshelf.data.services;

import dev.hnatiuk.android.samples.compose_bookshelf.data.response.book.BookShelfNT
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("/books/v1/volumes")
    suspend fun getBooks(
        @Query("q") searchQuery: String,
        @Query("maxResults") maxResults: Int
    ): BookShelfNT
}