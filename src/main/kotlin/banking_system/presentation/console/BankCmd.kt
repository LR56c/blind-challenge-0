package banking_system.presentation.console

import arrow.core.Either
import banking_system.application.Transfer
import banking_system.domain.dao.AccountDao
import banking_system.domain.entities.Account
import banking_system.domain.value_objects.Money
import features.authentication.domain.repository.AuthenticationRepository
import shared.domain.value_objects.Username
import shared.presentation.moneyRequest
import shared.presentation.usernameRequest

class BankCmd {
	companion object {
		/*
			viendolo desde el lado android. se necesitaria un viewmodel (por ui dinamica)
		  y un state. donde por 'di' se le pasaria dependencias a viewmodel y
		  este ejecutaria y lo reflejaria en la 'presentacion'
		 */
		fun run(
			accountDao: AccountDao, authenticationRepository: AuthenticationRepository
		) {
			var exit = false
			while (!exit) {
				val usernameCheck = Username.create(authenticationRepository.token)

				usernameCheck.onLeft {
					println("Username incorrect. Please login again")
					exit = true
				}

				val accountCheck = accountDao.getAccount(usernameCheck.getOrNull()!!)

				accountCheck.onLeft {
					println("Account not found. Please enter a valid account.")
					exit = true
				}

				val account = accountCheck.getOrNull()!!

				println("Welcome to the Bank")
				println("Please enter your command")
				println("1. Deposit")
				println("2. Withdraw")
				println("3. Transfer")
				println("4. View Balance")
				println("5. Return")
				val input = readLine()
				when (input) {
					"1"  -> depositAction(account)
					"2"  -> withdrawAction(account)
					"3"  -> transferAction(account, accountDao)
					"4"  -> getBalanceAction(account)
					"5"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
		}

		private fun getBalanceAction(account: Account) {
			println("Your balance is: ${account.getBalance()}")
		}

		private fun transferAction(
			account: Account, accountDao: AccountDao
		) {
			val otherAccount: Account = accountRequest(account, accountDao)
			val money: Money = moneyRequest("Amount to transfer")
			val transfer = Transfer(accountDao)
			val transferResult = transfer(money, account, otherAccount)
			println("transferResult: $transferResult")
			transferResult.fold({
				println("Transfer failed")
			}, { println("Transfer successful") })
		}

		private fun withdrawAction(account: Account) {
			val money: Money = moneyRequest("Amount to withdraw")
			account.withdraw(money)
		}

		private fun depositAction(account: Account) {
			val money: Money = moneyRequest("Please enter the amount to deposit")
			account.deposit(money)
		}
	}
}
