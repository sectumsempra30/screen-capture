package com.hnatiuk.core.utils

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.ChecksSdkIntAtLeast

/**
 * Marshmallow
 * API 23
 * Android 6
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.M)
fun isAtLeastMarshmallow() = VERSION.SDK_INT >= VERSION_CODES.M

/**
 * Nougat
 * API 24
 * Android 7
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.N)
fun isAtLeastNougat() = VERSION.SDK_INT >= VERSION_CODES.N

/**
 * Oreo
 * API 26
 * Android 8
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.O)
fun isAtLeastOreo() = VERSION.SDK_INT >= VERSION_CODES.O

/**
 * Pie
 * API 28
 * Android 9
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.P)
fun isAtLeastPie() = VERSION.SDK_INT >= VERSION_CODES.P

/**
 * Q
 * API 29
 * Android 10
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.Q)
fun isAtLeastAndroidQ() = VERSION.SDK_INT >= VERSION_CODES.Q

/**
 * R
 * API 30
 * Android 11
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.R)
fun isAtLeastAndroidR() = VERSION.SDK_INT >= VERSION_CODES.R

/**
 * Tiramisu
 * API 33
 * Android 13
 */
@ChecksSdkIntAtLeast(api = VERSION_CODES.TIRAMISU)
fun isAtLeastAndroidTiramisu() = VERSION.SDK_INT >= VERSION_CODES.TIRAMISU