package features.account.domain.dao

import arrow.core.Either
import features.account.domain.entities.Account
import features.account.domain.value_objects.Username
import features.account.infrastructure.exceptions.AccountNotFoundException

interface AccountDao {
	fun get() : MutableMap<Username, Account>
	fun getAccount(username: Username): Either<AccountNotFoundException, Account>
	fun saveAccount(account: Account): Either<AccountNotFoundException, Account>
}
