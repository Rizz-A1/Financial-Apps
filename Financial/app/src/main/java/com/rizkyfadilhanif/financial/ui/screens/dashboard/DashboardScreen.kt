package com.rizkyfadilhanif.financial.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rizkyfadilhanif.financial.R
import com.rizkyfadilhanif.financial.ui.components.*
import com.rizkyfadilhanif.financial.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToIncome: () -> Unit,
    onNavigateToExpense: () -> Unit,
    onNavigateToEmployee: () -> Unit,
    onNavigateToKasbon: () -> Unit,
    onNavigateToReport: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var isDrawerOpen by remember { mutableStateOf(false) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryDark)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { isDrawerOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                
                Text(
                    text = stringResource(R.string.greeting, uiState.userName),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                
                Text(
                    text = uiState.userName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = PrimaryDark
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Dashboard header with download button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    
                    Button(
                        onClick = onNavigateToReport,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudDownload,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.btn_download_report),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Income Card
                DashboardCard(
                    title = stringResource(R.string.income_today),
                    value = formatCurrency(uiState.todayIncome),
                    weeklyValue = stringResource(R.string.weekly_label, formatCurrency(uiState.weeklyIncome)),
                    icon = Icons.Default.SwapHoriz,
                    backgroundColor = CardIncome,
                    borderColor = BorderIncome
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Expense Card
                DashboardCard(
                    title = stringResource(R.string.expense_today),
                    value = formatCurrency(uiState.todayExpense),
                    weeklyValue = stringResource(R.string.weekly_label, formatCurrency(uiState.weeklyExpense)),
                    icon = Icons.Default.MonetizationOn,
                    backgroundColor = CardExpense,
                    borderColor = BorderExpense
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Balance Card
                SimpleCard(
                    title = stringResource(R.string.remaining_balance),
                    value = formatCurrency(uiState.balance),
                    icon = Icons.Default.AccountBalanceWallet,
                    backgroundColor = CardBalance,
                    borderColor = BorderBalance
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Employee Card
                SimpleCard(
                    title = stringResource(R.string.employees),
                    value = uiState.employeeCount.toString(),
                    icon = Icons.Default.Groups,
                    backgroundColor = CardEmployee,
                    borderColor = BorderEmployee
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Weekly Chart Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.weekly_income_chart),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        val maxValue = uiState.weeklyData.maxOrNull() ?: 1.0
                        WeeklyBarChart(
                            data = uiState.weeklyData,
                            maxValue = if (maxValue > 0) maxValue else 1.0
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Day labels
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf(
                                R.string.day_4_ago,
                                R.string.day_3_ago,
                                R.string.day_2_ago,
                                R.string.day_1_ago
                            ).forEach { dayRes ->
                                Text(
                                    text = stringResource(dayRes),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf(
                                R.string.day_7_ago,
                                R.string.day_6_ago,
                                R.string.day_5_ago
                            ).forEach { dayRes ->
                                Text(
                                    text = stringResource(dayRes),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Comparison Chart Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.comparison_chart),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            }
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                ChartLegendItem(
                                    color = ChartExpense,
                                    label = stringResource(R.string.expense_label)
                                )
                                ChartLegendItem(
                                    color = ChartIncome,
                                    label = stringResource(R.string.income_label)
                                )
                                ChartLegendItem(
                                    color = ChartCycle,
                                    label = stringResource(R.string.cycle_label)
                                )
                            }
                            
                            PieChart(
                                data = PieChartData(
                                    expense = uiState.weeklyExpense.toFloat(),
                                    income = uiState.weeklyIncome.toFloat(),
                                    cycle = (uiState.weeklyIncome - uiState.weeklyExpense).coerceAtLeast(0.0).toFloat()
                                )
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        // Navigation Drawer overlay
        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInHorizontally { -it },
            exit = slideOutHorizontally { -it }
        ) {
            NavigationDrawerContent(
                userName = uiState.userName,
                onNavigateToIncome = {
                    isDrawerOpen = false
                    onNavigateToIncome()
                },
                onNavigateToExpense = {
                    isDrawerOpen = false
                    onNavigateToExpense()
                },
                onNavigateToEmployee = {
                    isDrawerOpen = false
                    onNavigateToEmployee()
                },
                onNavigateToKasbon = {
                    isDrawerOpen = false
                    onNavigateToKasbon()
                },
                onNavigateToReport = {
                    isDrawerOpen = false
                    onNavigateToReport()
                },
                onLogout = {
                    isDrawerOpen = false
                    onLogout()
                },
                onClose = { isDrawerOpen = false }
            )
        }
    }
}

@Composable
private fun ChartLegendItem(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}
