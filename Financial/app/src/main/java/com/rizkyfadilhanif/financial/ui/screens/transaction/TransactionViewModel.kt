package com.rizkyfadilhanif.financial.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.Transaction
import com.rizkyfadilhanif.financial.domain.model.TransactionType
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

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val transactionType: TransactionType
) : ViewModel() {
    
    private val _listState = MutableStateFlow(TransactionListUiState())
    val listState: StateFlow<TransactionListUiState> = _listState.asStateFlow()
    
    private val _formState = MutableStateFlow(TransactionFormUiState())
    val formState: StateFlow<TransactionFormUiState> = _formState.asStateFlow()
    
    init {
        loadTransactions()
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
    
    class Factory(
        private val repository: TransactionRepository,
        private val transactionType: TransactionType
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TransactionViewModel(repository, transactionType) as T
        }
    }
}
