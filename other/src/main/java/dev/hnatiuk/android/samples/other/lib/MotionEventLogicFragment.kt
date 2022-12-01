package dev.hnatiuk.android.samples.other.lib

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.other.databinding.FragmentMotionEventLogicBinding

const val MOTION_EVENT_TAG = "checkMotion"

class FrameLayout1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "dispatchTouchEvent: FrameLayout1 / action: ${event.action}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "onTouchEvent: FrameLayout1 / action: ${event.action}")
        return super.onTouchEvent(event)
    }
}

class FrameLayout2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "dispatchTouchEvent: FrameLayout2 / action: ${event.action}")
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        //return true
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "onTouchEvent: FrameLayout2 / action: ${event.action}")
        //return true
        return super.onTouchEvent(event)
    }
}

class TopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "dispatchTouchEvent: TopView / action: ${event.action}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(MOTION_EVENT_TAG, "onTouchEvent: TopView")
        return super.onTouchEvent(event)
    }
}

class MotionEventLogicFragment : BaseFragment<FragmentMotionEventLogicBinding>() {

    override val bindingFactory: Inflate<FragmentMotionEventLogicBinding>
        get() = FragmentMotionEventLogicBinding::inflate

    @SuppressLint("ClickableViewAccessibility")
    override fun FragmentMotionEventLogicBinding.initUI() {
        topView.setOnTouchListener { _, event ->
            Log.i(MOTION_EVENT_TAG, "initUI: setOnTouchListener / action: ${event.action}")
            false
        }
    }

    companion object {

        fun newInstance() = MotionEventLogicFragment()
    }
}