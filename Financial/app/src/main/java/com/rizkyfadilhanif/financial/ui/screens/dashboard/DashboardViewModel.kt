package com.rizkyfadilhanif.financial.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import com.rizkyfadilhanif.financial.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

data class DashboardUiState(
    val userName: String = "Rizky",
    val todayIncome: Double = 0.0,
    val weeklyIncome: Double = 0.0,
    val todayExpense: Double = 0.0,
    val weeklyExpense: Double = 0.0,
    val balance: Double = 0.0,
    val employeeCount: Int = 0,
    val weeklyData: List<Double> = List(7) { 0.0 },
    val isLoading: Boolean = true
)

class DashboardViewModel(
    private val transactionRepository: TransactionRepository,
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            
            // Today start
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val todayStart = calendar.timeInMillis
            
            // Today end
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            val todayEnd = calendar.timeInMillis
            
            // Week start (7 days ago)
            calendar.timeInMillis = todayStart
            calendar.add(Calendar.DAY_OF_YEAR, -6)
            val weekStart = calendar.timeInMillis
            
            // Combine all flows
            combine(
                transactionRepository.getTotalByTypeAndDateRange(TransactionType.INCOME, todayStart, todayEnd),
                transactionRepository.getTotalByTypeAndDateRange(TransactionType.EXPENSE, todayStart, todayEnd),
                transactionRepository.getTotalByTypeAndDateRange(TransactionType.INCOME, weekStart, todayEnd),
                transactionRepository.getTotalByTypeAndDateRange(TransactionType.EXPENSE, weekStart, todayEnd),
                transactionRepository.getTotalByType(TransactionType.INCOME),
                transactionRepository.getTotalByType(TransactionType.EXPENSE),
                employeeRepository.getEmployeeCount()
            ) { values ->
                val todayIncome = values[0] as Double
                val todayExpense = values[1] as Double
                val weeklyIncome = values[2] as Double
                val weeklyExpense = values[3] as Double
                val totalIncome = values[4] as Double
                val totalExpense = values[5] as Double
                val employeeCount = values[6] as Int
                
                DashboardUiState(
                    todayIncome = todayIncome,
                    todayExpense = todayExpense,
                    weeklyIncome = weeklyIncome,
                    weeklyExpense = weeklyExpense,
                    balance = totalIncome - totalExpense,
                    employeeCount = employeeCount,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
        
        // Load weekly data separately
        loadWeeklyData()
    }
    
    private fun loadWeeklyData() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val weeklyData = mutableListOf<Double>()
            
            for (i in 6 downTo 0) {
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.add(Calendar.DAY_OF_YEAR, -i)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val dayStart = calendar.timeInMillis
                
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                val dayEnd = calendar.timeInMillis
                
                transactionRepository.getTotalByTypeAndDateRange(TransactionType.INCOME, dayStart, dayEnd)
                    .first()
                    .let { weeklyData.add(it) }
            }
            
            _uiState.update { it.copy(weeklyData = weeklyData) }
        }
    }
    
    class Factory(
        private val transactionRepository: TransactionRepository,
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(transactionRepository, employeeRepository) as T
        }
    }
}
