package banking_system.domain.dao

import arrow.core.Either
import banking_system.domain.entities.Account
import shared.domain.value_objects.Username
import banking_system.domain.exceptions.AccountNotFoundException

interface AccountDao {
	fun getAccount(username: Username): Either<AccountNotFoundException, Account>
	fun saveAccount(account: Account): Either<AccountNotFoundException, Unit>
}
