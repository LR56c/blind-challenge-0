package banking_system.presentation.console

import banking_system.di.Dependencies
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
						authenticationRepository = dependencies.authenticationRepository,
						maxAttemps = 3
					)

					"2"  -> runIfAuthenticated(dependencies.authenticationRepository) {
						BankCmd.run(
							accountDao = dependencies.accountDao,
							authenticationRepository = dependencies.authenticationRepository
						)
					}

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
