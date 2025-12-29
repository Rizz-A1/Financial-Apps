package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.EmployeeDao
import com.rizkyfadilhanif.financial.data.local.dao.KasbonDao
import com.rizkyfadilhanif.financial.data.local.entity.KasbonEntity
import com.rizkyfadilhanif.financial.domain.model.Kasbon
import com.rizkyfadilhanif.financial.domain.repository.KasbonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KasbonRepositoryImpl(
    private val kasbonDao: KasbonDao,
    private val employeeDao: EmployeeDao
) : KasbonRepository {
    
    override fun getAllKasbon(): Flow<List<Kasbon>> {
        return kasbonDao.getAllKasbon().map { list ->
            list.map { entity ->
                val employee = employeeDao.getEmployeeById(entity.employeeId)
                entity.toDomain(employee?.name ?: "")
            }
        }
    }
    
    override fun getKasbonByEmployee(employeeId: Long): Flow<List<Kasbon>> {
        return kasbonDao.getKasbonByEmployee(employeeId).map { list ->
            list.map { entity ->
                val employee = employeeDao.getEmployeeById(entity.employeeId)
                entity.toDomain(employee?.name ?: "")
            }
        }
    }
    
    override fun getKasbonByStatus(isPaid: Boolean): Flow<List<Kasbon>> {
        return kasbonDao.getKasbonByStatus(isPaid).map { list ->
            list.map { entity ->
                val employee = employeeDao.getEmployeeById(entity.employeeId)
                entity.toDomain(employee?.name ?: "")
            }
        }
    }
    
    override fun getTotalUnpaidKasbon(): Flow<Double> {
        return kasbonDao.getTotalUnpaidKasbon()
    }
    
    override suspend fun getKasbonById(id: Long): Kasbon? {
        val entity = kasbonDao.getKasbonById(id) ?: return null
        val employee = employeeDao.getEmployeeById(entity.employeeId)
        return entity.toDomain(employee?.name ?: "")
    }
    
    override suspend fun insertKasbon(kasbon: Kasbon): Long {
        return kasbonDao.insertKasbon(kasbon.toEntity())
    }
    
    override suspend fun updateKasbon(kasbon: Kasbon) {
        kasbonDao.updateKasbon(kasbon.toEntity())
    }
    
    override suspend fun deleteKasbon(id: Long) {
        kasbonDao.deleteKasbonById(id)
    }
    
    override suspend fun markAsPaid(id: Long) {
        kasbonDao.markAsPaid(id, System.currentTimeMillis())
    }
    
    private fun KasbonEntity.toDomain(employeeName: String): Kasbon {
        return Kasbon(
            id = id,
            employeeId = employeeId,
            employeeName = employeeName,
            amount = amount,
            description = description,
            isPaid = isPaid,
            date = date,
            paidDate = paidDate,
            createdAt = createdAt
        )
    }
    
    private fun Kasbon.toEntity(): KasbonEntity {
        return KasbonEntity(
            id = id,
            employeeId = employeeId,
            amount = amount,
            description = description,
            isPaid = isPaid,
            date = date,
            paidDate = paidDate,
            createdAt = createdAt
        )
    }
}
