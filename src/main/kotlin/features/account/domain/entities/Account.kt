package features.account.domain.entities

import features.account.domain.value_objects.Username

class Account(
	private var balance: Double = 2_000.0,
	val username: Username
) {

	fun deposit(amount: Double) {
		balance += amount
	}

	fun withdraw(amount: Double) {
		balance -= amount
	}

	//TODO: Faltan varios 'either'
	fun transfer(amount: Double, account: Account) {
		withdraw(amount)
		account.deposit(amount)
	}

	fun getBalance(): Double = balance
}

