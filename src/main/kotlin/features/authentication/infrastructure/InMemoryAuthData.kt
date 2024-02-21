package features.authentication.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.value_objects.Password
import features.account.domain.value_objects.Username
import features.authentication.domain.repository.AuthenticationRepository
import features.authentication.infrastructure.exceptions.InvalidAuthenticationException

class InMemoryAuthData : AuthenticationRepository {
	private val accounts = mutableMapOf<Username, Password>()

	init {
		val u = Username.create("user1").getOrNull()!!
		val p = Password.create("password1").getOrNull()!!
		accounts[u] = p
	}

	override fun login(
		username: Username, password: Password
	): Either<InvalidAuthenticationException, Unit> = either {
		println("username: $username, password: $password")
		println("accounts")
		println(accounts)
		ensure(accounts.containsKey(username) && accounts[username] == password) { InvalidAuthenticationException() }
	}
}
