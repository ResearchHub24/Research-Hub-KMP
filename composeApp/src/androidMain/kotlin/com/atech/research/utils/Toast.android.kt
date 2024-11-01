package com.atech.research.utils

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual class Toast(
    private val context: Context
) {
    actual fun show(message: String, duration: ToastDuration) {
        CoroutineScope(Dispatchers.Main).launch {
            android.widget.Toast.makeText(
                context,
                message,
                when (duration) {
                    ToastDuration.SHORT -> android.widget.Toast.LENGTH_SHORT
                    ToastDuration.LONG -> android.widget.Toast.LENGTH_LONG
                }
            ).show()
        }
    }
}