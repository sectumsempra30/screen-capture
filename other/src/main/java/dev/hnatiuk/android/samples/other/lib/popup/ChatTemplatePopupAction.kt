package dev.hnatiuk.android.samples.other.lib.popup;

import dev.hnatiuk.android.samples.other.R


data class ChatTemplatePopupAction(
    val type: Type,
    val state: State = State.ENABLED
) {

    enum class Type {

        PIN,
        UNPIN,
        MOVE_UP,
        MOVE_DOWN,
        DELETE
    }

    enum class State {

        ENABLED,
        DISABLED,
        WARNING
    }

    companion object {

        fun Type.asAction(state: State = State.ENABLED) = ChatTemplatePopupAction(type = this)

        val ChatTemplatePopupAction.titleResByType get() = when (type) {
            Type.PIN -> R.string.payments_v2_fragment_chat_template_action_pin
            Type.UNPIN -> R.string.payments_v2_fragment_chat_template_action_unpin
            Type.MOVE_UP -> R.string.payments_v2_fragment_chat_template_action_move_up
            Type.MOVE_DOWN -> R.string.payments_v2_fragment_chat_template_action_move_down
            Type.DELETE -> R.string.payments_v2_fragment_chat_template_action_delete
        }

        val ChatTemplatePopupAction.iconResByType get() = when (type) {
            else -> 0
        }
    }
}