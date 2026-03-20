package com.example.kmptemplate.ads

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun BannerAd(modifier: Modifier = Modifier.fillMaxWidth())

@Composable
expect fun InterstitialAd(onDismiss: () -> Unit = {}, modifier: Modifier = Modifier.fillMaxSize())