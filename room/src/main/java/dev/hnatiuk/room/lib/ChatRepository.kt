package dev.hnatiuk.room.lib;

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

class ChatRepository(
    private val retrofitMock: RetrofitMock,
    private val dao: ChatDao
) {

    fun fetchChatList(): Single<List<ChatEntity>> {
        val localChatsSingleSource = dao.subscribeOnCashedChats().first(emptyList())

        return retrofitMock
            .getChats1()
            .flatMapCompletable { Completable.fromAction { dao.updateOrInsert(it.mapToUpdate()) } }
            .andThen(localChatsSingleSource)
            .onErrorResumeNext { localChatsSingleSource }
    }

    fun getSingle() = dao.subscribeOnCashedChats().first(emptyList())

    fun fetchChatListCompletable(): Completable {
        val localChatsSingleSource = dao.subscribeOnCashedChats().last(emptyList())

        return retrofitMock
            .getChats1()
            .flatMapCompletable { Completable.fromAction { dao.updateOrInsert(it.mapToUpdate()) } }
//            .andThen(localChatsSingleSource2)
//            .onErrorResumeNext { localChatsSingleSource2 }
    }

    private fun List<ChatDto>.mapToUpdate() = map {
        ChatUpdate(
            id = it.id,
            name = it.name,
            participant = it.participant,
            iconLink = it.iconLink,
            numUnreadMessage = it.numUnreadMessage,
            lastDate = it.lastDate,
            status = it.status,
            iconColor = it.iconColor,
            onDesktop = it.onDesktop,
            lastMessageText = it.lastMessageText
        )
    }

}

class RetrofitMock {

    fun getChats1(): Single<List<ChatDto>> {
        val item = ChatDto(
            id = "1",
            participant = emptyList(),
            name = "Максим Халін",
            numUnreadMessage = 2,
            status = ChatStatus.ACTIVE,
            iconLink = null,
            iconColor = null,
            onDesktop = false,
            lastDate = Date().time,
            lastMessageText = "Hello! Can you pay for the food please!",
        )

        return Single.just(listOf(
            item,
            item.copy(id = "2", name = "Максим Халін 2")
        ))
    }
}

data class ChatDto(
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
)