package shared.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import shared.domain.exceptions.InvalidNameException

data class Name private constructor(val value: String) {
	companion object {
		fun create(value: String): Either<InvalidNameException, Name> = either {
			ensure(value.isNotEmpty()) { InvalidNameException() }
			Name(value)
		}
	}
}
