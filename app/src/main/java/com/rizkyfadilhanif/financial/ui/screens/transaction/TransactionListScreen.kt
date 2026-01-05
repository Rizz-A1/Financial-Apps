package com.rizkyfadilhanif.financial.ui.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizkyfadilhanif.financial.R
import com.rizkyfadilhanif.financial.domain.model.ExpenseCategory
import com.rizkyfadilhanif.financial.domain.model.IncomeCategory
import com.rizkyfadilhanif.financial.domain.model.Note
import com.rizkyfadilhanif.financial.domain.model.Transaction
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.ui.components.AppTopBar
import com.rizkyfadilhanif.financial.ui.components.GradientBackground
import com.rizkyfadilhanif.financial.ui.components.formatCurrency
import com.rizkyfadilhanif.financial.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    viewModel: TransactionViewModel,
    transactionType: TransactionType,
    onNavigateBack: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val uiState by viewModel.listState.collectAsState()
    val categoryState by viewModel.categoryState.collectAsState()
    val notesState by viewModel.notesState.collectAsState()
    
    var showDeleteDialog by remember { mutableStateOf<Long?>(null) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showEditNoteDialog by remember { mutableStateOf<Note?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showCount by remember { mutableIntStateOf(10) }
    
    val isIncome = transactionType == TransactionType.INCOME
    
    val title = if (isIncome) {
        stringResource(R.string.menu_pendapatan)
    } else {
        stringResource(R.string.menu_pengeluaran)
    }
    
    val userName = "Rizky" // This should come from session/user data
    
    // Filter transactions based on search
    val filteredTransactions = uiState.transactions.filter {
        it.description.contains(searchQuery, ignoreCase = true) ||
        it.category.contains(searchQuery, ignoreCase = true)
    }.take(showCount)
    
    GradientBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            // Shared Top App Bar
            AppTopBar(
                title = title,
                userName = userName,
                onNavigationClick = onNavigateBack,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
            
            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Sources Card
                item {
                    if (isIncome) {
                        IncomeSourcesCard(
                            categories = categoryState.incomeCategories,
                            transactions = uiState.transactions,
                            onAddCategory = { showAddCategoryDialog = true }
                        )
                    } else {
                        ExpenseSourcesCard(
                            categories = categoryState.expenseCategories,
                            transactions = uiState.transactions,
                            onAddCategory = { showAddCategoryDialog = true }
                        )
                    }
                }
                
                // Add Transaction Button
                item {
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier.wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isIncome) Success else Color.Red
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = if (isIncome) "+ PEMASUKAN" else "+ KELUARAN",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                // Transaction Table Section
                item {
                    TransactionTableSection(
                        transactions = filteredTransactions,
                        showCount = showCount,
                        searchQuery = searchQuery,
                        onShowCountChange = { showCount = it },
                        onSearchQueryChange = { searchQuery = it },
                        onEditClick = onEditClick,
                        onDeleteClick = { showDeleteDialog = it },
                        isIncome = isIncome
                    )
                }
                
                // Notes Section
                items(notesState.notes.take(3)) { note ->
                    NoteCard(
                        note = note,
                        onEditClick = { showEditNoteDialog = note }
                    )
                }
                
                // Add empty note cards if less than 3
                val remainingNotes = 3 - notesState.notes.size
                if (remainingNotes > 0) {
                    items(remainingNotes) { index ->
                        val noteNumber = notesState.notes.size + index + 1
                        EmptyNoteCard(
                            noteNumber = noteNumber,
                            onAddClick = {
                                viewModel.addNote(
                                    Note(
                                        title = "Catatan $noteNumber",
                                        content = "",
                                        type = if (isIncome) "INCOME" else "EXPENSE"
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Delete Confirmation Dialog
    showDeleteDialog?.let { id ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text(stringResource(R.string.confirm_delete)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTransaction(id)
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
    
    // Add Category Dialog
    if (showAddCategoryDialog) {
        AddCategoryDialog(
            onDismiss = { showAddCategoryDialog = false },
            onConfirm = { categoryName ->
                if (isIncome) {
                    viewModel.addIncomeCategory(IncomeCategory(name = categoryName))
                } else {
                    viewModel.addExpenseCategory(ExpenseCategory(name = categoryName))
                }
                showAddCategoryDialog = false
            }
        )
    }
    
    // Edit Note Dialog
    showEditNoteDialog?.let { note ->
        EditNoteDialog(
            note = note,
            onDismiss = { showEditNoteDialog = null },
            onConfirm = { updatedNote ->
                viewModel.updateNote(updatedNote)
                showEditNoteDialog = null
            }
        )
    }
}



@Composable
private fun IncomeSourcesCard(
    categories: List<IncomeCategory>,
    transactions: List<Transaction>,
    onAddCategory: () -> Unit
) {
    // Default categories if empty
    val displayCategories = if (categories.isEmpty()) {
        listOf(
            IncomeCategory(name = "Penjualan Sparepart"),
            IncomeCategory(name = "Service Komputer"),
            IncomeCategory(name = "Instalasi Software"),
            IncomeCategory(name = "Service Printer"),
            IncomeCategory(name = "Lain Lain")
        )
    } else {
        categories
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.sumber_pendapatan),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            displayCategories.forEach { category ->
                // Calculate total for this category
                val categoryTransactions = transactions
                    .filter { it.category.equals(category.name, ignoreCase = true) }
                
                val categoryTotal = categoryTransactions.sumOf { it.amount }
                val categoryCount = categoryTransactions.size
                
                CategoryProgressItem(
                    name = category.name,
                    amount = categoryTotal,
                    targetAmount = category.targetAmount,
                    count = categoryCount,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Add new category button
            TextButton(
                onClick = onAddCategory,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Primary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.add_category), color = Primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ExpenseSourcesCard(
    categories: List<ExpenseCategory>,
    transactions: List<Transaction>,
    onAddCategory: () -> Unit
) {
    // Default categories if empty
    val displayCategories = if (categories.isEmpty()) {
        listOf(
            ExpenseCategory(name = "Mouse"),
            ExpenseCategory(name = "Processor(CPU)"),
            ExpenseCategory(name = "Motherboard"),
            ExpenseCategory(name = "Hard Drive / SSD"),
            ExpenseCategory(name = "Lain Lain")
        )
    } else {
        categories
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.sumber_pengeluaran),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            displayCategories.forEach { category ->
                // Calculate total for this category
                val categoryTransactions = transactions
                    .filter { it.category.equals(category.name, ignoreCase = true) }
                
                val categoryTotal = categoryTransactions.sumOf { it.amount }
                val categoryCount = categoryTransactions.size
                
                CategoryProgressItem(
                    name = category.name,
                    amount = categoryTotal,
                    targetAmount = category.targetAmount,
                    count = categoryCount,
                    color = Error // Red for Expense
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Add new category button
            TextButton(
                onClick = onAddCategory,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Error)
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.add_category), color = Error, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CategoryProgressItem(
    name: String,
    amount: Double,
    targetAmount: Double,
    count: Int,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = formatCurrency(amount),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Modern Progress bar with StrokeCap.Round
        val progress = if (targetAmount > 0) (amount / targetAmount).coerceIn(0.0, 1.0) else 0.05 // small filling if 0 target but has value? or just 0
        
        // Mockup shows "X Kali" below the bar or aligned
        // Let's create a custom bar layout
        
        Column(modifier = Modifier.fillMaxWidth()) {
            LinearProgressIndicator(
                progress = { progress.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = Color.LightGray.copy(alpha = 0.3f),
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "$count Kali", 
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionTableSection(
    transactions: List<Transaction>,
    showCount: Int,
    searchQuery: String,
    onShowCountChange: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    isIncome: Boolean
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Column {
        Text(
            text = if (isIncome) stringResource(R.string.transaksi_masuk) else stringResource(R.string.transaksi_keluar),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search and Show controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Show dropdown
            Column {
                 Text(
                     text = stringResource(R.string.show), 
                     style = MaterialTheme.typography.bodySmall,
                     color = TextSecondary
                 )
                 Spacer(modifier = Modifier.height(4.dp))
                 
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = showCount.toString(),
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .width(100.dp)
                            .height(48.dp)
                            .menuAnchor(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                         trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf(5, 10, 25, 50).forEach { count ->
                            DropdownMenuItem(
                                text = { Text(count.toString()) },
                                onClick = {
                                    onShowCountChange(count)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            // Search field
            Column {
                Text(
                    text = stringResource(R.string.search), 
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Table Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
             elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                // Header row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Surface)
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isIncome) stringResource(R.string.id_pemasukan) else stringResource(R.string.id_pengeluaran),
                        modifier = Modifier.weight(1.2f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                    VerticalDivider()
                    Text(
                        text = stringResource(R.string.hint_date),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                    VerticalDivider()
                    Text(
                        text = stringResource(R.string.hint_amount),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        textAlign = TextAlign.End, // Align Right
                        fontSize = 11.sp
                    )
                    VerticalDivider()
                    Text(
                        text = stringResource(R.string.sumber),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                    VerticalDivider()
                    Text(
                        text = stringResource(R.string.aksi),
                        modifier = Modifier.weight(0.5f),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                         fontSize = 11.sp
                    )
                }
                
                HorizontalDivider(color = Color.LightGray)
                
                // Data rows
                if (transactions.isEmpty()) {
                     Box(
                         modifier = Modifier
                             .fillMaxWidth()
                             .height(60.dp),
                         contentAlignment = Alignment.Center
                     ) {
                         Text(
                             text = stringResource(R.string.no_data),
                             style = MaterialTheme.typography.bodySmall,
                             color = TextSecondary
                         )
                     }
                } else {
                    transactions.forEachIndexed { index, transaction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = transaction.id.toString(),
                                modifier = Modifier.weight(1.2f),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp
                            )
                             // No vertical divider in rows for cleaner look, or subtle one? 
                             // Plan said "remove black border", impl plan said "Vertical Dividers: Color LightGray".
                             // Let's keep them but make them very subtle or just space?
                             // Modern tables often drop vertical dividers in rows. 
                             // Let's drop them in ROWS for cleaner look, but keep space.
                             Spacer(modifier = Modifier.width(4.dp)) 
                            Text(
                                text = dateFormat.format(Date(transaction.date)),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp
                            )
                             Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = formatCurrency(transaction.amount),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if(isIncome) Success else Error,
                                textAlign = TextAlign.End,
                                fontSize = 12.sp
                            )
                             Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = transaction.category,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp
                            )
                             Spacer(modifier = Modifier.width(4.dp))
                            Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                                IconButton(
                                    onClick = { onEditClick(transaction.id) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        if (index < transactions.size - 1) {
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(16.dp) // Fixed height for header divider
            .background(Color.LightGray)
    )
}


@Composable
private fun NoteCard(
    note: Note,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEditClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content.ifEmpty { "........." },
                style = MaterialTheme.typography.bodyMedium,
                color = if (note.content.isEmpty()) TextSecondary else TextPrimary,
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun EmptyNoteCard(
    noteNumber: Int,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onAddClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Catatan $noteNumber",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ".........",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_category)) },
        text = {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text(stringResource(R.string.category_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (categoryName.isNotBlank()) onConfirm(categoryName) },
                enabled = categoryName.isNotBlank()
            ) {
                Text(stringResource(R.string.btn_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_cancel))
            }
        }
    )
}

@Composable
private fun EditNoteDialog(
    note: Note,
    onDismiss: () -> Unit,
    onConfirm: (Note) -> Unit
) {
    var content by remember { mutableStateOf(note.content) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(note.title) },
        text = {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(stringResource(R.string.note_content)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(note.copy(content = content)) }
            ) {
                Text(stringResource(R.string.btn_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_cancel))
            }
        }
    )
}
