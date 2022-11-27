package dev.hnatiuk.android.samples.core.utils

import android.content.Context
import android.content.Intent

abstract class SimpleIntentProvider(private val clazz: Class<*>) {

    fun getIntent(context: Context): Intent {
        return Intent(context, clazz)
    }
}