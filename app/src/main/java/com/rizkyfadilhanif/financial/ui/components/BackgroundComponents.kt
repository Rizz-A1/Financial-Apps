package com.rizkyfadilhanif.financial.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    // Use MD3 primary colors for gradient - follows Monet/Dynamic Color
    val gradientStart = colorScheme.primary
    val gradientEnd = colorScheme.primaryContainer
    val decorativeColor = colorScheme.inversePrimary
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(gradientStart, gradientEnd)
                )
            )
    ) {
        // Decorative diagonal shapes
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            
            // Top right diagonal
            val path1 = Path().apply {
                moveTo(width * 0.6f, 0f)
                lineTo(width, 0f)
                lineTo(width, height * 0.4f)
                lineTo(width * 0.5f, height * 0.1f)
                close()
            }
            drawPath(
                path = path1,
                color = decorativeColor.copy(alpha = 0.3f),
                style = Fill
            )
            
            // Middle diagonal
            val path2 = Path().apply {
                moveTo(width * 0.7f, height * 0.1f)
                lineTo(width * 1.1f, height * 0.15f)
                lineTo(width * 0.9f, height * 0.45f)
                lineTo(width * 0.5f, height * 0.35f)
                close()
            }
            drawPath(
                path = path2,
                color = decorativeColor.copy(alpha = 0.2f),
                style = Fill
            )
            
            // Bottom left diagonal
            val path3 = Path().apply {
                moveTo(0f, height * 0.6f)
                lineTo(width * 0.3f, height * 0.7f)
                lineTo(width * 0.2f, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                path = path3,
                color = decorativeColor.copy(alpha = 0.25f),
                style = Fill
            )
        }
        
        content()
    }
}

@Composable
fun WaveShape(
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    Canvas(modifier = modifier.fillMaxWidth().height(80.dp)) {
        val width = size.width
        val height = size.height
        
        val path = Path().apply {
            moveTo(0f, height)
            lineTo(0f, height * 0.6f)
            
            // Wave curve
            cubicTo(
                width * 0.25f, height * 0.2f,
                width * 0.5f, height * 0.8f,
                width, height * 0.4f
            )
            
            lineTo(width, height)
            close()
        }
        
        drawPath(
            path = path,
            color = surfaceColor,
            style = Fill
        )
    }
}
