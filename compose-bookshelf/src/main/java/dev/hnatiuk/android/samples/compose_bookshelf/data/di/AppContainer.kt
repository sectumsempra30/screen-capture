package dev.hnatiuk.android.samples.compose_bookshelf.data.di

import dev.hnatiuk.android.samples.compose_bookshelf.domain.repository.IBooksRepository

interface AppContainer {

    val booksRepository: IBooksRepository
}