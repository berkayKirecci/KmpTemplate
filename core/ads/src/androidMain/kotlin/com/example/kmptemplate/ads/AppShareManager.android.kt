package com.example.kmptemplate.ads

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberAppShareManager(): AppShareManager {
    val context = LocalContext.current
    return remember(context) {
        object : AppShareManager {
            override fun shareApp(message: String) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                context.startActivity(Intent.createChooser(intent, null))
            }
        }
    }
}

