package banking_system

import banking_system.di.vanillaDI
import banking_system.presentation.console.BankingSystem

fun main() {
	BankingSystem.run(vanillaDI())
}


