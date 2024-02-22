package currrency_converter.di

import currrency_converter.presentation.console.CurrencyService

interface Dependencies {
	fun currencyService(): CurrencyService
}
