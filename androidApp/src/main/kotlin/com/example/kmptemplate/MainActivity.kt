package com.example.kmptemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptemplate.base.AppConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(AndroidAppConfig)
        }
    }
}

private object AndroidAppConfig : AppConfig {
    override val environment: String = BuildConfig.ENVIRONMENT
    override val apiBaseUrl: String = BuildConfig.API_BASE_URL
    override val logHttpBodies: Boolean = BuildConfig.LOG_HTTP_BODIES
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(AndroidAppConfig)
}