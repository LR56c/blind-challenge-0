package shared.presentation

import arrow.core.Either
import shared.domain.value_objects.Password
import shared.domain.value_objects.Username
import features.authentication.domain.repository.AuthenticationRepository

class AuthenticationCmd {
	companion object {
		fun run(
			onAuthSuccess: (Username) -> Unit = {},
			onAuthLogout: () -> Unit = {},
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
					// se podria hacer que si el login es exitoso, no haga nada para mantener el menu auth, si es que se lo desea. ya que ahora se hace return para 'acceso rapido'
					"1"  -> return loginAction(
						onAuthSuccess,
						authenticationRepository,
						maxAttemps
					)

					"2"  -> {
							when (val username = Username.create(authenticationRepository.token)) {
								is Either.Left  -> println("You are not logged in.")
								is Either.Right -> {
									onAuthLogout()

									val result =
										authenticationRepository.logout(username.getOrNull()!!)
									when (result) {
										is Either.Left  -> println("Logout failed. Try again later.")
										is Either.Right -> println("Logout successful.")
									}
								}
							}
					}
					"3"  -> {
						exit = true
					}

					else -> println("Invalid command")
				}
			}
			return true
		}

		private fun loginAction(
			onAuthSuccess: (Username) -> Unit,
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

