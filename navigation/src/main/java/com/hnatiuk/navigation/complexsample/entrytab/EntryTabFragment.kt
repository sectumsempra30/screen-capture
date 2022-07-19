package com.hnatiuk.navigation.complexsample.entrytab

import androidx.fragment.app.viewModels
import com.hnatiuk.core.base.BaseVMFragment
import com.hnatiuk.core.base.Inflate
import com.hnatiuk.navigation.databinding.FragmentEntryTabBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryTabFragment : BaseVMFragment<FragmentEntryTabBinding, EntryTabViewModel>() {

    override val bindingFactory: Inflate<FragmentEntryTabBinding>
        get() = FragmentEntryTabBinding::inflate

    override val viewModel by viewModels<EntryTabViewModel>()
}