package dev.hnatiuk.core.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import dev.hnatiuk.core.utils.isAtLeastAndroidTiramisu

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    isAtLeastAndroidTiramisu() -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    isAtLeastAndroidTiramisu() -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}