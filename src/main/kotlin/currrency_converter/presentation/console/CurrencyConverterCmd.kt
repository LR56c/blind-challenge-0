package currrency_converter.presentation.console

import arrow.core.Either
import currrency_converter.domain.entities.CurrencyCodes
import shared.presentation.confirmRequest
import shared.presentation.moneyRequest
import java.text.NumberFormat
import java.util.*


class CurrencyConverterCmd {
	companion object {
		suspend fun run(currencyService: CurrencyService) {
			var exit = false
			while (!exit) {
				val options = CurrencyCodes.entries.joinToString("-")
				val fromCode = currencyRequest(
					"Please choose your initial currency\nOptions: $options"
				)
				val toCode = currencyRequest(
					"Please choose the currency you want to exchange to\nOptions: $options"
				)
				val money =
					moneyRequest("Please enter the amount ($fromCode) you want to exchange")

				val result = currencyService.getCurrencyExchange(
					money.amount, fromCode.name, toCode.name
				)

				when (result) {
					is Either.Left  -> println("An error occurred while performing the operation. Please try again.")
					is Either.Right -> {
						val confirmCharge =
							confirmRequest("Commission charge: 1% (${money.amount * 0.01} $fromCode)\nDo you want to perform the operation? (Yes/No)")

						if (!confirmCharge) {
							exit = true
						}
						val format = NumberFormat.getCurrencyInstance()
						format.currency = Currency.getInstance(toCode.name)
						println("The result of the operation is: ${format.format(result.value)}")
					}
				}

				val confirmRestart =
					confirmRequest("Do you want to perform another operation? (Yes/No)")

				if (!confirmRestart) {
					exit = true
				}
			}
		}
	}
}
