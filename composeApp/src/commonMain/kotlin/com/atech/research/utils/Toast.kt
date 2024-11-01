package com.atech.research.utils

expect class Toast {
    fun show(message: String,duration: ToastDuration)
}

enum class ToastDuration{
    SHORT,
    LONG
}