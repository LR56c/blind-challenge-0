package currrency_converter.presentation.console

import currrency_converter.di.Dependencies
import currrency_converter.domain.entities.CurrencyCodes

class CurrencyConverterSystem {
	companion object {
		suspend fun run(dependencies: Dependencies) {
			var exit = false
			while (!exit) {
				println("Welcome to the currency converter system")
				println("Please enter your command")
				println("1. Convert currency")
				println("2. Exit")
				val input = readLine()
				when (input) {
					"1"  -> CurrencyConverterCmd.run(dependencies.currencyService())
					"2"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
		}

	}
}

fun currencyRequest(titleMessage: String): CurrencyCodes {
	var code: CurrencyCodes? = null
	while (code == null) {
		try {
			println(titleMessage)
			val input = readLine()
			code = CurrencyCodes.valueOf(input?.uppercase() ?: "")
		} catch (e: Exception) {
			println("Invalid currency code")
		}
	}
	return code
}
