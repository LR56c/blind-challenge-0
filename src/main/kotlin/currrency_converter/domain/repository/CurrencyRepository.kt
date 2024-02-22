package currrency_converter.domain.repository

import arrow.core.Either
import currrency_converter.domain.exceptions.InvalidCurrencyException

interface CurrencyRepository {
	suspend fun getCurrencyExchange(
		from: String, to: String
	): Either<InvalidCurrencyException, Double>
}
