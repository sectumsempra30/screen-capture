package dev.hnatiuk.android.samples.compose_bookshelf.data.core;

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.hnatiuk.android.samples.compose_bookshelf.data.services.GoogleBooksApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit

val RestClient.googleBooksService: GoogleBooksApi
    get() = retrofit.create(GoogleBooksApi::class.java)

object RestClient {

    private const val BASE_URL = "https://www.googleapis.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClientFactory.buildOkHttpClient())
            .addConverterFactory(getConverterFactory())
            .build()
    }

    @Suppress("JSON_FORMAT_REDUNDANT")
    private fun getConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType)
    }
}