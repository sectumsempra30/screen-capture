package com.hnatiuk.entry.base

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavType
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
