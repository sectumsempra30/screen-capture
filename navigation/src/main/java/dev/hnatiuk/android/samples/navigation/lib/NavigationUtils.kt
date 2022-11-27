package dev.hnatiuk.android.samples.navigation.lib

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.navigation.NavType
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

val Enum<*>.asRoute get() = buildRoute(this)

val Pair<Enum<*>, Any>.asRoute get() = buildRoute(this.first, this.second)

fun buildRoute(route: Enum<*>, vararg args: Any): String {
    return StringBuilder()
        .append(route.name)
        .append(if (args.isNotEmpty()) "/" else "")
        .append(args.joinToString(separator = "/") {
            val arg = GsonBuilder().create().toJson(it)
            Uri.encode(arg).toString()
        })
        .toString()
}

val Fragment.navController get() = findNavController()

inline fun <reified T : Parcelable> getDestinationNavType() = object : NavType<T>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key) as? T
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun parseValue(value: String): T {
        val type = object : TypeToken<T>() {}.type
        return GsonBuilder().create().fromJson(value, type)
    }
}