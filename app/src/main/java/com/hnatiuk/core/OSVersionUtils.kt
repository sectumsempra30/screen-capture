package com.hnatiuk.core

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

/**
 * Marshmallow
 * API 23
 * Android 6
 */
fun isAtLeastMarshmallow() = VERSION.SDK_INT >= VERSION_CODES.M

/**
 * Nougat
 * API 24
 * Android 7
 */
fun isAtLeastNougat() = VERSION.SDK_INT >= VERSION_CODES.N

/**
 * Oreo
 * API 26
 * Android 8
 */
fun isAtLeastOreo() = VERSION.SDK_INT >= VERSION_CODES.O

/**
 * Pie
 * API 28
 * Android 9
 */
fun isAtLeastPie() = VERSION.SDK_INT >= VERSION_CODES.P

/**
 * Q
 * API 29
 * Android 10
 */
fun isAtLeastAndroidQ() = VERSION.SDK_INT >= VERSION_CODES.Q

/**
 * R
 * API 30
 * Android 11
 */
fun isAtLeastAndroidR() = VERSION.SDK_INT >= VERSION_CODES.R