package com.rizkyfadilhanif.financial.ui.screens.kasbon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.Employee
import com.rizkyfadilhanif.financial.domain.model.Kasbon
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import com.rizkyfadilhanif.financial.domain.repository.KasbonRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class KasbonListUiState(
    val kasbonList: List<Kasbon> = emptyList(),
    val isLoading: Boolean = true
)

data class KasbonFormUiState(
    val id: Long = 0,
    val employeeId: Long = 0,
    val amount: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val isPaid: Boolean = false,
    val employees: List<Employee> = emptyList(),
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class KasbonViewModel(
    private val kasbonRepository: KasbonRepository,
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    
    private val _listState = MutableStateFlow(KasbonListUiState())
    val listState: StateFlow<KasbonListUiState> = _listState.asStateFlow()
    
    private val _formState = MutableStateFlow(KasbonFormUiState())
    val formState: StateFlow<KasbonFormUiState> = _formState.asStateFlow()
    
    init {
        loadKasbon()
        loadEmployees()
    }
    
    private fun loadKasbon() {
        viewModelScope.launch {
            kasbonRepository.getAllKasbon()
                .collect { list ->
                    _listState.value = KasbonListUiState(
                        kasbonList = list,
                        isLoading = false
                    )
                }
        }
    }
    
    private fun loadEmployees() {
        viewModelScope.launch {
            employeeRepository.getAllEmployees()
                .collect { employees ->
                    _formState.update { it.copy(employees = employees) }
                }
        }
    }
    
    fun loadKasbon(id: Long) {
        viewModelScope.launch {
            val kasbon = kasbonRepository.getKasbonById(id)
            kasbon?.let {
                _formState.update { state ->
                    state.copy(
                        id = it.id,
                        employeeId = it.employeeId,
                        amount = it.amount.toString(),
                        description = it.description,
                        date = it.date,
                        isPaid = it.isPaid
                    )
                }
            }
        }
    }
    
    fun updateEmployeeId(employeeId: Long) {
        _formState.update { it.copy(employeeId = employeeId, error = null) }
    }
    
    fun updateAmount(amount: String) {
        _formState.update { it.copy(amount = amount, error = null) }
    }
    
    fun updateDescription(description: String) {
        _formState.update { it.copy(description = description, error = null) }
    }
    
    fun updateDate(date: Long) {
        _formState.update { it.copy(date = date, error = null) }
    }
    
    fun saveKasbon() {
        val state = _formState.value
        
        if (state.employeeId == 0L) {
            _formState.update { it.copy(error = "Pilih karyawan") }
            return
        }
        
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _formState.update { it.copy(error = "Jumlah tidak valid") }
            return
        }
        
        viewModelScope.launch {
            _formState.update { it.copy(isSaving = true) }
            
            try {
                val kasbon = Kasbon(
                    id = state.id,
                    employeeId = state.employeeId,
                    amount = amount,
                    description = state.description,
                    date = state.date,
                    isPaid = state.isPaid
                )
                
                if (state.id == 0L) {
                    kasbonRepository.insertKasbon(kasbon)
                } else {
                    kasbonRepository.updateKasbon(kasbon)
                }
                
                _formState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _formState.update { it.copy(isSaving = false, error = e.message) }
            }
        }
    }
    
    fun markAsPaid(id: Long) {
        viewModelScope.launch {
            kasbonRepository.markAsPaid(id)
        }
    }
    
    fun deleteKasbon(id: Long) {
        viewModelScope.launch {
            kasbonRepository.deleteKasbon(id)
        }
    }
    
    fun resetForm() {
        _formState.update { 
            KasbonFormUiState(employees = it.employees) 
        }
    }
    
    class Factory(
        private val kasbonRepository: KasbonRepository,
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return KasbonViewModel(kasbonRepository, employeeRepository) as T
        }
    }
}
