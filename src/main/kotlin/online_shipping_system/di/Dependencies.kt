package online_shipping_system.di

import features.authentication.domain.repository.AuthenticationRepository

interface Dependencies {
	val authenticationRepository: AuthenticationRepository
}
