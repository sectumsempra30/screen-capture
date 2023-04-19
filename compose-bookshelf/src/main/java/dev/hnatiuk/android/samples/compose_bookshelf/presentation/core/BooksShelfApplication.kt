package dev.hnatiuk.android.samples.compose_bookshelf.presentation.core;

import android.app.Application
import dev.hnatiuk.android.samples.compose_bookshelf.data.di.AppContainer
import dev.hnatiuk.android.samples.compose_bookshelf.data.di.DefaultAppContainer

class BooksShelfApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}