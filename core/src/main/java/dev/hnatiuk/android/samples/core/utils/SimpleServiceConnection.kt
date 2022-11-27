package dev.hnatiuk.android.samples.core.utils

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder

abstract class SimpleServiceConnection<B : Binder> : ServiceConnection {

    abstract fun onServiceConnected(service: B)

    @Suppress("UNCHECKED_CAST")
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        onServiceConnected(service as B)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //no op
    }
}