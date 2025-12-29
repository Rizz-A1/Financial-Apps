package com.rizkyfadilhanif.financial.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.rizkyfadilhanif.financial.ui.theme.ChartCycle
import com.rizkyfadilhanif.financial.ui.theme.ChartExpense
import com.rizkyfadilhanif.financial.ui.theme.ChartIncome

data class PieChartData(
    val expense: Float,
    val income: Float,
    val cycle: Float
)

@Composable
fun PieChart(
    data: PieChartData,
    modifier: Modifier = Modifier
) {
    val total = data.expense + data.income + data.cycle
    val expenseAngle = if (total > 0) (data.expense / total) * 360f else 120f
    val incomeAngle = if (total > 0) (data.income / total) * 360f else 120f
    val cycleAngle = if (total > 0) (data.cycle / total) * 360f else 120f
    
    Canvas(modifier = modifier.size(120.dp)) {
        val strokeWidth = 30f
        val radius = (size.minDimension - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)
        
        var startAngle = -90f
        
        // Expense arc
        drawArc(
            color = ChartExpense,
            startAngle = startAngle,
            sweepAngle = expenseAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        startAngle += expenseAngle
        
        // Income arc
        drawArc(
            color = ChartIncome,
            startAngle = startAngle,
            sweepAngle = incomeAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        startAngle += incomeAngle
        
        // Cycle arc
        drawArc(
            color = ChartCycle,
            startAngle = startAngle,
            sweepAngle = cycleAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
    }
}

@Composable
fun WeeklyBarChart(
    data: List<Double>,
    maxValue: Double,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth().height(100.dp)) {
        val barWidth = size.width / (data.size * 2 + 1)
        val maxHeight = size.height - 20f
        
        data.forEachIndexed { index, value ->
            val barHeight = if (maxValue > 0) (value / maxValue * maxHeight).toFloat() else 0f
            val x = barWidth * (index * 2 + 1)
            val y = size.height - barHeight
            
            drawLine(
                color = Color(0xFF4A90D9),
                start = Offset(x + barWidth / 2, size.height),
                end = Offset(x + barWidth / 2, y),
                strokeWidth = barWidth * 0.8f,
                cap = StrokeCap.Round
            )
        }
    }
}
