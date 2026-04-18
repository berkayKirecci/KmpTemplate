package com.example.kmptemplate.designsystem.composables

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.kmptemplate.designsystem.theme.GreenPrimary

@Composable
fun TutorialOverlay(
    targetRect: Rect?,
    text: String,
    onClose: () -> Unit
) {
    if (targetRect == null) return

    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )

    var overlayOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                overlayOffset = coordinates.positionInRoot()
            }
            .clickable { onClose() }
    ) {
        val localCenter = targetRect.center - overlayOffset
        val localBottom = targetRect.bottom - overlayOffset.y

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
        ) {
            drawRect(color = Color.Black.copy(alpha = 0.8f))
            val holeRadius = (targetRect.maxDimension / 2f) + 16f
            drawCircle(
                color = Color.Transparent,
                radius = holeRadius,
                center = localCenter,
                blendMode = BlendMode.Clear
            )
            drawCircle(
                color = GreenPrimary.copy(alpha = 0.25f),
                radius = holeRadius + 6f,
                center = localCenter,
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = GreenPrimary.copy(alpha = 0.6f),
                radius = holeRadius,
                center = localCenter,
                style = Stroke(width = 3f)
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .offset {
                        IntOffset(
                            x = (localCenter.x - 24.dp.toPx()).toInt(),
                            y = (localBottom + 20f + offsetY).toInt()
                        )
                    }
            )

            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(
                            x = 0,
                            y = (localBottom + 20f + offsetY + 48.dp.toPx() + 8.dp.toPx()).toInt()
                        )
                    }
                    .padding(horizontal = 32.dp)
            )
        }
    }
}