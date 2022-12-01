package dev.hnatiuk.android.samples.firebase.remoteconfig;

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.hnatiuk.android.samples.core.extensions.toast
import java.lang.ref.WeakReference

class RemoteConfigManager(context: Context) {

    private val contextRef = WeakReference(context)
    private val context get() = contextRef.get() ?: throw IllegalStateException("No context")

    val config = MutableLiveData<FirebaseRemoteConfig>()

    init {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun update() {
        Firebase.remoteConfig.apply {
            fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    config.postValue(this)
                    context.toast("Config updated")
                } else {
                    context.toast("Config update failed")
                }
            }

            config.postValue(this)
        }
    }
}