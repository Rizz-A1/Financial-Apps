package com.rizkyfadilhanif.financial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rizkyfadilhanif.financial.R

@Composable
fun NavigationDrawerContent(
    userName: String,
    onNavigateToIncome: () -> Unit,
    onNavigateToExpense: () -> Unit,
    onNavigateToEmployee: () -> Unit,
    onNavigateToKasbon: () -> Unit,
    onNavigateToReport: () -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(colorScheme.primary) // MD3 primary color (follows Monet)
    ) {
        // Header with back arrow and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding() // Prevent collision with status bar
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = colorScheme.onPrimary
                )
            }
            
            Text(
                text = stringResource(R.string.menu_keuangan),
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Icon keuangan
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorScheme.onPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = colorScheme.inversePrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        // Dashboard label
        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Transaksi Section
        DrawerSectionHeader(title = stringResource(R.string.menu_transaksi))
        DrawerMenuItem(
            icon = Icons.Default.SwapHoriz,
            title = stringResource(R.string.menu_pendapatan),
            onClick = onNavigateToIncome
        )
        DrawerMenuItem(
            icon = Icons.Default.MonetizationOn,
            title = stringResource(R.string.menu_pengeluaran),
            onClick = onNavigateToExpense
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Karyawan Section
        DrawerSectionHeader(title = stringResource(R.string.menu_karyawan))
        DrawerMenuItem(
            icon = Icons.Default.Person,
            title = stringResource(R.string.menu_karyawan),
            onClick = onNavigateToEmployee
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Tagihan Section
        DrawerSectionHeader(title = stringResource(R.string.menu_tagihan))
        DrawerMenuItem(
            icon = Icons.Default.Receipt,
            title = stringResource(R.string.menu_kasbon),
            onClick = onNavigateToKasbon
        )
        DrawerMenuItem(
            icon = Icons.Default.Assessment,
            title = stringResource(R.string.menu_laporan),
            onClick = onNavigateToReport
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Logout
        DrawerMenuItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            title = stringResource(R.string.menu_logout),
            onClick = onLogout
        )
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DrawerSectionHeader(title: String) {
    val colorScheme = MaterialTheme.colorScheme
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = colorScheme.onPrimary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 32.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorScheme.onPrimary.copy(alpha = 0.9f),
            modifier = Modifier.size(22.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onPrimary.copy(alpha = 0.95f)
        )
    }
}
