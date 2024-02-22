package currrency_converter.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import currrency_converter.domain.exceptions.InvalidCurrencyException
import currrency_converter.domain.repository.CurrencyRepository
import currrency_converter.shared.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class CurrencyExchangeApiData(private val client: HttpClient) : CurrencyRepository {
	override suspend fun getCurrencyExchange(
		from: String,
		to: String
	): Either<InvalidCurrencyException, Double> = either {
		val response = client.get {
			url(HttpRoutes.CURRENCY_EXCHANGE)
			parameter("from", from)
			parameter("to", to)
			header(
				"X-RapidAPI-Key",
				"447800c911msha0ff3254fa4c25cp1da8f3jsn6fb0775a4187"
			)
			header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
		}.bodyAsText()
		val result = response.toDoubleOrNull()
		ensureNotNull(result) { InvalidCurrencyException() }
		result
	}
}
