package dev.hnatiuk.android.samples.other.lib;

import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.core.utils.hideKeyboard
import dev.hnatiuk.android.samples.other.databinding.FragmentEditTextLostFocusOutsideBinding

class EditTextLostFocusOutsideFragment : BaseFragment<FragmentEditTextLostFocusOutsideBinding>() {

    override val bindingFactory: Inflate<FragmentEditTextLostFocusOutsideBinding>
        get() = FragmentEditTextLostFocusOutsideBinding::inflate

    private val listener = View.OnFocusChangeListener { _, hasFocus ->
        Log.i("checkFocus", "initUI: setOnTouchListener $hasFocus")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun FragmentEditTextLostFocusOutsideBinding.initUI() {
        editText.onFocusChangeListener = listener
        root.setOnTouchListener { _, motionEvent ->
            onDispatchEvent(motionEvent, binding.editText)
            false
        }
    }

    override fun onStop() {
        super.onStop()
        binding.editText.onFocusChangeListener = null
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

        fun newInstance() = EditTextLostFocusOutsideFragment()
    }
}