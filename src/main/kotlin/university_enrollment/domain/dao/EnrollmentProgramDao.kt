package university_enrollment.domain.dao

import arrow.core.Either
import shared.domain.value_objects.Name
import shared.domain.value_objects.Username
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.exceptions.EnrollmentException

interface EnrollmentProgramDao {
	fun insertStudentInProgram(
		credential: Credential,
		campusName: Name,
		programName: Name
	): Either<EnrollmentException, Unit>

	fun removeStudentFromProgram(
		username: Username,
		campusName: Name,
		programName: Name
	): Either<EnrollmentException, Unit>

	fun getFlatMapPrograms(): Set<Name>
	fun getFlatMapCampus(): Set<Name>
}
