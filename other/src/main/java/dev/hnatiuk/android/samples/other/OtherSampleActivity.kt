package dev.hnatiuk.android.samples.other

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.widget.TextViewCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nabilmh.lottieswiperefreshlayout.LottieSwipeRefreshLayout
import dev.hnatiuk.android.samples.core.base.BaseActivity
import dev.hnatiuk.android.samples.core.extensions.toast
import dev.hnatiuk.android.samples.core.utils.SimpleIntentProvider
import dev.hnatiuk.android.samples.other.databinding.ActivityOtherSampleBinding
import dev.hnatiuk.android.samples.other.lib.EditTextLostFocusOutsideFragment
import dev.hnatiuk.android.samples.other.lib.SampleAdapter
import dev.hnatiuk.android.samples.other.lib.SampleItem
import dev.hnatiuk.android.samples.other.lib.popup.ChatTemplatePopupAction.Companion.asAction
import dev.hnatiuk.android.samples.other.lib.popup.ChatTemplatePopupAction.Type
import dev.hnatiuk.android.samples.other.lib.popup.CustomPopupView
import java.security.Permission
import java.security.Permissions

class OtherSampleActivity : BaseActivity<ActivityOtherSampleBinding>() {

    override val bindingFactory: (LayoutInflater) -> ActivityOtherSampleBinding
        get() = ActivityOtherSampleBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun ActivityOtherSampleBinding.initUI() {
        val instance = EditTextLostFocusOutsideFragment.newInstance()

        setupList()


    }

    val pickContact = registerForActivityResult(ActivityResultContracts.GetContent()) {
        toast("picked")
    }

    private fun setupList() = with(binding) {
        binding.square.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this@OtherSampleActivity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                    toast("granted")
                }
//                ActivityCompat.shouldShowRequestPermissionRationale(this@OtherSampleActivity, Manifest.permission.READ_CONTACTS) -> {
//                    toast("should show rationale")
//                }
                else -> {
                    ActivityCompat.requestPermissions(this@OtherSampleActivity, arrayOf(Manifest.permission.READ_CONTACTS), 1)
                }
            }
        }

//        list.layoutManager = LinearLayoutManager(this@OtherSampleActivity, LinearLayoutManager.VERTICAL, false)
//
//        val adapter = SampleAdapter(
//            onClick = { view, index -> handleClick(view, index) }
//        ).also {
//            list.adapter = it
//        }

        setAutoSizeDescription(14)
//        input.doOnTextChanged { text, _, _, _ ->
//            textView.text = text
//        }

//        adapter.submitList(getList(headerSuffix = "#1"))
//
//        changeHeader.setOnClickListener {
//            //adapter.submitList(getList(headerSuffix = "updated"))
//            appBar.setExpanded(false)
//        }

    }

    fun setAutoSizeDescription(minSize: Int) {
//        TextViewCompat.setAutoSizeTextTypeWithDefaults(binding.textView,
//            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
//        )
//        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
//            /* textView = */ binding.textView,
//            /* autoSizeMinTextSize = */ minSize,
//            /* autoSizeMaxTextSize = */ 28,
//            /* autoSizeStepGranularity = */ 1,
//            /* unit = */ TypedValue.COMPLEX_UNIT_SP
//        )
    }

    private fun handleClick(targetView: View, index: Int) = with(binding) {
        CustomPopupView(
            context = this@OtherSampleActivity,
            actionList = listOf(
                Type.MOVE_UP.asAction(),
                Type.DELETE.asAction(),
                Type.MOVE_DOWN.asAction(),
                Type.PIN.asAction()
            ),
            onClickActionListener = {},
            onShowListener = {},
            onDismissListener = {}
        ).also { popUp ->
            //popUp.show(targetView, list)
        }
    }

    private fun getList(headerSuffix: String): List<SampleItem> {
        return buildList {
            add(
                SampleItem.HeaderModel(
                    id = 1,
                    text = "Some header text",
                    backgroundColor = ContextCompat.getColor(
                        this@OtherSampleActivity,
                        android.R.color.holo_green_light
                    )
                )
            )
            repeat(30) {
                if (it == 8) {
                    add(
                        SampleItem.HeaderModel(
                            id = 2,
                            text = "Some header text $headerSuffix",
                            backgroundColor = ContextCompat.getColor(
                                this@OtherSampleActivity,
                                android.R.color.holo_orange_light
                            )
                        )
                    )
                }
                add(SampleItem.SampleModel(id = it, title = "Title number #$it"))
            }
        }
    }

    companion object : SimpleIntentProvider(OtherSampleActivity::class.java)
}