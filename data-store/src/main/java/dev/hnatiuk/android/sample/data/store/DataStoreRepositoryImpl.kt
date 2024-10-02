package dev.hnatiuk.android.sample.data.store;

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cipherManager: CipherManager
) : PreferenceRepository {

    override fun stringPreference(key: String): PreferenceAccessor<String> {
        return DataStoreAccessor(dataStore, stringPreferencesKey(key))
    }

    override fun encryptedStringPreference(key: String): PreferenceAccessor<String> {
        return EncryptedDataStoreAccessor(dataStore, cipherManager, stringPreferencesKey(key))
    }
}