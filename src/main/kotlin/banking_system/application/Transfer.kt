package banking_system.application

import arrow.core.Either
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import banking_system.domain.dao.AccountDao
import banking_system.domain.entities.Account
import banking_system.domain.value_objects.Money

class Transfer(private val accountDao: AccountDao) {
	operator fun invoke(
		money: Money, account: Account, otherAccount: Account
	): Either<List<Exception>, Unit> = either {
		account.withdraw(money).onLeft { raise(nonEmptyListOf(it)) }
		otherAccount.deposit(money)
		return Either.zipOrAccumulate(
			accountDao.saveAccount(account), accountDao.saveAccount(otherAccount)
		) { _, _ -> Unit }
	}
}

