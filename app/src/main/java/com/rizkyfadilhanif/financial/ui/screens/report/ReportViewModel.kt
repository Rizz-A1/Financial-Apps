package com.rizkyfadilhanif.financial.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

data class ReportUiState(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val profit: Double = 0.0,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val isLoading: Boolean = true
)

class ReportViewModel(
    private val repository: TransactionRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()
    
    init {
        // Default to current month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startDate = calendar.timeInMillis
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endDate = calendar.timeInMillis
        
        _uiState.value = _uiState.value.copy(startDate = startDate, endDate = endDate)
        loadReportData(startDate, endDate)
    }
    
    fun updateDateRange(startDate: Long, endDate: Long) {
        _uiState.update { it.copy(startDate = startDate, endDate = endDate, isLoading = true) }
        loadReportData(startDate, endDate)
    }
    
    private fun loadReportData(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            combine(
                repository.getTotalByTypeAndDateRange(TransactionType.INCOME, startDate, endDate),
                repository.getTotalByTypeAndDateRange(TransactionType.EXPENSE, startDate, endDate)
            ) { income, expense ->
                ReportUiState(
                    totalIncome = income,
                    totalExpense = expense,
                    profit = income - expense,
                    startDate = startDate,
                    endDate = endDate,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
    
    class Factory(
        private val repository: TransactionRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReportViewModel(repository) as T
        }
    }
}
