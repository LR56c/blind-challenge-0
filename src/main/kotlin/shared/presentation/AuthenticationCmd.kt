package shared.presentation

import arrow.core.Either
import features.account.domain.entities.Account
import features.account.domain.value_objects.Password
import features.account.domain.value_objects.Username
import features.authentication.domain.repository.AuthenticationRepository
import shared.Context

class AuthenticationCmd {
	companion object {
		fun run(
			authenticationRepository: AuthenticationRepository, maxAttemps: Int = 1
		) {
			var currentAttemps = maxAttemps
			while (currentAttemps > 0) {
				println("Welcome to the Authentication System")
				val username: Username = UsernameRequest()
				val password: Password = PasswordRequest()

				when (authenticationRepository.login(username, password)) {
					is Either.Left  -> {
						println("Authentication failed")
						currentAttemps--
					}

					is Either.Right -> {
						println("Authentication successful")
						Context.currentAccount = Account(username = username)
						break
					}
				}
			}
			if (currentAttemps == 0) {
				println("Max attemps reached. Try again later.")
			}
		}

		private fun UsernameRequest(): Username {
			println("Please enter your username")
			var username: Username? = null
			while (username == null) {
				val usernameLine = readLine()
				val usernameCheck = Username.create(usernameLine ?: "")
				when (usernameCheck) {
					is Either.Left  -> println("Username cant be empty. Please enter a valid username.")
					is Either.Right -> username = usernameCheck.value
				}
			}
			return username
		}

		private fun PasswordRequest(): Password {
			println("Please enter your password")
			var password: Password? = null
			while (password == null) {
				val passwordLine = readLine()
				val passwordCheck = Password.create(passwordLine ?: "")
				when (passwordCheck) {
					is Either.Left  -> println("Password cant be empty. Please enter a valid password.")
					is Either.Right -> password = passwordCheck.value
				}
			}
			return password
		}
	}
}
