package banking_system.di

import banking_system.domain.dao.AccountDao
import features.authentication.domain.repository.AuthenticationRepository

interface Dependencies {
	val accountDao: AccountDao
	val authenticationRepository: AuthenticationRepository
}
