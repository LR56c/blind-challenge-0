package features.authentication.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.authentication.domain.repository.AuthenticationRepository
import features.authentication.infrastructure.exceptions.InvalidAuthenticationException
import shared.domain.value_objects.Password
import shared.domain.value_objects.Username

class InMemoryAuthData(override var token: String = "") :
	AuthenticationRepository {
	private val accounts = mutableMapOf<Username, Password>()

	init {
		val u1 = Username.create("user1").getOrNull()!!
		val p1 = Password.create("password1").getOrNull()!!
		accounts[u1] = p1
		val u2 = Username.create("user2").getOrNull()!!
		val p2 = Password.create("password2").getOrNull()!!
		accounts[u2] = p2
	}

	override fun login(
		username: Username, password: Password
	): Either<InvalidAuthenticationException, Unit> = either {
		ensure(accounts.containsKey(username) && accounts[username] == password) { InvalidAuthenticationException() }
		token = username.value
	}

	override fun logout(username: Username): Either<InvalidAuthenticationException, Unit> =
		either {
			token = ""
		}
}
