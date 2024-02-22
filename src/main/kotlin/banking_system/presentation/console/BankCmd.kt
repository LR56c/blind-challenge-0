package banking_system.presentation.console

import arrow.core.Either
import banking_system.application.Transfer
import features.account.domain.dao.AccountDao
import features.account.domain.entities.Account
import features.account.domain.value_objects.Money
import shared.Context
import shared.presentation.moneyRequest
import shared.presentation.usernameRequest

class BankCmd {
	companion object {
		/*
			viendolo desde el lado android. se necesitaria un viewmodel (por ui dinamica)
		  y un state. donde por 'di' se le pasaria dependencias a viewmodel y
		  este ejecutaria y lo reflejaria en la 'presentacion'
		 */
		fun run(accountDao: AccountDao) {
			var exit = false
			while (!exit) {
				println(accountDao.get())
				println("Welcome to the Bank")
				println("Please enter your command")
				println("1. Deposit")
				println("2. Withdraw")
				println("3. Transfer")
				println("4. View Balance")
				println("5. Return")
				val input = readLine()
				val account = Context.currentAccount!!
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

		private fun accountRequest(
			currentAccount: Account, accountDao: AccountDao
		): Account {
			var account: Account? = null
			while (account == null) {
				val usernameCheck = usernameRequest(
					"Please enter the account username",
					"Username cant be empty. Please enter a valid username."
				)

				if (currentAccount.username == usernameCheck) {
					println("You can't transfer to yourself. Please enter a valid account.")
				} else {
					when (val accountCheck = accountDao.getAccount(usernameCheck)) {
						is Either.Left  -> println("Account not found. Please enter a valid account.")
						is Either.Right -> account = accountCheck.value
					}
				}
			}
			return account
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
