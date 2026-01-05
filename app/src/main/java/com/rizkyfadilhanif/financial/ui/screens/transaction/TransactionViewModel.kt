package com.rizkyfadilhanif.financial.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.ExpenseCategory
import com.rizkyfadilhanif.financial.domain.model.IncomeCategory
import com.rizkyfadilhanif.financial.domain.model.Note
import com.rizkyfadilhanif.financial.domain.model.Transaction
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.domain.repository.ExpenseCategoryRepository
import com.rizkyfadilhanif.financial.domain.repository.IncomeCategoryRepository
import com.rizkyfadilhanif.financial.domain.repository.NoteRepository
import com.rizkyfadilhanif.financial.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TransactionListUiState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true
)

data class TransactionFormUiState(
    val id: Long = 0,
    val amount: String = "",
    val description: String = "",
    val category: String = "",
    val date: Long = System.currentTimeMillis(),
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

// Generic category UI state that works for both income and expense
data class CategoryUiState(
    val incomeCategories: List<IncomeCategory> = emptyList(),
    val expenseCategories: List<ExpenseCategory> = emptyList(),
    val isLoading: Boolean = true
)

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true
)

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val transactionType: TransactionType,
    private val incomeCategoryRepository: IncomeCategoryRepository? = null,
    private val expenseCategoryRepository: ExpenseCategoryRepository? = null,
    private val noteRepository: NoteRepository? = null
) : ViewModel() {
    
    private val _listState = MutableStateFlow(TransactionListUiState())
    val listState: StateFlow<TransactionListUiState> = _listState.asStateFlow()
    
    private val _formState = MutableStateFlow(TransactionFormUiState())
    val formState: StateFlow<TransactionFormUiState> = _formState.asStateFlow()
    
    private val _categoryState = MutableStateFlow(CategoryUiState())
    val categoryState: StateFlow<CategoryUiState> = _categoryState.asStateFlow()
    
    private val _notesState = MutableStateFlow(NotesUiState())
    val notesState: StateFlow<NotesUiState> = _notesState.asStateFlow()
    
    init {
        loadTransactions()
        loadCategories()
        loadNotes()
    }
    
    private fun loadTransactions() {
        viewModelScope.launch {
            repository.getTransactionsByType(transactionType)
                .collect { transactions ->
                    _listState.value = TransactionListUiState(
                        transactions = transactions,
                        isLoading = false
                    )
                }
        }
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            if (transactionType == TransactionType.INCOME) {
                incomeCategoryRepository?.getAllCategories()?.collect { categories ->
                    _categoryState.value = _categoryState.value.copy(
                        incomeCategories = categories,
                        isLoading = false
                    )
                }
            } else {
                expenseCategoryRepository?.getAllCategories()?.collect { categories ->
                    _categoryState.value = _categoryState.value.copy(
                        expenseCategories = categories,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun loadNotes() {
        val noteType = if (transactionType == TransactionType.INCOME) "INCOME" else "EXPENSE"
        viewModelScope.launch {
            noteRepository?.getNotesByType(noteType)?.collect { notes ->
                _notesState.value = NotesUiState(
                    notes = notes,
                    isLoading = false
                )
            }
        }
    }
    
    fun loadTransaction(id: Long) {
        viewModelScope.launch {
            val transaction = repository.getTransactionById(id)
            transaction?.let {
                _formState.value = TransactionFormUiState(
                    id = it.id,
                    amount = it.amount.toString(),
                    description = it.description,
                    category = it.category,
                    date = it.date
                )
            }
        }
    }
    
    fun updateAmount(amount: String) {
        _formState.update { it.copy(amount = amount, error = null) }
    }
    
    fun updateDescription(description: String) {
        _formState.update { it.copy(description = description, error = null) }
    }
    
    fun updateCategory(category: String) {
        _formState.update { it.copy(category = category, error = null) }
    }
    
    fun updateDate(date: Long) {
        _formState.update { it.copy(date = date, error = null) }
    }
    
    fun saveTransaction() {
        val state = _formState.value
        
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _formState.update { it.copy(error = "Jumlah tidak valid") }
            return
        }
        
        if (state.description.isBlank()) {
            _formState.update { it.copy(error = "Deskripsi tidak boleh kosong") }
            return
        }
        
        viewModelScope.launch {
            _formState.update { it.copy(isSaving = true) }
            
            try {
                val transaction = Transaction(
                    id = state.id,
                    type = transactionType,
                    amount = amount,
                    description = state.description,
                    category = state.category.ifBlank { "Umum" },
                    date = state.date
                )
                
                if (state.id == 0L) {
                    repository.insertTransaction(transaction)
                } else {
                    repository.updateTransaction(transaction)
                }
                
                _formState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _formState.update { it.copy(isSaving = false, error = e.message) }
            }
        }
    }
    
    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
        }
    }
    
    fun resetForm() {
        _formState.value = TransactionFormUiState()
    }
    
    // Income Category operations
    fun addIncomeCategory(category: IncomeCategory) {
        viewModelScope.launch {
            incomeCategoryRepository?.insertCategory(category)
        }
    }
    
    fun updateIncomeCategory(category: IncomeCategory) {
        viewModelScope.launch {
            incomeCategoryRepository?.updateCategory(category)
        }
    }
    
    fun deleteIncomeCategory(id: Long) {
        viewModelScope.launch {
            incomeCategoryRepository?.deleteCategory(id)
        }
    }
    
    // Expense Category operations
    fun addExpenseCategory(category: ExpenseCategory) {
        viewModelScope.launch {
            expenseCategoryRepository?.insertCategory(category)
        }
    }
    
    fun updateExpenseCategory(category: ExpenseCategory) {
        viewModelScope.launch {
            expenseCategoryRepository?.updateCategory(category)
        }
    }
    
    fun deleteExpenseCategory(id: Long) {
        viewModelScope.launch {
            expenseCategoryRepository?.deleteCategory(id)
        }
    }
    
    // Note operations
    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository?.insertNote(note)
        }
    }
    
    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository?.updateNote(note)
        }
    }
    
    fun deleteNote(id: Long) {
        viewModelScope.launch {
            noteRepository?.deleteNote(id)
        }
    }
    
    class Factory(
        private val repository: TransactionRepository,
        private val transactionType: TransactionType,
        private val incomeCategoryRepository: IncomeCategoryRepository? = null,
        private val expenseCategoryRepository: ExpenseCategoryRepository? = null,
        private val noteRepository: NoteRepository? = null
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TransactionViewModel(
                repository,
                transactionType,
                incomeCategoryRepository,
                expenseCategoryRepository,
                noteRepository
            ) as T
        }
    }
}
