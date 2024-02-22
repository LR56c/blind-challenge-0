package university_enrollment.di

import features.authentication.domain.repository.AuthenticationRepository
import university_enrollment.domain.dao.CredentialDao
import university_enrollment.domain.dao.EnrollmentProgramDao

interface Dependencies {
	val authenticationRepository: AuthenticationRepository
	val credentialDao: CredentialDao
	val enrollmentProgramDao: EnrollmentProgramDao
}
