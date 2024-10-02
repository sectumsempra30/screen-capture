package dev.hnatiuk.android.sample.data.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.data.store.R
import dev.hnatiuk.android.samples.data.store.databinding.ActivityDataStoreSampleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DataStoreSampleActivity : BaseActivity<ActivityDataStoreSampleBinding>() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override val bindingFactory: (LayoutInflater) -> ActivityDataStoreSampleBinding
        get() = ActivityDataStoreSampleBinding::inflate

    override fun ActivityDataStoreSampleBinding.initUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setup()
    }

    @SuppressLint("SetTextI18n")
    private fun setup() = with(binding) {
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                preferenceRepository.stringPreference(VALUE_KEY)
//                    .subscribe()
//                    .collectLatest {
//                        runOnUiThread {
//                            decryptedText.text = it
//                        }
//                    }
//            }
//        }

        save.setOnClickListener {
            lifecycleScope.launch {
                val text = textToCipher.text?.toString().orEmpty()
                withContext(Dispatchers.IO) {
                    val encrypted = preferenceRepository
                        .encryptedStringPreference(VALUE_KEY)
                        .put(text)

                    runOnUiThread {
                        textToCipher.setText("")
                        decryptedText.text = "Encrypted: $encrypted"
                    }
                }
            }
        }

        load.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val decrypted = preferenceRepository
                        .encryptedStringPreference(VALUE_KEY)
                        .get()

                    runOnUiThread {
                        decryptedText.text = "Decrypted: $decrypted"
                    }
                }
            }
        }
    }

    companion object : SimpleIntentProvider(DataStoreSampleActivity::class.java) {

        private const val VALUE_KEY = "key1"
    }
}