package shared.presentation

import arrow.core.Either
import shared.domain.value_objects.Username

fun usernameRequest(titleMessage: String, errorMessage: String): Username {
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

