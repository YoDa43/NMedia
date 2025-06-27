package ru.netology.nmedia.logicFun

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import java.math.RoundingMode

fun getViewFormInt(i: Int): String {
    return if (i < 1000) {
        i.toString()
    } else if (i < 10000) {
        "%.1f K".format(i / 100 / 10.0, RoundingMode.DOWN)
    } else if (i < 1_000_000) {
        (i / 1000).toString() + "K"
    } else {
        "%.1f M".format(i / 100000 / 10.0, RoundingMode.DOWN)
    }
}

    object AndroidUtils{
        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

//object AndroidUtils {
//    fun hideKeyboard(view: View) {
//        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//    // thanks to https://stackoverflow.com/a/68925063/1219012
//    fun showKeyboard(view: View) {
//        view.requestFocus()
//        if (view.hasWindowFocus()) {
//            showKeyboardNow(view)
//        } else {
//            view.viewTreeObserver.addOnWindowFocusChangeListener(object :
//                ViewTreeObserver.OnWindowFocusChangeListener {
//                override fun onWindowFocusChanged(hasFocus: Boolean) {
//                    if (hasFocus) {
//                        showKeyboardNow(view)
//                        view.viewTreeObserver.removeOnWindowFocusChangeListener(this)
//                    }
//                }
//            })
//        }
//    }
//
//    private fun showKeyboardNow(view: View) {
//        if (!view.isFocused) return
//
//        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
//    }
//}
//
//fun View.focusAndShowKeyboard() {
//    /**
//     * This is to be called when the window already has focus.
//     */
//    fun View.showTheKeyboardNow() {
//        if (isFocused) {
//            post {
//                // We still post the call, just in case we are being notified of the windows focus
//                // but InputMethodManager didn't get properly setup yet.
//                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
//            }
//        }
//    }
//
//    requestFocus()
//    if (hasWindowFocus()) {
//        // No need to wait for the window to get focus.
//        showTheKeyboardNow()
//    } else {
//        // We need to wait until the window gets focus.
//        viewTreeObserver.addOnWindowFocusChangeListener(
//            object : ViewTreeObserver.OnWindowFocusChangeListener {
//                override fun onWindowFocusChanged(hasFocus: Boolean) {
//                    // This notification will arrive just before the InputMethodManager gets set up.
//                    if (hasFocus) {
//                        this@focusAndShowKeyboard.showTheKeyboardNow()
//                        // It’s very important to remove this listener once we are done.
//                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
//                    }
//                }
//            })
//    }
//}
