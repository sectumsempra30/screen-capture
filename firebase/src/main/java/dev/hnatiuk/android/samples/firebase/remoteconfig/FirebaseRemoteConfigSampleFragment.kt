package dev.hnatiuk.android.samples.firebase.remoteconfig

import com.google.firebase.remoteconfig.ktx.get
import dev.hnatiuk.android.samples.core.base.BaseFragment
import dev.hnatiuk.android.samples.core.base.Inflate
import dev.hnatiuk.android.samples.firebase.databinding.FragmentFirebaseRemoteConfigSampleBinding

class FirebaseRemoteConfigSampleFragment :
    BaseFragment<FragmentFirebaseRemoteConfigSampleBinding>() {

    private val configManager by lazy { RemoteConfigManager(requireContext()) }

    override val bindingFactory: Inflate<FragmentFirebaseRemoteConfigSampleBinding>
        get() = FragmentFirebaseRemoteConfigSampleBinding::inflate

    override fun FragmentFirebaseRemoteConfigSampleBinding.initUI() {
        configManager.update()
        configManager.config.observe(viewLifecycleOwner) {
            sendMessagesEnabled.isChecked = it[SEND_MESSAGES_ENABLED_KEY].asBoolean()
            defaultMessage.text = it[DEFAULT_MESSAGE_KEY].asString()
        }
        updateConfig.setOnClickListener {
            configManager.update()
        }
    }

    companion object {

        fun newInstance() = FirebaseRemoteConfigSampleFragment()
    }
}