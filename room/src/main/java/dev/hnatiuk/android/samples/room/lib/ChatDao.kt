package dev.hnatiuk.android.samples.room.lib

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface ChatDao {

    //rx
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRx(chat: ChatEntity?): Single<Long>

    @Update(entity = ChatEntity::class)
    fun updateRx(update: ChatUpdateMessageStatus): Single<Int>
    //

    @Query("SELECT * FROM chats")
    fun subscribeOnCashedChats(): Observable<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chat: ChatEntity?)

    @Update(entity = ChatEntity::class)
    fun update(chat: ChatUpdate): Int

    @Transaction
    fun updateOrInsert(entities: List<ChatUpdate>) {
        entities.forEach { entity ->
            val updatedCount = update(entity)

            if (updatedCount < 1) {
                insert(
                    ChatEntity(
                        id = entity.id,
                        name = entity.name,
                        participant = entity.participant,
                        iconLink = entity.iconLink,
                        iconColor = entity.iconColor,
                        numUnreadMessage = entity.numUnreadMessage,
                        lastMessageText = entity.lastMessageText,
                        lastDate = entity.lastDate,
                        status = entity.status,
                        onDesktop = entity.onDesktop,
                        draftMessage = "",
                        lastMessageStatus = MessageStatus.UNDEFINED
                    )
                )
            }
        }
    }
}