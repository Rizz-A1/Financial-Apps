package com.rizkyfadilhanif.financial.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object IncomeList : Screen("income_list")
    data object AddIncome : Screen("add_income")
    data object EditIncome : Screen("edit_income/{id}") {
        fun createRoute(id: Long) = "edit_income/$id"
    }
    data object ExpenseList : Screen("expense_list")
    data object AddExpense : Screen("add_expense")
    data object EditExpense : Screen("edit_expense/{id}") {
        fun createRoute(id: Long) = "edit_expense/$id"
    }
    data object EmployeeList : Screen("employee_list")
    data object AddEmployee : Screen("add_employee")
    data object EditEmployee : Screen("edit_employee/{id}") {
        fun createRoute(id: Long) = "edit_employee/$id"
    }
    data object KasbonList : Screen("kasbon_list")
    data object AddKasbon : Screen("add_kasbon")
    data object EditKasbon : Screen("edit_kasbon/{id}") {
        fun createRoute(id: Long) = "edit_kasbon/$id"
    }
    data object Report : Screen("report")
}
