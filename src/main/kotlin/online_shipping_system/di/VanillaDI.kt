package online_shipping_system.di

import features.authentication.infrastructure.InMemoryAuthData
import online_shipping_system.presentation.console.ShippingService

fun vanillaDI(): Dependencies = object : Dependencies {
	override val authenticationRepository = InMemoryAuthData()
	override val shippingService: ShippingService = ShippingService()
}
