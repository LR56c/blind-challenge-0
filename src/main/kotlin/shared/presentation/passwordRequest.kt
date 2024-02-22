package shared.presentation

import arrow.core.Either
import shared.domain.value_objects.Password

fun passwordRequest(): Password {
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

