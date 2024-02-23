package banking_system.domain.value_objects

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import banking_system.domain.exceptions.InvalidMoneyException

data class Money private constructor(
	val amount: Double
) {
	companion object {
		fun create(value : String?): Either<InvalidMoneyException, Money> = either {
			val amount = value?.toDoubleOrNull()
			ensureNotNull(amount) { InvalidMoneyException() }
			ensure(amount > 0) { InvalidMoneyException() }
			Money(amount)
		}
	}
}
