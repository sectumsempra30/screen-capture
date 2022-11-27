package dev.hnatiuk.screencapture.lib.internal

import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.os.Build
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi

internal object Utils {

    private const val LOG_TAG = "ScreenCapture"

    fun checkOnMainThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalAccessException("The method can be called only on the main thread")
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun closeSafely(image: Image?) = doSafely {
        image?.close()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun closeSafely(reader: ImageReader?) = doSafely {
        reader?.close()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stopSafely(projection: MediaProjection?) = doSafely {
        projection?.stop()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun releaseSafely(display: VirtualDisplay?) = doSafely {
        display?.release()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun interruptSafely(thread: HandlerThread) = doSafely {
        thread.quitSafely()
        thread.interrupt()
    }

    private inline fun doSafely(action: () -> Unit) = try {
        action()
    } catch (e: Exception) {
        Log.e(LOG_TAG, e.message, e)
    }
}