package currrency_converter.shared

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
object HttpRoutes {
	const val CURRENCY_EXCHANGE =
		"https://currency-exchange.p.rapidapi.com/exchange"
}

val httpClient = HttpClient(CIO){
	install(Logging) {
		level = LogLevel.NONE
	}
	install(ContentNegotiation){
		json()
	}
}
