package finance_management_application.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import banking_system.domain.value_objects.Money
import finance_management_application.domain.dao.FinanceDao
import finance_management_application.domain.entities.User
import shared.domain.value_objects.Username

class InMemoryFinanceData : FinanceDao {

	private val users = mutableMapOf<Username, User>()

	init {
		val username1 = Username.create("user1").getOrNull()!!
		users[username1] = User(
			username = username1, expenses = mutableMapOf(
				"Medical expenses" to mutableListOf(
					Money.create("100").getOrNull()!!,
					Money.create("100").getOrNull()!!,
				),
				"Household expenses" to mutableListOf(
					Money.create("200").getOrNull()!!,
				),
				"Leisure" to mutableListOf(
					Money.create("100").getOrNull()!!,
				),
				"Savings" to mutableListOf(
					Money.create("100").getOrNull()!!,
				),
				"Education" to mutableListOf(
					Money.create("150").getOrNull()!!,
					Money.create("150").getOrNull()!!,
				),
			)
		)
	}

	override fun getAllUsers(): List<User> = users.values.toList()

	override fun getUser(username: Username): Either<Exception, User> = either {
		users[username] ?: throw Exception("User not found")
	}

	override fun updateIncome(
		username: Username, income: Money
	): Either<Exception, Unit> = either {
		val user = getUser(username).bind()
		users[username] = user.copy(income = income)
	}

	override fun addExpense(
		username: Username, category: String, expense: Money
	): Either<Exception, Unit> = either {
		val user = getUser(username).bind()
		ensure(user.expenses.containsKey(category)) { Exception("Category not found") }
		val expenses = user.expenses[category]!!
		expenses.add(expense)
	}
}
