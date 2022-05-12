package com.hnatiuk.screencapturing

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected val inflater by lazy { LayoutInflater.from(this) }

    protected fun <T : View> Int.asView(): T {
        return findViewById(this)
    }
}