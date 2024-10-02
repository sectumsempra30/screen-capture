package dev.hnatiuk.android.samples.other.lib.popup;

import android.content.Context
import android.graphics.Rect
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import dev.hnatiuk.android.samples.other.R
import dev.hnatiuk.android.samples.other.databinding.ChatTemplatePopupItemBinding
import dev.hnatiuk.android.samples.other.lib.dpToPx
import dev.hnatiuk.android.samples.other.lib.popup.ChatTemplatePopupAction.Companion.titleResByType
import dev.hnatiuk.android.samples.other.lib.popup.ChatTemplatePopupAction.State

class CustomPopupView(
    private val context: Context,
    private val actionList: List<ChatTemplatePopupAction>,
    private val onClickActionListener: (ChatTemplatePopupAction) -> Unit,
    private val onShowListener: () -> Unit,
    private val onDismissListener: () -> Unit
) : PopupWindow(context) {

    companion object {

        private const val ALPHA_START = 0f
        private const val ALPHA_END = 1f
        private const val ANIMATION_DURATION = 150L

        private const val POPUP_WIDTH = 250
        private const val DIVIDER_HEIGHT = 8
    }

    private val layoutInflater = LayoutInflater.from(context)

    private var anchorRect: Rect? = null
    private var anchorView: View? = null
    private var parentContainer: View? = null
    private val offsetX = context.resources.dpToPx(8)
    private val offsetY = context.resources.dpToPx(4)

    init {
        this.inputMethodMode = INPUT_METHOD_NOT_NEEDED
    }

    private val locationLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            removeOnGlobalLayoutListener(contentView, this)
            anchorView?.let { anchorView ->
                anchorRect?.let { rect ->
                    parentContainer?.let { parentContainer ->
                        /* X coordinate */
                        val contentViewRight = rect.left + contentView.measuredWidth + offsetX
//                        val additionalOffsetX = if (contentViewRight > parentContainer.right) {
//                            parentContainer.right - contentViewRight
//                        } else {
//                            0
//                        }
                        val additionalOffsetX = rect.right - contentView.measuredWidth

                        /* Y coordinate */
                        val additionalOffsetY = if (rect.bottom + contentView.measuredHeight + offsetY > parentContainer.bottom) {
                            if (rect.top - parentContainer.top > contentView.measuredHeight) {
                                -(anchorView.measuredHeight + contentView.measuredHeight + offsetY)
                            } else {
                                rect.centerY() - rect.bottom
                            }
                        } else {
                            offsetY
                        }

                        if (additionalOffsetX != 0 || additionalOffsetY != 0) {
                            this@CustomPopupView.update(
                                anchorView,
                                additionalOffsetX + 70,
                                additionalOffsetY,
                                -1,
                                -1
                            )
                        }
                    }
                }
            }
            val animation = AlphaAnimation(ALPHA_START, ALPHA_END).apply {
                duration = ANIMATION_DURATION
                fillAfter = true
            }
            contentView.startAnimation(animation)
            contentView.visibility = View.VISIBLE
            onShowListener.invoke()
        }
    }

    init {
        setBackgroundDrawable(null)
        width = context.resources.dpToPx(POPUP_WIDTH)
        height = WindowManager.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isTouchable = true
        isFocusable = true
        isClippingEnabled = false

        contentView = LinearLayout(context).apply {
            this.orientation = LinearLayout.VERTICAL
            this.setBackgroundResource(R.drawable.bg_chat_message_popup_view)
            fillContainerWithActions(this, actionList)
        }
    }

    fun show(anchorView: View, parentContainer: View) {

        this.anchorView = anchorView
        this.parentContainer = parentContainer
        contentView.visibility = View.INVISIBLE
        contentView.viewTreeObserver.addOnGlobalLayoutListener(locationLayoutListener)
        val screenPosition = IntArray(2)
        anchorView.getLocationOnScreen(screenPosition)
        anchorRect = Rect(
            screenPosition[0], screenPosition[1], screenPosition[0]
                    + anchorView.width, screenPosition[1] + anchorView.height
        )

        showAtLocation(
            anchorView,
            Gravity.NO_GRAVITY,
            anchorRect!!.left,
            anchorRect!!.bottom + offsetY
        )
    }

    private fun fillContainerWithActions(container: ViewGroup, actions: List<ChatTemplatePopupAction>) {
        actions.forEachIndexed { index, item ->
            /* Item View */
            createActionView(item).apply {
                setOnClickListener {
                    onClickActionListener(item)
                    dismiss()
                }
            }.also { view ->
                container.addView(
                    view,
                    LayoutParams(LayoutParams.MATCH_PARENT, context.dpToPx(45))
                )
            }

            /* Divider */
            val divider = when {
                index == 0 && actionList.size > 1 -> View(context).apply {
                    this.layoutParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, context.dpToPx(DIVIDER_HEIGHT))
                    this.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                }
                else -> null
            }
            divider?.let { container.addView(divider) }
        }
    }

    private fun createActionView(item: ChatTemplatePopupAction) = ChatTemplatePopupItemBinding.inflate(layoutInflater).apply {
//        val color = when (item.state) {
//            State.ENABLED -> R.color.basic_01
//            State.DISABLED -> R.color.basic_02
//            State.WARNING -> R.color.basic_red_secondary
//        }.also { colorRes ->
//            ContextCompat.getColor(context, colorRes)
//        }
        val color = ContextCompat.getColor(context, android.R.color.black)

        if (item.state == State.DISABLED) {
            root.isClickable = false
        }

        title.text = context.getString(item.titleResByType)
        title.setTextColor(color)
//        icon.setImageResource(item.iconResByType)
//        icon.imageTintList = ColorStateList.valueOf(color)
    }.root

    private fun removeOnGlobalLayoutListener(
        view: View?,
        listener: ViewTreeObserver.OnGlobalLayoutListener
    ) {
        view?.viewTreeObserver?.removeOnGlobalLayoutListener(listener)
    }

    override fun dismiss() {
        super.dismiss()
        onDismissListener.invoke()
    }
}