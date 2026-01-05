package com.rizkyfadilhanif.financial.ui.screens.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizkyfadilhanif.financial.R
import com.rizkyfadilhanif.financial.domain.model.Employee
import com.rizkyfadilhanif.financial.ui.components.AppTopBar
import com.rizkyfadilhanif.financial.ui.components.GradientBackground
import com.rizkyfadilhanif.financial.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListScreen(
    viewModel: EmployeeViewModel,
    onNavigateBack: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val uiState by viewModel.listState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<Long?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showCount by remember { mutableIntStateOf(10) }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                AppTopBar(
                    title = stringResource(R.string.menu_karyawan),
                    userName = "Rizky",
                    onNavigationClick = onNavigateBack,
                    navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
                )
                
                // Add Button (Top Left outside card as per common pattern, or inside? Mockup has +KARYAWAN green button)
                // The mockup shows green "+ KARYAWAN" button ABOVE the main card.
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                   Button(
                       onClick = onAddClick,
                       colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)), // Dark Green
                       shape = RoundedCornerShape(4.dp),
                       modifier = Modifier.align(Alignment.CenterStart)
                   ) {
                       Text(text = "+ KARYAWAN", fontWeight = FontWeight.Bold, color = Color.White)
                   }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Fill remaining space
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp), // Less rounded based on mockup
                    colors = CardDefaults.cardColors(containerColor = ColorUtils.cardBackground), // Assuming white
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Data Karyawan",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Filters Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Show Filter
                            Column {
                                Text("Show", style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(4.dp))
                                // Simplified Dropdown for mockup look
                                OutlinedTextField(
                                    value = showCount.toString(),
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(24.dp), // Stadium shape
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.White,
                                        focusedContainerColor = Color.White,
                                        unfocusedBorderColor = Color.Black,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    ),
                                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                                    trailingIcon = {
                                        Icon(Icons.Default.UnfoldMore, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                )
                            }
                            
                            // Search Filter
                            Column {
                                Text("Search :", style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(4.dp))
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.White,
                                        focusedContainerColor = Color.White,
                                        unfocusedBorderColor = Color.Black,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Table Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF2196F3)) // Blue header
                                .border(1.dp, Color.Black)
                        ) {
                            TableHeaderCell("Nama", 2f)
                            TableHeaderCell("Posisi", 1.5f)
                            TableHeaderCell("Alamat", 1.5f)
                            TableHeaderCell("Umur", 0.8f)
                            TableHeaderCell("Aksi", 0.8f)
                        }
                        
                        // Table Body
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color.Black) // Border for the list container
                        ) {
                            items(uiState.employees) { employee ->
                                TableRow(employee, onEditClick, { showDeleteDialog = it })
                                HorizontalDivider(color = Color.Black, thickness = 1.dp)
                            }
                            // Empty rows filler if needed (optional)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Pagination
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Showing 1 to ${uiState.employees.size} of ${uiState.employees.size} entries",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            
                            Row {
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(4.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Previous", color = Color.Black, fontSize = 12.sp)
                                }
                                
                                Spacer(modifier = Modifier.width(4.dp))
                                
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0000FF)), // Blue active
                                    shape = RoundedCornerShape(4.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("1", color = Color.White, fontSize = 12.sp)
                                }
                                
                                Spacer(modifier = Modifier.width(4.dp))
                                
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(4.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Next", color = Color.Black, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
     showDeleteDialog?.let { id ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text(stringResource(R.string.confirm_delete)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteEmployee(id)
                        showDeleteDialog = null
                    }
                ) {
                    Text(stringResource(R.string.yes), color = Error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }
}

@Composable
fun RowScope.TableHeaderCell(text: String, weight: Float) {
    Box(
        modifier = Modifier
            .weight(weight)
            .height(40.dp) // Fixed header height
            .border(width = 0.5.dp, color = Color.Black), // Inner borders
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TableRow(
    employee: Employee,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
         TableCell(employee.name, 2f)
         TableCell(employee.position, 1.5f)
         TableCell(employee.address, 1.5f) // Address field
         TableCell(employee.age.toString(), 0.8f) // Age field
         
         // Actions
         Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
                .border(width = 0.5.dp, color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
             Column {
                  // Edit Icon (Greenish Arrow)
                 IconButton(onClick = { onEdit(employee.id) }, modifier = Modifier.size(24.dp)) {
                      Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF008080)) // Cyan/Teal
                 }
                  // Delete Icon (optional, not strictly in mockup but typical)
             }
        }
    }
}

@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    Box(
        modifier = Modifier
             .weight(weight)
             .fillMaxHeight()
             .padding(vertical = 8.dp) // Auto height
             .border(width = 0.5.dp, color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
             modifier = Modifier.padding(4.dp)
        )
    }
}

object ColorUtils {
    val cardBackground = Color.White
}
