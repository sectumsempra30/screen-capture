package dev.hnatiuk.android.samples.compose_bookshelf.data.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientFactory {

    companion object {

        private const val DEFAULT_CONNECT_TIMEOUT = 20L
        private const val DEFAULT_READ_TIMEOUT = 20L
        private const val DEFAULT_WRITE_TIMEOUT = 20L

        fun buildOkHttpClient() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}