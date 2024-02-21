package banking_system.presentation

import banking_system.di.Dependencies
import features.account.domain.value_objects.Money
import shared.Context
import shared.presentation.AuthenticationCmd

class BankingSystem {
	companion object {
		fun run(dependencies: Dependencies) {
			var exit = false

			while (!exit) {
				println("Welcome to the Banking System")
				println("Please enter your command")
				println("1. Authenticate")
				println("2. Bank")
				println("3. Exit")
				val input = readLine()
				when (input) {
					"1"  -> AuthenticationCmd.run(
						authenticationRepository = dependencies.authenticationRepository,
						maxAttemps = 3
					)

					"2"  -> if (Context.currentAccount == null) {
						println("Please authenticate first")
					} else BankCmd.run(
						accountDao = dependencies.accountDao
					)

					"3"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
		}
	}
}
