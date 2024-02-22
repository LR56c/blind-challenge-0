package university_enrollment.domain.dao

import arrow.core.Either
import university_enrollment.domain.entities.Campus
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.entities.Program
import university_enrollment.domain.exceptions.EnrollmentProgramMaxCapacityException

interface EnrollmentProgramDao {
	fun insertStudentInProgram(credential: Credential, campus: Campus, program: Program): Either<EnrollmentProgramMaxCapacityException, Unit>
	fun removeStudentFromProgram(credential: Credential, campus: Campus, program: Program): Either<Exception, Unit>
}
