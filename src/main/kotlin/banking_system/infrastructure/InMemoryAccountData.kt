package banking_system.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import banking_system.domain.dao.AccountDao
import banking_system.domain.entities.Account
import banking_system.domain.exceptions.AccountNotFoundException
import shared.domain.value_objects.Username

class InMemoryAccountData : AccountDao {

	private val accounts = mutableMapOf<Username, Account>()

	init {
		val u1 = Username.create("user1").getOrNull()!!
		accounts[u1] = Account(username = u1)
		val u2 = Username.create("user2").getOrNull()!!
		accounts[u2] = Account(username = u2)
	}

	override fun getAccount(username: Username): Either<AccountNotFoundException, Account> =
		either {
			ensure(accounts.containsKey(username)) { AccountNotFoundException() }
			accounts[username]!!
		}

	override fun saveAccount(account: Account): Either<AccountNotFoundException, Unit> =
		either {
			ensure(accounts.containsKey(account.username)) { AccountNotFoundException() }
			accounts[account.username] = account
		}
}
