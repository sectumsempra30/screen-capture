package dev.hnatiuk.android.samples.room.lib;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [ChatEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun provideChatDao(): ChatDao
}