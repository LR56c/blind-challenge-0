package features.account.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import features.account.domain.exceptions.InvalidMoneyException

class Money private constructor(
	val amount: Double
) {
	companion object {
		fun create(amount: Double): Either<InvalidMoneyException, Money> = either {
			ensure(amount > 0) { InvalidMoneyException() }
			Money(amount)
		}
	}
}
