package com.rizkyfadilhanif.financial.data.local.dao

import androidx.room.*
import com.rizkyfadilhanif.financial.data.local.entity.KasbonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KasbonDao {
    @Query("SELECT * FROM kasbon ORDER BY date DESC")
    fun getAllKasbon(): Flow<List<KasbonEntity>>
    
    @Query("SELECT * FROM kasbon WHERE employeeId = :employeeId ORDER BY date DESC")
    fun getKasbonByEmployee(employeeId: Long): Flow<List<KasbonEntity>>
    
    @Query("SELECT * FROM kasbon WHERE isPaid = :isPaid ORDER BY date DESC")
    fun getKasbonByStatus(isPaid: Boolean): Flow<List<KasbonEntity>>
    
    @Query("SELECT * FROM kasbon WHERE id = :id")
    suspend fun getKasbonById(id: Long): KasbonEntity?
    
    @Query("SELECT COALESCE(SUM(amount), 0) FROM kasbon WHERE isPaid = 0")
    fun getTotalUnpaidKasbon(): Flow<Double>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKasbon(kasbon: KasbonEntity): Long
    
    @Update
    suspend fun updateKasbon(kasbon: KasbonEntity)
    
    @Delete
    suspend fun deleteKasbon(kasbon: KasbonEntity)
    
    @Query("DELETE FROM kasbon WHERE id = :id")
    suspend fun deleteKasbonById(id: Long)
    
    @Query("UPDATE kasbon SET isPaid = 1, paidDate = :paidDate WHERE id = :id")
    suspend fun markAsPaid(id: Long, paidDate: Long)
}
