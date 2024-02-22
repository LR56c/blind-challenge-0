package banking_system.presentation.console

import arrow.core.Either
import banking_system.domain.dao.AccountDao
import banking_system.domain.entities.Account
import shared.presentation.usernameRequest

internal fun accountRequest(
	currentAccount: Account, accountDao: AccountDao
): Account {
	var account: Account? = null
	while (account == null) {
		val usernameCheck = usernameRequest(
			"Please enter the account username",
			"Username cant be empty. Please enter a valid username."
		)

		if (currentAccount.username == usernameCheck) {
			println("You can't transfer to yourself. Please enter a valid account.")
		} else {
			when (val accountCheck = accountDao.getAccount(usernameCheck)) {
				is Either.Left  -> println("Account not found. Please enter a valid account.")
				is Either.Right -> account = accountCheck.value
			}
		}
	}
	return account
}

