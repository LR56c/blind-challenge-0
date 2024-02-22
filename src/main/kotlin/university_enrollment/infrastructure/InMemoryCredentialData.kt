package university_enrollment.infrastructure

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import shared.domain.value_objects.Username
import university_enrollment.domain.dao.CredentialDao
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.exceptions.CredentialNotFoundException

class InMemoryCredentialData: CredentialDao {

	private val credentials = mutableMapOf<Username, Credential>()
	override fun getCredential(username: Username): Either<CredentialNotFoundException, Credential> = either{
		ensure(credentials.containsKey(username)) { CredentialNotFoundException() }
		credentials[username]!!
	}

	override fun saveCredential(credential: Credential): Either<CredentialNotFoundException, Unit> = either{
		ensure(credentials.containsKey(credential.username)) { CredentialNotFoundException() }
		credentials[credential.username] = credential
	}
}
