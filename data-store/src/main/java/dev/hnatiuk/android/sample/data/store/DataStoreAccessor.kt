package dev.hnatiuk.android.sample.data.store;

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class DataStoreAccessor<T, K : Preferences.Key<T>>(
    private val dataStore: DataStore<Preferences>,
    private val key: K
) : PreferenceAccessor<T> {

    override suspend fun get(): T? {
        return dataStore.data.map { it[key] }.first()
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override fun subscribe(): Flow<T> {
        return dataStore.data.mapNotNull { it[key] }
    }

    override suspend fun put(value: T): T {
        dataStore.edit { preference ->
            preference[key] = value
        }
        return value
    }
}