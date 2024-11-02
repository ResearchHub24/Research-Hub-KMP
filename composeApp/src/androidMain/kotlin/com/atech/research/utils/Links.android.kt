package com.atech.research.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

actual class LinkHelper(
    private val context: Context
) {
    actual fun openLink(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            if (context !is Activity) {
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(browserIntent)
        } catch (e: Exception) {
            Log.d(ResearchLogLevel.ERROR.name, "${e.message}")
            Toast.makeText(context, e.message ?: "Error opening link", Toast.LENGTH_SHORT).show()
        }
    }
}