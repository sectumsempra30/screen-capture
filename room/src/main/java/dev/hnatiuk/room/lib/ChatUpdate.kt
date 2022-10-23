package dev.hnatiuk.room.lib

data class ChatUpdate(
    val id: String,
    val name: String,//имя
    val participant: List<String>,//участники
    val iconLink: String?,// иконка чата
    val numUnreadMessage: Int, //кол-во непрочитанных
    val lastMessageText: String?,// последнее сообщение
    val lastDate: Long,//дата последнего сообщения
    val status: ChatStatus,
    val iconColor: String?,
    val onDesktop: Boolean,
)