package ru.netology.nmedia.logicFun

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