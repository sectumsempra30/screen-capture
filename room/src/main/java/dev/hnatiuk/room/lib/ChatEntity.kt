package dev.hnatiuk.room.lib

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "participant") val participant: List<String>,
    @ColumnInfo(name = "iconLink") val iconLink: String?,
    @ColumnInfo(name = "numUnreadMessage") val numUnreadMessage: Int,
    @ColumnInfo(name = "lastMessageText") val lastMessageText: String?,
    @ColumnInfo(name = "lastDate") val lastDate: Long,
    @ColumnInfo(name = "status") val status: ChatStatus,
    @ColumnInfo(name = "iconColor") val iconColor: String?,
    @ColumnInfo(name = "onDesktop") val onDesktop: Boolean,
    @ColumnInfo(name = "draftMessage") val draftMessage: String?,
    @ColumnInfo(name = "lastMessageStatus") val lastMessageStatus: MessageStatus
)