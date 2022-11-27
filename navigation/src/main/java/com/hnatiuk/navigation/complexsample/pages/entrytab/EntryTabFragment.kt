package dev.hnatiuk.navigation.complexsample.pages.entrytab

import androidx.fragment.app.viewModels
import dev.hnatiuk.core.base.BaseVMFragment
import dev.hnatiuk.core.base.Inflate
import dev.hnatiuk.navigation.databinding.FragmentEntryTabBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryTabFragment : BaseVMFragment<FragmentEntryTabBinding, EntryTabViewModel>() {

    override val bindingFactory: Inflate<FragmentEntryTabBinding>
        get() = FragmentEntryTabBinding::inflate

    override val viewModel by viewModels<EntryTabViewModel>()
}