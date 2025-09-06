package ru.netology.nmedia.util

import android.content.Context
import android.view.View
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

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}