package online_shipping_system.di

import features.authentication.domain.repository.AuthenticationRepository
import online_shipping_system.presentation.console.ShippingService

interface Dependencies {
	val authenticationRepository: AuthenticationRepository
	val shippingService: ShippingService
}
