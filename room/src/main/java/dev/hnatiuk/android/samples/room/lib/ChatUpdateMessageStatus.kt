package dev.hnatiuk.android.samples.room.lib;

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ChatUpdateMessageStatus(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "lastMessageStatus") val lastMessageStatus: MessageStatus
)