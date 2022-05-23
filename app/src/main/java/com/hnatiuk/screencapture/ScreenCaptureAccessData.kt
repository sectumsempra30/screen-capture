package com.hnatiuk.screencapture

import android.content.Intent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ScreenCaptureAccessData(
    val resultCode: Int,
    val data: Intent
) : Parcelable