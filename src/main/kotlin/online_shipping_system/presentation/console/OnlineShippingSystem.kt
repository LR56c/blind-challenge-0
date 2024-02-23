package online_shipping_system.presentation.console

import online_shipping_system.di.Dependencies
import shared.presentation.AuthenticationLoginCmd
import shared.runIfAuthenticated

class OnlineShippingSystem {
	companion object {
		fun run(dependencies: Dependencies) {
			var exit = false
			println("Please enter your credentials")
			val lock = !AuthenticationLoginCmd.run(
				authenticationRepository = dependencies.authenticationRepository,
				maxAttemps = 3
			)
			while (!exit && !lock) {
				println("Welcome to the Online Shipping System")
				println("Please enter your command")
				println("1. Send package")
				println("2. Exit")
				val input = readLine()
				when (input) {
					"1"  -> runIfAuthenticated(dependencies.authenticationRepository) {
						exit = SendPackageCmd.run()
					}

					"2"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
			if (lock) {
				println("System locked. Try again later.")
			}
		}
	}
}
