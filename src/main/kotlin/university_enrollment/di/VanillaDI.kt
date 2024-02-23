package university_enrollment.di

import features.authentication.infrastructure.InMemoryAuthData
import university_enrollment.domain.dao.CredentialDao
import university_enrollment.domain.dao.EnrollmentProgramDao
import university_enrollment.infrastructure.InMemoryCredentialData
import university_enrollment.infrastructure.InMemoryEnrollmentProgramData


fun vanillaDI(): Dependencies = object : Dependencies {
	override val authenticationRepository = InMemoryAuthData()
	override val credentialDao: CredentialDao = InMemoryCredentialData()
	override val enrollmentProgramDao: EnrollmentProgramDao =
		InMemoryEnrollmentProgramData()
}
