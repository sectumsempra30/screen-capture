package dev.hnatiuk.android.samples.other.lib

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Size
import android.util.SizeF
import android.view.View
import kotlin.math.roundToInt

// ---------------- RESOURCES --------------------

fun Resources.dpToPxFloat(dp: Float): Float = dp * (this.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Resources.dpToPxFloat(dp: Int): Float = this.dpToPxFloat(dp.toFloat())

fun Resources.dpToPx(dp: Float): Int = this.dpToPxFloat(dp).roundToInt()

fun Resources.dpToPx(dp: Int): Int = this.dpToPxFloat(dp).roundToInt()

fun Resources.pxToDpFloat(px: Float): Float = px / (this.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Resources.pxToDpFloat(px: Int): Float = this.pxToDpFloat(px.toFloat())

fun Resources.pxToDp(px: Float): Int = this.pxToDpFloat(px).roundToInt()

fun Resources.pxToDp(px: Int): Int = this.pxToDpFloat(px).roundToInt()

// ---------------- CONTEXT --------------------

fun Context.dpToPxFloat(dp: Float): Float = this.resources.dpToPxFloat(dp)

fun Context.dpToPxFloat(dp: Int): Float = this.resources.dpToPxFloat(dp)

fun Context.dpToPx(dp: Float): Int = this.resources.dpToPx(dp)

fun Context.dpToPx(dp: Int): Int = this.resources.dpToPx(dp)

fun Context.pxToDpFloat(px: Float): Float = this.resources.pxToDpFloat(px)

fun Context.pxToDpFloat(px: Int): Float = this.resources.pxToDpFloat(px)

fun Context.pxToDp(px: Float): Int = this.resources.pxToDp(px)

fun Context.pxToDp(px: Int): Int = this.resources.pxToDp(px)

// ---------------- FLOAT --------------------

fun Float.dpToPxFloat(context: Context): Float = context.dpToPxFloat(this)

fun Float.dpToPx(context: Context): Int = context.dpToPx(this)

fun Float.pxToDpFloat(context: Context): Float = context.pxToDpFloat(this)

fun Float.pxToDp(context: Context): Int = context.pxToDp(this)

// ---------------- INTEGER --------------------

fun Int.dpToPxFloat(context: Context): Float = context.dpToPxFloat(this)

fun Int.dpToPx(context: Context): Int = context.dpToPx(this)

fun Int.pxToDpFloat(context: Context): Float = context.pxToDpFloat(this)

fun Int.pxToDp(context: Context): Int = context.pxToDp(this)

// ---------------- SIZE --------------------

fun Size.dpToPxFloat(context: Context) = SizeF(this.width.dpToPxFloat(context), this.height.dpToPxFloat(context))

fun Size.dpToPx(context: Context) = Size(this.width.dpToPx(context), this.height.dpToPx(context))

fun Size.pxToDpFloat(context: Context) = SizeF(this.width.pxToDpFloat(context), this.height.pxToDpFloat(context))

fun Size.pxToDp(context: Context) = Size(this.width.pxToDp(context), this.height.pxToDp(context))

// ---------------- SIZEF --------------------

fun SizeF.dpToPxFloat(context: Context) = SizeF(this.width.dpToPxFloat(context), this.height.dpToPxFloat(context))

fun SizeF.dpToPx(context: Context) = Size(this.width.dpToPx(context), this.height.dpToPx(context))

fun SizeF.pxToDpFloat(context: Context) = SizeF(this.width.pxToDpFloat(context), this.height.pxToDpFloat(context))

fun SizeF.pxToDp(context: Context) = Size(this.width.pxToDp(context), this.height.pxToDp(context))

// ---------------- VIEW --------------------

fun View.dpToPxFloat(dp: Float): Float = this.resources.dpToPxFloat(dp)

fun View.dpToPxFloat(dp: Int): Float = this.resources.dpToPxFloat(dp)

fun View.dpToPx(dp: Float): Int = this.resources.dpToPx(dp)

fun View.dpToPx(dp: Int): Int = this.resources.dpToPx(dp)