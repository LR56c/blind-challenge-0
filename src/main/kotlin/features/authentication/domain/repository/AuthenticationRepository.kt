package features.authentication.domain.repository

import arrow.core.Either
import features.authentication.infrastructure.exceptions.InvalidAuthenticationException
import shared.domain.value_objects.Password
import shared.domain.value_objects.Username

interface AuthenticationRepository {
	fun login(
		username: Username,
		password: Password
	): Either<InvalidAuthenticationException, Unit>

	fun logout(username: Username): Either<InvalidAuthenticationException, Unit>
	var token: String
}
