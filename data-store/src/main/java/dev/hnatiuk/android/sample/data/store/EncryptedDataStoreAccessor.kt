package dev.hnatiuk.android.sample.data.store;

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class EncryptedDataStoreAccessor(
    private val dataStore: DataStore<Preferences>,
    private val cipherManager: CipherManager,
    private val key: Preferences.Key<String>
) : PreferenceAccessor<String> {

    override suspend fun get(): String? {
        val encryptedValue = dataStore.data.map { it[key] }.first()
        return encryptedValue?.let { cipherManager.decrypt(it) }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override fun subscribe(): Flow<String> {
        return dataStore.data.mapNotNull {
            val encryptedValue = it[key]
            encryptedValue?.let { cipherManager.decrypt(it) }
        }
    }

    override suspend fun put(value: String): String {
        val encrypted = cipherManager.encrypt(value)
        dataStore.edit { preference ->
            preference[key] = encrypted
        }
        return encrypted
    }
}