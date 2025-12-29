package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.EmployeeDao
import com.rizkyfadilhanif.financial.data.local.entity.EmployeeEntity
import com.rizkyfadilhanif.financial.domain.model.Employee
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmployeeRepositoryImpl(
    private val employeeDao: EmployeeDao
) : EmployeeRepository {
    
    override fun getAllEmployees(): Flow<List<Employee>> {
        return employeeDao.getAllEmployees().map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getEmployeeCount(): Flow<Int> {
        return employeeDao.getEmployeeCount()
    }
    
    override suspend fun getEmployeeById(id: Long): Employee? {
        return employeeDao.getEmployeeById(id)?.toDomain()
    }
    
    override suspend fun insertEmployee(employee: Employee): Long {
        return employeeDao.insertEmployee(employee.toEntity())
    }
    
    override suspend fun updateEmployee(employee: Employee) {
        employeeDao.updateEmployee(employee.toEntity())
    }
    
    override suspend fun deleteEmployee(id: Long) {
        employeeDao.deleteEmployeeById(id)
    }
    
    private fun EmployeeEntity.toDomain(): Employee {
        return Employee(
            id = id,
            name = name,
            position = position,
            salary = salary,
            phone = phone,
            createdAt = createdAt
        )
    }
    
    private fun Employee.toEntity(): EmployeeEntity {
        return EmployeeEntity(
            id = id,
            name = name,
            position = position,
            salary = salary,
            phone = phone,
            createdAt = createdAt
        )
    }
}
