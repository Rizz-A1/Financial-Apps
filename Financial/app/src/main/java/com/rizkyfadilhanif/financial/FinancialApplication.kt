package com.rizkyfadilhanif.financial

import android.app.Application
import com.rizkyfadilhanif.financial.data.local.FinancialDatabase
import com.rizkyfadilhanif.financial.data.local.SessionManager
import com.rizkyfadilhanif.financial.data.repository.EmployeeRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.KasbonRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.TransactionRepositoryImpl
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import com.rizkyfadilhanif.financial.domain.repository.KasbonRepository
import com.rizkyfadilhanif.financial.domain.repository.TransactionRepository

class FinancialApplication : Application() {
    
    private val database by lazy { FinancialDatabase.getInstance(this) }
    
    val sessionManager by lazy { SessionManager(this) }
    
    val transactionRepository: TransactionRepository by lazy {
        TransactionRepositoryImpl(database.transactionDao())
    }
    
    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(database.employeeDao())
    }
    
    val kasbonRepository: KasbonRepository by lazy {
        KasbonRepositoryImpl(database.kasbonDao(), database.employeeDao())
    }
}
