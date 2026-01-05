package com.rizkyfadilhanif.financial

import android.app.Application
import com.rizkyfadilhanif.financial.data.local.FinancialDatabase
import com.rizkyfadilhanif.financial.data.local.SessionManager
import com.rizkyfadilhanif.financial.data.repository.EmployeeRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.ExpenseCategoryRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.IncomeCategoryRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.KasbonRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.NoteRepositoryImpl
import com.rizkyfadilhanif.financial.data.repository.TransactionRepositoryImpl
import com.rizkyfadilhanif.financial.domain.repository.EmployeeRepository
import com.rizkyfadilhanif.financial.domain.repository.ExpenseCategoryRepository
import com.rizkyfadilhanif.financial.domain.repository.IncomeCategoryRepository
import com.rizkyfadilhanif.financial.domain.repository.KasbonRepository
import com.rizkyfadilhanif.financial.domain.repository.NoteRepository
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
    
    val incomeCategoryRepository: IncomeCategoryRepository by lazy {
        IncomeCategoryRepositoryImpl(database.incomeCategoryDao())
    }
    
    val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(database.noteDao())
    }
    
    val expenseCategoryRepository: ExpenseCategoryRepository by lazy {
        ExpenseCategoryRepositoryImpl(database.expenseCategoryDao())
    }
}
