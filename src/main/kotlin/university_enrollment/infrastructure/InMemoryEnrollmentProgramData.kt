package university_enrollment.infrastructure

import arrow.core.Either
import university_enrollment.domain.dao.EnrollmentProgramDao
import university_enrollment.domain.entities.Campus
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.entities.Name
import university_enrollment.domain.entities.Program
import university_enrollment.domain.exceptions.EnrollmentProgramMaxCapacityException

typealias ProgramName = Name
class InMemoryEnrollmentProgramData : EnrollmentProgramDao {

	private val programs = mutableMapOf<Campus, MutableMap<ProgramName, Program>>()

	override fun insertStudentInProgram(
		credential: Credential, campus: Campus, program: Program
	): Either<EnrollmentProgramMaxCapacityException, Unit> {
		TODO("Not yet implemented")
	}

	override fun removeStudentFromProgram(
		credential: Credential, campus: Campus, program: Program
	): Either<Exception, Unit> {
		TODO("Not yet implemented")
	}

}
//		if(programs.containsKey(campus)) {
//
//		}
//		ensure(programs.containsKey(campus)) { EnrollmentProgramMaxCapacityException() }
//		val selectedProgram = programs[campus]!!
//		ensure(selectedProgram == program) { EnrollmentProgramMaxCapacityException() }
//		selectedProgram
