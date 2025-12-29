package com.rizkyfadilhanif.financial.domain.repository

import com.rizkyfadilhanif.financial.domain.model.Kasbon
import kotlinx.coroutines.flow.Flow

interface KasbonRepository {
    fun getAllKasbon(): Flow<List<Kasbon>>
    fun getKasbonByEmployee(employeeId: Long): Flow<List<Kasbon>>
    fun getKasbonByStatus(isPaid: Boolean): Flow<List<Kasbon>>
    fun getTotalUnpaidKasbon(): Flow<Double>
    suspend fun getKasbonById(id: Long): Kasbon?
    suspend fun insertKasbon(kasbon: Kasbon): Long
    suspend fun updateKasbon(kasbon: Kasbon)
    suspend fun deleteKasbon(id: Long)
    suspend fun markAsPaid(id: Long)
}
