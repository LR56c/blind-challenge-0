package university_enrollment.presentation.console

import arrow.core.Either
import features.authentication.domain.repository.AuthenticationRepository
import shared.domain.value_objects.Username
import university_enrollment.domain.dao.CredentialDao
import university_enrollment.domain.dao.EnrollmentProgramDao
import university_enrollment.domain.entities.Credential

class EnrollmentCmd {
	companion object {    /*
 		The user must input their first name, last name, and chosen program.
 		Upon logging in, a menu displays the available programs: Computer Science, Medicine, Marketing, and Arts.

 		Each program has only 5 available slots. The system will store the data of each registered user, and if it exceeds the limit,
 		 it should display a message indicating the program is unavailable.

 		The user must choose a campus from three cities: London, Manchester, Liverpool.
 		In London, there is 1 slot per program; in Manchester, there are 3 slots per program, and in Liverpool, there is 1 slot per program.
 		If the user selects a program at a campus that has no available slots, the system should display the option to enroll in the program at another campus.
		 */

		/*
			- programRequest
			- campusRequest
			- program dao
				- map campus-program
		 */
		fun run(
			credentialDao: CredentialDao,
			enrollmentProgramDao: EnrollmentProgramDao,
			authenticationRepository: AuthenticationRepository
		) {
			var exit = false
			while (!exit) {
				println("Welcome to the University Enrollment System")
				val credential =
					credentialsCheck(authenticationRepository, credentialDao)
				if (credential == null) {
					println("Credentials failed. Please try again.")
					exit = true
				}

				println("Available programs: Computer Science, Medicine, Marketing, and Arts")
				//TODO: aunque me gustaria mostrar los programas de cada campus, el requisito solo menciona mostrarlos, por lo que tendria que hacer un program flatmap de todos los campus
			//				val program = programRequest("Enter your chosen program", "Invalid program")
			}
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
