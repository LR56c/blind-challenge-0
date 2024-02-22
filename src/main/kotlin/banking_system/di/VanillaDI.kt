package banking_system.di

import banking_system.infrastructure.InMemoryAccountData
import features.authentication.infrastructure.InMemoryAuthData


fun vanillaDI(): Dependencies = object : Dependencies {
		override val accountDao = InMemoryAccountData()
		override val authenticationRepository = InMemoryAuthData()
}
