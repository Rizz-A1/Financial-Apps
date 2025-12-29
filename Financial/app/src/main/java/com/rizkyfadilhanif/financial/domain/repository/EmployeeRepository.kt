package com.rizkyfadilhanif.financial.domain.repository

import com.rizkyfadilhanif.financial.domain.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getAllEmployees(): Flow<List<Employee>>
    fun getEmployeeCount(): Flow<Int>
    suspend fun getEmployeeById(id: Long): Employee?
    suspend fun insertEmployee(employee: Employee): Long
    suspend fun updateEmployee(employee: Employee)
    suspend fun deleteEmployee(id: Long)
}
