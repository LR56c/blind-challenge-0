package banking_system.presentation.console

import banking_system.di.Dependencies
import shared.Context
import shared.presentation.AuthenticationCmd
import shared.runIfAuthenticated

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
						accountDao = dependencies.accountDao,
						authenticationRepository = dependencies.authenticationRepository,
						maxAttemps = 3
					)

					"2"  -> runIfAuthenticated { BankCmd.run(accountDao = dependencies.accountDao) }
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
