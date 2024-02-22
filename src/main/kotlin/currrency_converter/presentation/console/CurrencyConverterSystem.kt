package currrency_converter.presentation.console

import currrency_converter.di.Dependencies
import currrency_converter.domain.entities.CurrencyCodes
import shared.presentation.confirmRequest
import shared.presentation.moneyRequest

class CurrencyConverterSystem {
	companion object {
		/*
* 		The user can choose whether or not to withdraw their funds. If they choose not to withdraw, it should return to the main menu.
* 		If the user decides to withdraw the funds, the system will charge a 1% commission.
* 		The system should ask the user if they want to perform another operation. If they choose to do so, it should restart the process; otherwise, the system should close.
		 */
		suspend fun run(dependencies: Dependencies) {
			val a =
				dependencies.currencyService().getCurrencyExchange(5000.0, "CLP", "USD")
			println(a)
			var exit = false
			while (!exit) {
				println("Welcome to the currency converter system")
				val fromCode = CurrencyRequest(
					"Please choose your initial currency\nOptions: ${
						CurrencyCodes.entries.joinToString("-")
					}"
				)
				val toCode = CurrencyRequest(
					"Please choose the currency you want to exchange to\nOptions: ${
						CurrencyCodes.entries.joinToString("-")
					}"
				)
				val amount = moneyRequest("Please enter the amount you want to exchange")

				//TODO: ambos confirm deben reiniciar el proceso. talvez hacer sub menu para separarlo (con otro cmd, o aprovechar el CurrencyConverterCmd
				val confirmCharge = confirmRequest("Commission charge: 1%\nDo you want to perform the operation? (Yes/No)")
				val confirmRestart = confirmRequest("Do you want to perform another operation? (Yes/No)")
			}
		}

		private fun CurrencyRequest(titleMessage: String): CurrencyCodes {
			var code: CurrencyCodes? = null
			while (code == null) {
				println(titleMessage)
				val input = readLine()!!
				code = CurrencyCodes.entries.find { it.name == input }
				if (code == null) {
					println("Invalid currency code")
				}
			}
			return code
		}
	}
}
