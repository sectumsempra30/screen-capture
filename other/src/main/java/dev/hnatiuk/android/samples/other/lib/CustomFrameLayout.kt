package dev.hnatiuk.android.samples.other.lib;

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class CustomFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

//    var onTouchListener: (MotionEvent?) -> Unit = {}
//
//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        onTouchListener.invoke(ev)
//        return false
//    }
}