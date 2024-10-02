package dev.hnatiuk.android.sample.data.store

interface PreferenceRepository {

    fun stringPreference(key: String): PreferenceAccessor<String>

    fun encryptedStringPreference(key: String): PreferenceAccessor<String>
}