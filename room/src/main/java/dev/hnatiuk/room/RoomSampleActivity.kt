package dev.hnatiuk.room

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import dev.hnatiuk.core.base.BaseActivity
import dev.hnatiuk.core.extensions.toast
import dev.hnatiuk.core.utils.SimpleIntentProvider
import dev.hnatiuk.room.databinding.ActivityRoomSampleBinding
import dev.hnatiuk.room.lib.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class RoomSampleActivity : BaseActivity<ActivityRoomSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityRoomSampleBinding
        get() = ActivityRoomSampleBinding::inflate

    @SuppressLint("CheckResult")
    override fun ActivityRoomSampleBinding.initUI() {
        list.movementMethod = ScrollingMovementMethod()

        val database = DatabaseBuilder.provideMessengerDatabase(this@RoomSampleActivity)
        val dao = database.provideChatDao()
        val repository = ChatRepository(RetrofitMock(), dao)

//        dao.subscribeOnCashedChats()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { result ->
//                    list.text = result.map { it.toString() }.joinToString(separator = "\n")
//                },
//                {}
//            )

        single.setOnClickListener {
            repository.getSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> toast(result.joinToString(separator = ",") { it.name }) },
                    { error -> toast("error $error") }
                )
        }

        Completable.fromAction { database.clearAllTables() }
            .subscribeOn(Schedulers.io())
            .subscribe({}, {})

        val mock = getMockChat()

        insert.setOnClickListener {
            dao.insertRx(mock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { toast("Insert-Success | id = $it") },
                    { toast("Insert-Error | error = $it") },
                )
        }

        updatePart.setOnClickListener {
            dao.updateRx(
                ChatUpdateMessageStatus(
                    id = "2",
                    lastMessageStatus = MessageStatus.READ
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { toast("Update-Success | id = $it") },
                    { toast("Update-Error | error = $it") },
                )
        }

        updateOrInsert.setOnClickListener {
//            Completable.fromAction { dao.updateOrInsert(listOf(mock)) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { toast("Update-Or-Insert-Success") },
//                    { toast("Update-Or-Insert-Error") },
//                )
        }

        fetch.setOnClickListener {
            repository.fetchChatList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    //{}, {}
                    { result ->
                        list.text = result.map { it }.joinToString(separator = "\n")
                    },
                    {
                        toast(it.toString())
                    }
                )
        }
    }

    private fun getMockChat() = ChatEntity(
        id = "1",
        participant = emptyList(),
        name = "Максим Халін",
        numUnreadMessage = 2,
        status = ChatStatus.ACTIVE,
        draftMessage = null,
        iconLink = null,
        iconColor = null,
        onDesktop = false,
        lastDate = Date().time,
        lastMessageText = "Hello! Can you pay for the food please!",
        lastMessageStatus = MessageStatus.UNDEFINED
    )

    companion object : SimpleIntentProvider(RoomSampleActivity::class.java)
}