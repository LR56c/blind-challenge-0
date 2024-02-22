package university_enrollment.domain.dao

import arrow.core.Either
import shared.domain.value_objects.Username
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.exceptions.CredentialNotFoundException

interface CredentialDao {
		fun getCredential(username: Username): Either<CredentialNotFoundException, Credential>
		fun saveCredential(credential: Credential): Either<CredentialNotFoundException, Unit>
}
