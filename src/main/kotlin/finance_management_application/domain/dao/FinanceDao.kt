package finance_management_application.domain.dao

import arrow.core.Either
import banking_system.domain.value_objects.Money
import finance_management_application.domain.entities.User
import shared.domain.value_objects.Username

interface FinanceDao {
	fun getAllUsers(): List<User>
	fun getUser(username: Username): Either<Exception, User>
	fun updateIncome(username: Username, income: Money): Either<Exception, Unit>
	fun addExpense(
		username: Username,
		category: String,
		expense: Money
	): Either<Exception, Unit>
}
