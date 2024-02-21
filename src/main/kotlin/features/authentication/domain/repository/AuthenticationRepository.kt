package features.authentication.domain.repository

import arrow.core.Either
import features.account.domain.value_objects.Password
import features.account.domain.value_objects.Username
import features.authentication.infrastructure.exceptions.InvalidAuthenticationException

interface AuthenticationRepository {
	fun login(username: Username, password: Password): Either<InvalidAuthenticationException, Unit>
}
