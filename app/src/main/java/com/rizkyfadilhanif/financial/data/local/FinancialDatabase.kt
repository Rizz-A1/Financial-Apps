package com.rizkyfadilhanif.financial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizkyfadilhanif.financial.data.local.dao.EmployeeDao
import com.rizkyfadilhanif.financial.data.local.dao.ExpenseCategoryDao
import com.rizkyfadilhanif.financial.data.local.dao.IncomeCategoryDao
import com.rizkyfadilhanif.financial.data.local.dao.KasbonDao
import com.rizkyfadilhanif.financial.data.local.dao.NoteDao
import com.rizkyfadilhanif.financial.data.local.dao.TransactionDao
import com.rizkyfadilhanif.financial.data.local.entity.EmployeeEntity
import com.rizkyfadilhanif.financial.data.local.entity.ExpenseCategoryEntity
import com.rizkyfadilhanif.financial.data.local.entity.IncomeCategoryEntity
import com.rizkyfadilhanif.financial.data.local.entity.KasbonEntity
import com.rizkyfadilhanif.financial.data.local.entity.NoteEntity
import com.rizkyfadilhanif.financial.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        EmployeeEntity::class,
        KasbonEntity::class,
        IncomeCategoryEntity::class,
        NoteEntity::class,
        ExpenseCategoryEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class FinancialDatabase : RoomDatabase() {
    
    abstract fun transactionDao(): TransactionDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun kasbonDao(): KasbonDao
    abstract fun incomeCategoryDao(): IncomeCategoryDao
    abstract fun noteDao(): NoteDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: FinancialDatabase? = null
        
        fun getInstance(context: Context): FinancialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinancialDatabase::class.java,
                    "financial_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
