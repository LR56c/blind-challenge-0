package banking_system.presentation

import arrow.core.Either
import features.account.domain.dao.AccountDao
import features.account.domain.entities.Account
import features.account.domain.value_objects.Money
import features.account.domain.value_objects.Username
import shared.Context

class BankCmd {
	companion object {
		fun run(accountDao: AccountDao) {
			var exit = false
			while (!exit) {
				println("Welcome to the Bank")
				println("Please enter your command")
				println("1. Deposit")
				println("2. Withdraw")
				println("3. Transfer")
				println("4. View Balance")
				println("5. Exit")
				val input = readLine()
				val account = Context.currentAccount!!
				when (input) {
					"1"  -> DepositAction(account)
					"2"  -> WithdrawAction(account)
					"3"  -> TransferAction(account, accountDao)
					"4"  -> GetBalanceAction(account)
					"5"  -> {
						println("Goodbye")
						exit = true
					}
					else -> println("Invalid command")
				}
			}
		}

		private fun GetBalanceAction(account: Account) {
			println("Your balance is: ${account.getBalance()}")
		}

		//TODO: Faltan varios 'either', mejorar Actions y transpaso dependencia
		private fun TransferAction(
			account: Account,
			accountDao: AccountDao
		) {
			val otherAccount : Account = AccountRequest(accountDao)
			val money: Money = AmountRequest()
			account.transfer(
				amount = money.amount,
				account = Account(username = otherAccount.username)
			)
			accountDao.saveAccount(account)
			accountDao.saveAccount(otherAccount)
		}

		private fun AccountRequest(accountDao: AccountDao): Account {
			println("Please enter the account username")
			var account: Account? = null
			while (account == null) {
				val username = readLine()

				val usernameCheck = Username.create(username ?: "")

				when (usernameCheck) {
					is Either.Right -> Unit
					is Either.Left  -> {
						println("Invalid username. Please enter a valid username.")
						continue
					}
				}
				println("Continuando validacion")
				//TODO: Ver como hacer para que no sea necesario el getOrNull
				val accountValid = accountDao.getAccount(usernameCheck.getOrNull()!!)
				when (accountValid) {
					is Either.Left  -> println("Account not found. Please enter a valid account.")
					is Either.Right -> account = accountValid.value
				}
			}
			return account
		}

		private fun AmountRequest(): Money {
			println("Amount to transfer")
			var money: Money? = null
			while (money == null) {
				val amount = readLine()
				val moneyCheck = Money.create(amount?.toDouble() ?: -1.0)
				when (moneyCheck) {
					is Either.Left  -> println("Invalid amount. Please enter a valid amount.")
					is Either.Right -> money = moneyCheck.value
				}
			}
			return money
		}

		private fun WithdrawAction(account: Account) {
			println("Amount to withdraw")
			val money : Money = AmountRequest()
			account.withdraw(money.amount)
		}

		private fun DepositAction(account: Account) {
			println("Please enter the amount to deposit")
			val money : Money = AmountRequest()
			account.deposit(money.amount)
		}
	}
}
