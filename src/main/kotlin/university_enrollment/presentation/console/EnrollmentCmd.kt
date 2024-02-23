package university_enrollment.presentation.console

import arrow.core.Either
import features.authentication.domain.repository.AuthenticationRepository
import shared.domain.value_objects.Username
import university_enrollment.domain.dao.CredentialDao
import university_enrollment.domain.dao.EnrollmentProgramDao
import university_enrollment.domain.entities.Credential
import university_enrollment.domain.entities.Name
import university_enrollment.domain.exceptions.CampusNotFoundException
import university_enrollment.domain.exceptions.EnrollmentProgramMaxCapacityException
import university_enrollment.domain.exceptions.ProgramNotFoundException

class EnrollmentCmd {
	companion object {
		fun run(
			credentialDao: CredentialDao,
			enrollmentProgramDao: EnrollmentProgramDao,
			authenticationRepository: AuthenticationRepository
		) {
			println("Welcome to the University Enrollment System")
			val credential = credentialsCheck(authenticationRepository, credentialDao)
			if (credential == null) {
				println("Credentials failed. Please try again.")
				return
			}
			//aunque me gustaria mostrar los programas de cada campus, el requisito solo menciona mostrarlos, por lo que tendria que hacer un program flatmap de todos los campus
			val availablePrograms = enrollmentProgramDao.getFlatMapPrograms()
			println("Available programs: ${availablePrograms.joinToString(", ") { it.value }}")

			val programName = checkNameInSet_Request(
				"Please enter the program name you want to enroll in",
				"Invalid program name. Try again",
				availablePrograms
			)

			val availableCampus = enrollmentProgramDao.getFlatMapCampus()
			println("Available campuses: ${availableCampus.joinToString(", ") { it.value }}")

			val campusName = checkNameInSet_Request(
				"Please enter the campus name you want to enroll in",
				"Invalid campus name. Try again",
				availableCampus
			)

			val result = enrollmentProgramDao.insertStudentInProgram(
				credential, campusName, programName
			)
			when (result) {
				is Either.Left  -> when (result.value) {
					is CampusNotFoundException               -> println("Campus not found. Try again.")
					is EnrollmentProgramMaxCapacityException -> println("Program at max capacity. Try another campus.")
					is ProgramNotFoundException              -> println("Program not found. Try again.")
				}

				is Either.Right -> println("Enrollment successful")
			}
		}

		private fun checkNameInSet_Request(
			titleMessage: String, errorMessage: String, availablePrograms: Set<Name>
		): Name {
			var name: Name? = null
			while (name == null) {
				val nameToCheck = nameRequest(titleMessage, errorMessage)
				if (availablePrograms.contains(nameToCheck)) {
					name = nameToCheck
				} else {
					println("Program not found. Please enter a valid program.")
				}
			}
			return name
		}

		private fun credentialsCheck(
			authenticationRepository: AuthenticationRepository,
			credentialDao: CredentialDao
		): Credential? {
			return when (val usernameCheck =
				Username.create(authenticationRepository.token)) {
				is Either.Left  -> return null

				is Either.Right -> when (val credentialCheck =
					credentialDao.getCredential(usernameCheck.getOrNull()!!)) {
					is Either.Left  -> {
						println("Credential not found. Please register.")
						val firstName =
							nameRequest("Enter your first name", "Invalid first name")
						val lastName =
							nameRequest("Enter your last name", "Invalid last name")
						return Credential(
							firstName, lastName, usernameCheck.value
						)
					}

					is Either.Right -> credentialCheck.value
				}
			}
		}
	}
}
