package finance_management_application.presentation.console

import finance_management_application.di.Dependencies
import finance_management_application.domain.entities.User
import shared.domain.value_objects.Username
import shared.presentation.moneyRequest

class FinanceManagementSystem {
	companion object {
		fun run(dependencies: Dependencies) {
			// el usuario podria venir en conjunto de la authenticacion y user dao
			// y obtener las categorias desde category dao. incluso se puede separar 'dao' entre sus propias categorias o las 'impuestas'
			// tambien se podria hacer financeService, que calcule. y financeDao que guarde si es necesario

			var exit = false
			while (!exit) {
				val user = dependencies.financeDao.getUser(
					Username.create("user1").getOrNull()!!
				).getOrNull()

				if (user == null) {
					println("User not found")
					exit = true
					continue
				}
				println("Welcome to the Finance Management System")
				println("Please enter your command")
				println("1. Record your total income")
				println("2. List your expenses within the categories")
				println("3. Exit")
				val input = readLine()
				when (input) {
					"1"  -> {
						val moneyIncome = moneyRequest("Enter your total income: ")
						dependencies.financeDao.updateIncome(user.username, moneyIncome)
					}

					"2"  -> listExpensesWithCategories(user)
					"3"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
		}


		private fun listExpensesWithCategories(user: User) {
			if (user.income == null) {
				println("You need to record your total income first. Try again.")
				return
			}
			println("List your expenses within the categories")
			user.expenses.forEach { (category, expenses) ->
				val totalExpenses = expenses.sumOf { it.amount }
				println("$category: $totalExpenses")
			}
			val totalExpenses =
				user.expenses.values.sumOf { expensesList -> expensesList.sumOf { it.amount } }
			val totalIncome = user.income.amount
			val difference = totalIncome - totalExpenses
			when {
				difference == 0.0 -> {
					val maxCategory =
						user.expenses.maxBy { expensesList -> expensesList.value.sumOf { it.amount } }.key
					println("You are spending the same amount of money you earn. You should reduce expenses in the category where you have spent the most money: $maxCategory")
				}

				difference > 0    -> {
					println("You are spending less than you earn. Congratulations!")
				}

				else              -> {
					println("You are spending more than you earn. You should improve your financial health.")
				}
			}
		}
	}
}
