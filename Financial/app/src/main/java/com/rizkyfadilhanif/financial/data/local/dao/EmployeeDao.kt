package com.rizkyfadilhanif.financial.data.local.dao

import androidx.room.*
import com.rizkyfadilhanif.financial.data.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees ORDER BY name ASC")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>
    
    @Query("SELECT * FROM employees WHERE id = :id")
    suspend fun getEmployeeById(id: Long): EmployeeEntity?
    
    @Query("SELECT COUNT(*) FROM employees")
    fun getEmployeeCount(): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity): Long
    
    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)
    
    @Delete
    suspend fun deleteEmployee(employee: EmployeeEntity)
    
    @Query("DELETE FROM employees WHERE id = :id")
    suspend fun deleteEmployeeById(id: Long)
}
