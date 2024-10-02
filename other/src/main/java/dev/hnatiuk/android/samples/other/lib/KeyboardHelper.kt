package dev.hnatiuk.android.samples.other.lib

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

fun hideKeyboard(view: View) {
    ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
}

val View.parentActivity: Activity?
    get() {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

fun Activity.contentView() = this.findViewById<View>(android.R.id.content) as ViewGroup

object KeyboardHelper {

    const val KEYBOARD_UP_DELAY = 200L

    fun isKeyboardVisible(view: View) =
        ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) == true

    fun getKeyboardHeight(view: View) =
        ViewCompat.getRootWindowInsets(view)?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0

    fun hideKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }

    fun showKeyboard(editText: View) {
        ViewCompat.getWindowInsetsController(editText)?.show(WindowInsetsCompat.Type.ime())
    }

    fun doOnKeyboardHidden(view: View, onlyOnce: Boolean = false, block: () -> Unit): KeyboardHook? {
        val activityContentView = view.parentActivity?.contentView() ?: return null
        val isKeyboardVisible = isKeyboardVisible(activityContentView)
        return if (!isKeyboardVisible) {
            block.invoke()
            null
        } else {
            onKeyboard(view.parentActivity, if (onlyOnce) KeyboardHookType.SINGLE_HIDE else KeyboardHookType.INFINITE) { visible ->
                if (!visible) {
                    block.invoke()
                }
            }.also {
                hideKeyboard(view)
            }
        }
    }

    fun doOnKeyboardShown(view: View, onlyOnce: Boolean = false, block: () -> Unit): KeyboardHook? {
        val activityContentView = view.parentActivity?.contentView() ?: return null
        val isKeyboardVisible = isKeyboardVisible(activityContentView)
        return if (isKeyboardVisible) {
            block.invoke()
            null
        } else {
            onKeyboard(view.parentActivity, if (onlyOnce) KeyboardHookType.SINGLE_SHOW else KeyboardHookType.INFINITE) { visible ->
                if (visible) {
                    block.invoke()
                }
            }.also {
                showKeyboard(view)
            }
        }
    }

    fun onKeyboard(activity: Activity?, hookType: KeyboardHookType = KeyboardHookType.INFINITE, block: IKeyboardEventListener) =
        activity?.let { onKeyboard(it.contentView(), hookType, block) }

    fun onKeyboard(fragment: Fragment?, hookType: KeyboardHookType = KeyboardHookType.INFINITE, block: IKeyboardEventListener) =
        fragment?.view?.let { onKeyboard(it, hookType, block) }

    fun onKeyboard(view: View, hookType: KeyboardHookType = KeyboardHookType.INFINITE, block: IKeyboardEventListener): KeyboardHook? {
        val activity = view.parentActivity ?: return null
        val activityContentView = activity.contentView()
        val activityInsetListener = getOrCreateActivityInsetListener(activity)
        val hook = KeyboardHook(activityInsetListener, isKeyboardVisible(activityContentView)) { v: View, hook: KeyboardHook ->
            val keyboardVisible = isKeyboardVisible(v)
            if (hook.tryChangeKeyboardState(keyboardVisible)) {
                block.invoke(keyboardVisible)

                val needRelease = when (hookType) {
                    KeyboardHookType.INFINITE -> false
                    KeyboardHookType.SINGLE -> true
                    KeyboardHookType.SINGLE_HIDE -> !keyboardVisible
                    KeyboardHookType.SINGLE_SHOW -> keyboardVisible
                }
//                if (needRelease && !hook.isDisposed) {
//                    hook.dispose()
//                }
            }
        }
        activityInsetListener.add(hook)
        ViewCompat.setOnApplyWindowInsetsListener(activityContentView, activityInsetListener)
        return hook
    }

    fun onKeyboardWithHeight(
        view: View,
        hookType: KeyboardHookType = KeyboardHookType.INFINITE,
        block: (visible: Boolean, height: Int) -> Unit
    ) = onKeyboard(view, hookType) { visible ->
        block(visible, getKeyboardHeight(view))
    }

    private val activityInsetListenersMap = ActivityInsetListenersMap()

    private fun getOrCreateActivityInsetListener(activity: Activity): MultiWindowInsetsListener {
        val activityContentView = activity.contentView()
        val activityRef = activityInsetListenersMap.keys.find { it.get() == activityContentView }
        val listener = activityRef?.let { activityInsetListenersMap[it] }
        return listener ?: MultiWindowInsetsListener(activity, activityInsetListenersMap).also {
            activityInsetListenersMap[WeakReference(activityContentView)] = it
        }
    }
}


typealias ActivityInsetListenersMap = HashMap<WeakReference<View>, MultiWindowInsetsListener>
typealias IKeyboardEventListener = (visible: Boolean) -> Unit

enum class KeyboardHookType {

    INFINITE, SINGLE, SINGLE_HIDE, SINGLE_SHOW
}

class KeyboardHook(private var parentListener: MultiWindowInsetsListener?,
                   currentKeyboardState: Boolean, block: (View, KeyboardHook) -> Unit)  {

    private var enabled = true
    private val currentKeyboardState = AtomicBoolean(currentKeyboardState)
    private val listenerRef = AtomicReference(block)

    internal fun getListener() = if (enabled) listenerRef.get() else null

    internal fun tryChangeKeyboardState(visible: Boolean) = currentKeyboardState.compareAndSet(!visible, visible)

    fun resume() {
        enabled = true
    }

    fun pause() {
        enabled = false
    }
}

class MultiWindowInsetsListener(activity: Activity,
                                private val activityInsetListenersMap: ActivityInsetListenersMap
) : OnApplyWindowInsetsListener {

    val hooks = ArrayList<KeyboardHook>()
    val disposed = AtomicBoolean(false)

    init {
        (activity as? ComponentActivity)?.lifecycle?.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                            }
        })
    }

    fun add(hook: KeyboardHook) {
        hooks.add(hook)
    }

    fun remove(hook: KeyboardHook) {
        hooks.remove(hook)
        if (hooks.isEmpty()) {

        }
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        hooks.forEach { hook ->
            hook.getListener()?.invoke(v, hook)
        }
        return insets
    }

}