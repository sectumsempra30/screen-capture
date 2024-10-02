package dev.hnatiuk.android.samples.room.lib;

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class ChatNameMessageUpdate(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastMessageText") val lastMessageText: String
)