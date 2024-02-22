package shared

import features.authentication.domain.repository.AuthenticationRepository


fun runIfAuthenticated(
	authenticationRepository: AuthenticationRepository, block: () -> Unit
) =
	if (authenticationRepository.token.isEmpty()) println("Please authenticate first") else block()
