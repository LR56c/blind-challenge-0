package features.account.domain.entities

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.exceptions.InvalidMoneyException
import features.account.domain.value_objects.Money
import features.account.domain.value_objects.Username

data class Account(
	private var balance: Double = 2_000.0,
	val username: Username
) {

	fun deposit(money: Money) {
		balance += money.amount
	}

	fun withdraw(money: Money) : Either<Exception, Unit> = either {
		ensure(balance >= money.amount) { InvalidMoneyException() }
		balance -= money.amount
	}

	fun getBalance(): Double = balance
}

