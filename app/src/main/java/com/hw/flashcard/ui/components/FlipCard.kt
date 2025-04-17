package com.hw.flashcard.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FlipCard(
    frontText: String,
    backText: String,
    isFlipped: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation = animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500),
        label = "cardRotation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick)
    ) {
        // Front of the card
        if (rotation.value <= 90f) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = rotation.value
                        cameraDistance = 8 * density
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = frontText,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Back of the card
        if (rotation.value > 90f) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f - rotation.value
                        cameraDistance = 8 * density
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = backText,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
} 