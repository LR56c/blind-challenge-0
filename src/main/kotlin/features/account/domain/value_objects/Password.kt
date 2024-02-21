package features.account.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.exceptions.InvalidPasswordException

data class Password private constructor(
	val value: String
) {
	companion object {
		fun create(value: String): Either<InvalidPasswordException, Password> = either {
			ensure(value.isNotEmpty()) { InvalidPasswordException() }
			Password(value)
		}
	}
}
