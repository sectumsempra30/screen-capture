package dev.hnatiuk.android.samples.room.lib

import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.Room

object DatabaseBuilder {

    fun provideMessengerDatabase(context: Context): ChatDatabase {
        return Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "chat_db"
        ).build()
    }
}