package currrency_converter.di

import currrency_converter.infrastructure.CurrencyExchangeApiData
import currrency_converter.presentation.console.CurrencyService
import currrency_converter.shared.httpClient

fun vanillaDI(): Dependencies = object : Dependencies {
	override fun currencyService(): CurrencyService =
		CurrencyService(CurrencyExchangeApiData(httpClient))
}
