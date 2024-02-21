package banking_system

import banking_system.di.vanillaDI
import banking_system.presentation.BankingSystem

fun main() {
	BankingSystem.run(vanillaDI())
}


