package shared.presentation

import arrow.core.Either
import banking_system.domain.value_objects.Money

fun moneyRequest(titleMessage : String): Money {
	println(titleMessage)
	var money: Money? = null
	while (money == null) {
		val amount = readLine()
		val moneyCheck = Money.create(amount?.toDouble() ?: -1.0)
		when (moneyCheck) {
			is Either.Left  -> println("Invalid amount. Please enter a valid amount.")
			is Either.Right -> money = moneyCheck.value
		}
	}
	return money
}

