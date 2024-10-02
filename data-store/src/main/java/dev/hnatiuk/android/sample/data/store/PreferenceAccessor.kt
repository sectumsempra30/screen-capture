package dev.hnatiuk.android.sample.data.store;

import kotlinx.coroutines.flow.Flow

interface PreferenceAccessor<T> {

    suspend fun get(): T?

    suspend fun put(value: T): T

    suspend fun clear()

    fun subscribe(): Flow<T>
}