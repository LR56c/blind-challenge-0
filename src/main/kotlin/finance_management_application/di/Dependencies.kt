package finance_management_application.di

import finance_management_application.domain.dao.FinanceDao

interface Dependencies {
	val financeDao: FinanceDao
}
