package dev.hnatiuk.android.samples.compose_bookshelf.data.di;

import dev.hnatiuk.android.samples.compose_bookshelf.data.core.RestClient
import dev.hnatiuk.android.samples.compose_bookshelf.data.core.googleBooksService
import dev.hnatiuk.android.samples.compose_bookshelf.data.repository.BooksRepository

class DefaultAppContainer : AppContainer {

    override val booksRepository by lazy {
        BooksRepository(RestClient.googleBooksService)
    }
}