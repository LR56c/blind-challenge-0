package shared.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import shared.domain.exceptions.InvalidULIDException

data class ULID private constructor(val value: String) {
	companion object {
		fun create(): ULID = ULID(ulid.ULID.randomULID())
		fun from(value: String): Either<InvalidULIDException, ULID> = either {
			try {
				ULID(ulid.ULID.parseULID(value).toString())
			} catch (e: Exception) {
				throw InvalidULIDException()
			}
		}
	}
}
