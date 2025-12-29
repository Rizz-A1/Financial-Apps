package com.rizkyfadilhanif.financial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rizkyfadilhanif.financial.FinancialApplication
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.ui.screens.dashboard.DashboardScreen
import com.rizkyfadilhanif.financial.ui.screens.dashboard.DashboardViewModel
import com.rizkyfadilhanif.financial.ui.screens.employee.AddEmployeeScreen
import com.rizkyfadilhanif.financial.ui.screens.employee.EmployeeListScreen
import com.rizkyfadilhanif.financial.ui.screens.employee.EmployeeViewModel
import com.rizkyfadilhanif.financial.ui.screens.kasbon.AddKasbonScreen
import com.rizkyfadilhanif.financial.ui.screens.kasbon.KasbonListScreen
import com.rizkyfadilhanif.financial.ui.screens.kasbon.KasbonViewModel
import com.rizkyfadilhanif.financial.ui.screens.login.LoginScreen
import com.rizkyfadilhanif.financial.ui.screens.report.ReportScreen
import com.rizkyfadilhanif.financial.ui.screens.report.ReportViewModel
import com.rizkyfadilhanif.financial.ui.screens.splash.SplashScreen
import com.rizkyfadilhanif.financial.ui.screens.transaction.AddTransactionScreen
import com.rizkyfadilhanif.financial.ui.screens.transaction.TransactionListScreen
import com.rizkyfadilhanif.financial.ui.screens.transaction.TransactionViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    val application = context.applicationContext as FinancialApplication
    val sessionManager = application.sessionManager
    
    // Determine start destination based on login state
    val startDestination = if (sessionManager.isLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Splash.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onLoginSuccess = { email, name ->
                    sessionManager.saveLoginSession(email, name)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Dashboard Screen
        composable(Screen.Dashboard.route) {
            val dashboardViewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModel.Factory(
                    application.transactionRepository,
                    application.employeeRepository
                )
            )
            
            DashboardScreen(
                viewModel = dashboardViewModel,
                userName = sessionManager.getUserName(),
                onNavigateToIncome = { navController.navigate(Screen.IncomeList.route) },
                onNavigateToExpense = { navController.navigate(Screen.ExpenseList.route) },
                onNavigateToEmployee = { navController.navigate(Screen.EmployeeList.route) },
                onNavigateToKasbon = { navController.navigate(Screen.KasbonList.route) },
                onNavigateToReport = { navController.navigate(Screen.Report.route) },
                onLogout = {
                    sessionManager.logout()
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        
        // Income List
        composable(Screen.IncomeList.route) {
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.INCOME
                )
            )
            
            TransactionListScreen(
                viewModel = viewModel,
                transactionType = TransactionType.INCOME,
                onNavigateBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.AddIncome.route) },
                onEditClick = { id -> navController.navigate(Screen.EditIncome.createRoute(id)) }
            )
        }
        
        // Add Income
        composable(Screen.AddIncome.route) {
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.INCOME
                )
            )
            
            AddTransactionScreen(
                viewModel = viewModel,
                transactionType = TransactionType.INCOME,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Edit Income
        composable(
            route = Screen.EditIncome.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.INCOME
                )
            )
            
            AddTransactionScreen(
                viewModel = viewModel,
                transactionType = TransactionType.INCOME,
                transactionId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Expense List
        composable(Screen.ExpenseList.route) {
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.EXPENSE
                )
            )
            
            TransactionListScreen(
                viewModel = viewModel,
                transactionType = TransactionType.EXPENSE,
                onNavigateBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.AddExpense.route) },
                onEditClick = { id -> navController.navigate(Screen.EditExpense.createRoute(id)) }
            )
        }
        
        // Add Expense
        composable(Screen.AddExpense.route) {
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.EXPENSE
                )
            )
            
            AddTransactionScreen(
                viewModel = viewModel,
                transactionType = TransactionType.EXPENSE,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Edit Expense
        composable(
            route = Screen.EditExpense.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel: TransactionViewModel = viewModel(
                factory = TransactionViewModel.Factory(
                    application.transactionRepository,
                    TransactionType.EXPENSE
                )
            )
            
            AddTransactionScreen(
                viewModel = viewModel,
                transactionType = TransactionType.EXPENSE,
                transactionId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Employee List
        composable(Screen.EmployeeList.route) {
            val viewModel: EmployeeViewModel = viewModel(
                factory = EmployeeViewModel.Factory(application.employeeRepository)
            )
            
            EmployeeListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.AddEmployee.route) },
                onEditClick = { id -> navController.navigate(Screen.EditEmployee.createRoute(id)) }
            )
        }
        
        // Add Employee
        composable(Screen.AddEmployee.route) {
            val viewModel: EmployeeViewModel = viewModel(
                factory = EmployeeViewModel.Factory(application.employeeRepository)
            )
            
            AddEmployeeScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Edit Employee
        composable(
            route = Screen.EditEmployee.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel: EmployeeViewModel = viewModel(
                factory = EmployeeViewModel.Factory(application.employeeRepository)
            )
            
            AddEmployeeScreen(
                viewModel = viewModel,
                employeeId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Kasbon List
        composable(Screen.KasbonList.route) {
            val viewModel: KasbonViewModel = viewModel(
                factory = KasbonViewModel.Factory(
                    application.kasbonRepository,
                    application.employeeRepository
                )
            )
            
            KasbonListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onAddClick = { navController.navigate(Screen.AddKasbon.route) },
                onEditClick = { id -> navController.navigate(Screen.EditKasbon.createRoute(id)) }
            )
        }
        
        // Add Kasbon
        composable(Screen.AddKasbon.route) {
            val viewModel: KasbonViewModel = viewModel(
                factory = KasbonViewModel.Factory(
                    application.kasbonRepository,
                    application.employeeRepository
                )
            )
            
            AddKasbonScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Edit Kasbon
        composable(
            route = Screen.EditKasbon.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel: KasbonViewModel = viewModel(
                factory = KasbonViewModel.Factory(
                    application.kasbonRepository,
                    application.employeeRepository
                )
            )
            
            AddKasbonScreen(
                viewModel = viewModel,
                kasbonId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Report
        composable(Screen.Report.route) {
            val viewModel: ReportViewModel = viewModel(
                factory = ReportViewModel.Factory(application.transactionRepository)
            )
            
            ReportScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
