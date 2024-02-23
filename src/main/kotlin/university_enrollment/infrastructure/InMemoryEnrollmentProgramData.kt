package university_enrollment.infrastructure

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import shared.domain.value_objects.Name
import shared.domain.value_objects.Username
import university_enrollment.domain.dao.EnrollmentProgramDao
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.entities.Program
import university_enrollment.domain.exceptions.CampusNotFoundException
import university_enrollment.domain.exceptions.EnrollmentException
import university_enrollment.domain.exceptions.EnrollmentProgramMaxCapacityException
import university_enrollment.domain.exceptions.ProgramNotFoundException

typealias ProgramName = Name
typealias CampusName = Name

class InMemoryEnrollmentProgramData : EnrollmentProgramDao {

	private val programs =
		mutableMapOf<CampusName, MutableMap<ProgramName, Program>>()

	override fun getFlatMapPrograms(): Set<ProgramName> =
		programs.flatMap { it.value.keys }.toSet()

	override fun getFlatMapCampus(): Set<CampusName> = programs.keys

	init {
		val londonCampus = Name.create("London").getOrNull()!!
		val csProgramLondon =
			Program(Name.create("Computer Science").getOrNull()!!, 1, mutableMapOf())
		val medicineProgramLondon =
			Program(Name.create("Medicine").getOrNull()!!, 1, mutableMapOf())
		val marketingProgramLondon =
			Program(Name.create("Marketing").getOrNull()!!, 1, mutableMapOf())
		val artsProgramLondon =
			Program(Name.create("Arts").getOrNull()!!, 1, mutableMapOf())

		val manchesterCampus = Name.create("Manchester").getOrNull()!!
		val csProgramManchester =
			Program(Name.create("Computer Science").getOrNull()!!, 3, mutableMapOf())
		val medicineProgramManchester =
			Program(Name.create("Medicine").getOrNull()!!, 3, mutableMapOf())
		val marketingProgramManchester =
			Program(Name.create("Marketing").getOrNull()!!, 3, mutableMapOf())
		val artsProgramManchester =
			Program(Name.create("Arts").getOrNull()!!, 3, mutableMapOf())

		val liverpoolCampus = Name.create("Liverpool").getOrNull()!!
		val csProgramLiverpool =
			Program(Name.create("Computer Science").getOrNull()!!, 1, mutableMapOf())
		val medicineProgramLiverpool =
			Program(Name.create("Medicine").getOrNull()!!, 1, mutableMapOf())
		val marketingProgramLiverpool =
			Program(Name.create("Marketing").getOrNull()!!, 1, mutableMapOf())
		val artsProgramLiverpool =
			Program(Name.create("Arts").getOrNull()!!, 1, mutableMapOf())

		programs[londonCampus] = mutableMapOf(
			csProgramLondon.name to csProgramLondon,
			medicineProgramLondon.name to medicineProgramLondon,
			marketingProgramLondon.name to marketingProgramLondon,
			artsProgramLondon.name to artsProgramLondon,
		)

		programs[manchesterCampus] = mutableMapOf(
			csProgramManchester.name to csProgramManchester,
			medicineProgramManchester.name to medicineProgramManchester,
			marketingProgramManchester.name to marketingProgramManchester,
			artsProgramManchester.name to artsProgramManchester
		)

		programs[liverpoolCampus] = mutableMapOf(
			csProgramLiverpool.name to csProgramLiverpool,
			medicineProgramLiverpool.name to medicineProgramLiverpool,
			marketingProgramLiverpool.name to marketingProgramLiverpool,
			artsProgramLiverpool.name to artsProgramLiverpool
		)
	}

	override fun insertStudentInProgram(
		credential: Credential, campusName: Name, programName: Name
	): Either<EnrollmentException, Unit> = either {
		val selectedProgram = programExist(campusName, programName)
		ensure(selectedProgram.students.size < selectedProgram.slotCapacity) { EnrollmentProgramMaxCapacityException() }
		selectedProgram.students[credential.username] = credential
	}

	override fun removeStudentFromProgram(
		username: Username, campusName: Name, programName: Name
	): Either<EnrollmentException, Unit> = either {
		val selectedProgram = programExist(campusName, programName)
		selectedProgram.students.remove(username)
	}

	private fun Raise<EnrollmentException>.programExist(
		campusName: Name, programName: Name
	): Program {
		ensure(programs.containsKey(campusName)) { CampusNotFoundException() }
		val selectedProgram = programs[campusName]!![programName]
		ensureNotNull(selectedProgram) { ProgramNotFoundException() }
		return selectedProgram
	}
}
