package dev.hnatiuk.android.sample.data.store;

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    private const val DATA_STORE_NAME = "data_store"

    private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @InstallIn(SingletonComponent::class)
    @Module
    interface DataStoreBinds {

        @Binds
        fun bindsCipherManager(impl: CipherManagerImpl): CipherManager

        @Binds
        fun bindsPreferenceRepository(impl: DataStoreRepositoryImpl): PreferenceRepository
    }
}