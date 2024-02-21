package features.account.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.entities.Account
import features.account.domain.dao.AccountDao
import features.account.domain.value_objects.Username
import features.account.infrastructure.exceptions.AccountNotFoundException

class InMemoryAccountData : AccountDao {

	private val accounts = mutableMapOf<Username, Account>()

	init {
		val u = Username.create("user1").getOrNull()!!
		accounts[u] = Account(username = u)
	}

	override fun getAccount(username: Username): Either<AccountNotFoundException, Account> = either{
		ensure(accounts.containsKey(username)) { AccountNotFoundException() }
		accounts[username]!!
	}

	override fun saveAccount(account: Account) : Either<AccountNotFoundException, Account> = either {
		ensure(!accounts.containsKey(account.username)) { AccountNotFoundException() }
		accounts[account.username]!!
	}
}
