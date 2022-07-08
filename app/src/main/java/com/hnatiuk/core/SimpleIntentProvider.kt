package com.hnatiuk.core

import android.content.Context
import android.content.Intent

open class SimpleIntentProvider(private val clazz: Class<*>){

    fun getIntent(context: Context): Intent {
        return Intent(context, clazz)
    }
}