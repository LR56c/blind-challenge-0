package online_shipping_system.di

import features.authentication.infrastructure.InMemoryAuthData

fun vanillaDI(): Dependencies = object : Dependencies {
	override val authenticationRepository = InMemoryAuthData()
}
