package shared.presentation

import arrow.core.Either
import features.account.domain.dao.AccountDao
import features.account.domain.entities.Account
import features.account.domain.value_objects.Password
import features.account.domain.value_objects.Username
import features.authentication.domain.repository.AuthenticationRepository
import shared.Context

class AuthenticationCmd {
	companion object {
		fun run(
			accountDao: AccountDao,
			authenticationRepository: AuthenticationRepository, maxAttemps: Int = 1
		) {
			var exit = false
			while (!exit) {
				println("Welcome to the Authentication System")
				println("Please enter your command")
				println("1. Login")
				println("2. Logout")
				println("3. Return")
				val input = readLine()
				when (input) {
					"1"  -> loginAction(accountDao,authenticationRepository, maxAttemps)
					"2"  -> {
						Context.currentAccount = null
						exit = true
					}

					"3"  -> {
						exit = true
					}

					else -> println("Invalid command")
				}
			}
		}

		private fun loginAction(
			accountDao: AccountDao,
			authenticationRepository: AuthenticationRepository,
			maxAttemps: Int
		) {
			var currentAttemps = maxAttemps
			while (currentAttemps > 0) {
				val username: Username = UsernameRequest("Please enter your username","Username cant be empty. Please enter a valid username.")
				val password: Password = PasswordRequest()

				when (authenticationRepository.login(username, password)) {
					is Either.Left  -> {
						println("Authentication failed")
						currentAttemps--
					}

					is Either.Right -> {
						println("Authentication successful")
						Context.currentAccount = accountDao.getAccount(username).getOrNull()
						break
					}
				}
			}
			if (currentAttemps == 0) {
				println("Max attemps reached. Try again later.")
			}
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

fun UsernameRequest(titleMessage: String, errorMessage : String): Username {

	println(titleMessage)
	var username: Username? = null
	while (username == null) {
		val usernameLine = readLine()
		val usernameCheck = Username.create(usernameLine ?: "")
		when (usernameCheck) {
			is Either.Left  -> println(errorMessage)
			is Either.Right -> username = usernameCheck.value
		}
	}
	return username
}
