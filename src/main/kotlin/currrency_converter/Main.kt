package currrency_converter

import currrency_converter.di.vanillaDI
import currrency_converter.presentation.console.CurrencyConverterSystem

suspend fun main() {
	CurrencyConverterSystem.run(vanillaDI())
}
