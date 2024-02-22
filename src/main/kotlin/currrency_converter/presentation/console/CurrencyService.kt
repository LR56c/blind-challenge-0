package currrency_converter.presentation.console

import arrow.core.Either
import arrow.core.raise.either
import currrency_converter.domain.exceptions.InvalidCurrencyException
import currrency_converter.domain.repository.CurrencyRepository

class CurrencyService(private val currencyRepository: CurrencyRepository) {
	suspend fun getCurrencyExchange(
		amount: Double, from: String, to: String
	): Either<InvalidCurrencyException, Double> = either {
		when (val result = currencyRepository.getCurrencyExchange(from, to)) {
			is Either.Left  -> raise(result.value)
			is Either.Right -> result.value * amount
		}
	}
}
