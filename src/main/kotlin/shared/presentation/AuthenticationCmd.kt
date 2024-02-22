package shared.presentation

import arrow.core.Either
import shared.domain.value_objects.Password
import shared.domain.value_objects.Username
import features.authentication.domain.repository.AuthenticationRepository

class AuthenticationCmd {
	companion object {
		fun run(
			onAuthSuccess : (Username) -> Unit = {},
			onAuthLogout : () -> Unit = {},
			authenticationRepository: AuthenticationRepository,
			maxAttemps: Int = 1
		): Boolean {
			var exit = false
			while (!exit) {
				println("Welcome to the Authentication System")
				println("Please enter your command")
				println("1. Login")
				println("2. Logout")
				println("3. Return")
				val input = readLine()
				when (input) {
					"1"  -> return loginAction(onAuthSuccess, authenticationRepository, maxAttemps)
					"2"  -> {
						onAuthLogout()
						exit = true
					}

					"3"  -> {
						exit = true
					}

					else -> println("Invalid command")
				}
			}
			return false
		}

		private fun loginAction(
			onAuthSuccess : (Username) -> Unit,
			authenticationRepository: AuthenticationRepository,
			maxAttemps: Int
		): Boolean {
			var currentAttemps = maxAttemps
			while (currentAttemps > 0) {
				val username: Username = usernameRequest(
					"Please enter your username",
					"Username cant be empty. Please enter a valid username."
				)
				val password: Password = passwordRequest()

				when (authenticationRepository.login(username, password)) {
					is Either.Left  -> {
						println("Authentication failed")
						currentAttemps--
					}

					is Either.Right -> {
						println("Authentication successful")
						onAuthSuccess(username)
						return true
					}
				}
			}
			if (currentAttemps == 0) {
				println("Max attemps reached. Try again later.")
			}
			return false
		}

	}
}

