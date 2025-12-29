package com.rizkyfadilhanif.financial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizkyfadilhanif.financial.data.local.dao.EmployeeDao
import com.rizkyfadilhanif.financial.data.local.dao.KasbonDao
import com.rizkyfadilhanif.financial.data.local.dao.TransactionDao
import com.rizkyfadilhanif.financial.data.local.entity.EmployeeEntity
import com.rizkyfadilhanif.financial.data.local.entity.KasbonEntity
import com.rizkyfadilhanif.financial.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        EmployeeEntity::class,
        KasbonEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinancialDatabase : RoomDatabase() {
    
    abstract fun transactionDao(): TransactionDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun kasbonDao(): KasbonDao
    
    companion object {
        @Volatile
        private var INSTANCE: FinancialDatabase? = null
        
        fun getInstance(context: Context): FinancialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinancialDatabase::class.java,
                    "financial_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
