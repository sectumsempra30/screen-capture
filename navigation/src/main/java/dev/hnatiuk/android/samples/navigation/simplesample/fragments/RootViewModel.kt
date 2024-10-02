package dev.hnatiuk.android.samples.navigation.simplesample.fragments;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RootViewModel : ViewModel() {

    val liveData = MutableLiveData("text me")
}