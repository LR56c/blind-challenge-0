package shared.presentation

import arrow.core.Either
import features.authentication.domain.repository.AuthenticationRepository
import shared.domain.value_objects.Password
import shared.domain.value_objects.Username

class AuthenticationLoginCmd {
	companion object {
		fun run(
			onAuthSuccess: (Username) -> Unit = {},
			authenticationRepository: AuthenticationRepository,
			maxAttemps: Int = 1
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
