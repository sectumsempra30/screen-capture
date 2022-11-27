package dev.hnatiuk.other;

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.MotionEvent
import android.widget.EditText
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.core.utils.hideKeyboard
import dev.hnatiuk.other.databinding.FragmentEditTextLostFocusOutsideBinding

class FragmentEditTextLostFocusOutside : BaseFragment<FragmentEditTextLostFocusOutsideBinding>() {

    override val bindingFactory: Inflate<FragmentEditTextLostFocusOutsideBinding>
        get() = FragmentEditTextLostFocusOutsideBinding::inflate

    @SuppressLint("ClickableViewAccessibility")
    override fun FragmentEditTextLostFocusOutsideBinding.initUI() {
        root.setOnTouchListener { _, motionEvent ->
            onDispatchEvent(motionEvent, binding.editText)
            false
        }
    }

    private fun onDispatchEvent(event: MotionEvent, editText: EditText) {
        if (event.action == MotionEvent.ACTION_DOWN && editText.isFocused) {
            val editTextRect = Rect().apply { editText.getGlobalVisibleRect(this) }
            val textViewOutRect = Rect().apply { binding.text1.getGlobalVisibleRect(this) }

            val (rawX, rawY) = event.rawX.toInt() to event.rawY.toInt()
            if (!editTextRect.contains(rawX, rawY) && !textViewOutRect.contains(rawX, rawY)) {
                editText.clearFocus()
                requireContext().hideKeyboard(editText)
            }
        }
    }

    companion object {

        fun newInstance() = FragmentEditTextLostFocusOutside()
    }
}