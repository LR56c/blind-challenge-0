package features.account.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.exceptions.InvalidUsernameException

data class Username private constructor(
	val value: String
) {
	companion object {
		fun create(value: String): Either<InvalidUsernameException, Username> = either{
			ensure(value.isNotEmpty()) { InvalidUsernameException() }
			Username(value)
		}
	}
}
