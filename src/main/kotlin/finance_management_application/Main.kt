package finance_management_application

import finance_management_application.di.vanillaDI
import finance_management_application.presentation.console.FinanceManagementSystem

fun main() {
	FinanceManagementSystem.run(vanillaDI())
}
