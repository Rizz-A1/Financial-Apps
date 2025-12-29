package com.rizkyfadilhanif.financial.ui.screens.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyfadilhanif.financial.domain.model.Employee
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class EmployeeListUiState(
    val employees: List<Employee> = emptyList(),
    val isLoading: Boolean = true
)

data class EmployeeFormUiState(
    val id: Long = 0,
    val name: String = "",
    val position: String = "",
    val salary: String = "",
    val phone: String = "",
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class EmployeeViewModel(
    private val repository: EmployeeRepository
) : ViewModel() {
    
    private val _listState = MutableStateFlow(EmployeeListUiState())
    val listState: StateFlow<EmployeeListUiState> = _listState.asStateFlow()
    
    private val _formState = MutableStateFlow(EmployeeFormUiState())
    val formState: StateFlow<EmployeeFormUiState> = _formState.asStateFlow()
    
    init {
        loadEmployees()
    }
    
    private fun loadEmployees() {
        viewModelScope.launch {
            repository.getAllEmployees()
                .collect { employees ->
                    _listState.value = EmployeeListUiState(
                        employees = employees,
                        isLoading = false
                    )
                }
        }
    }
    
    fun loadEmployee(id: Long) {
        viewModelScope.launch {
            val employee = repository.getEmployeeById(id)
            employee?.let {
                _formState.value = EmployeeFormUiState(
                    id = it.id,
                    name = it.name,
                    position = it.position,
                    salary = it.salary.toString(),
                    phone = it.phone
                )
            }
        }
    }
    
    fun updateName(name: String) {
        _formState.update { it.copy(name = name, error = null) }
    }
    
    fun updatePosition(position: String) {
        _formState.update { it.copy(position = position, error = null) }
    }
    
    fun updateSalary(salary: String) {
        _formState.update { it.copy(salary = salary, error = null) }
    }
    
    fun updatePhone(phone: String) {
        _formState.update { it.copy(phone = phone, error = null) }
    }
    
    fun saveEmployee() {
        val state = _formState.value
        
        if (state.name.isBlank()) {
            _formState.update { it.copy(error = "Nama tidak boleh kosong") }
            return
        }
        
        val salary = state.salary.toDoubleOrNull() ?: 0.0
        
        viewModelScope.launch {
            _formState.update { it.copy(isSaving = true) }
            
            try {
                val employee = Employee(
                    id = state.id,
                    name = state.name,
                    position = state.position,
                    salary = salary,
                    phone = state.phone
                )
                
                if (state.id == 0L) {
                    repository.insertEmployee(employee)
                } else {
                    repository.updateEmployee(employee)
                }
                
                _formState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _formState.update { it.copy(isSaving = false, error = e.message) }
            }
        }
    }
    
    fun deleteEmployee(id: Long) {
        viewModelScope.launch {
            repository.deleteEmployee(id)
        }
    }
    
    fun resetForm() {
        _formState.value = EmployeeFormUiState()
    }
    
    class Factory(
        private val repository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmployeeViewModel(repository) as T
        }
    }
}
