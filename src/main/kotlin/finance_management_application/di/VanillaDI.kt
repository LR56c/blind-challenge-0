package finance_management_application.di

import finance_management_application.domain.dao.FinanceDao
import finance_management_application.infrastructure.InMemoryFinanceData

fun vanillaDI(): Dependencies = object : Dependencies {
	override val financeDao: FinanceDao = InMemoryFinanceData()
}
