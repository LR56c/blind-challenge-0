package university_enrollment.presentation.console

import shared.presentation.AuthenticationCmd
import shared.runIfAuthenticated
import university_enrollment.di.Dependencies

class UniversityEnrollmentSystem {
	companion object {
		fun run(dependencies: Dependencies) {
			var exit = false
			var lock = false
			while (!exit && !lock) {
				println("Welcome to the University Enrollment System")
				println("Please enter your command")
				println("1. Authenticate")
				println("2. Register program")
				println("3. Exit")
				val input = readLine()
				when (input) {
					"1"  -> {
						lock = !AuthenticationCmd.run(
							authenticationRepository = dependencies.authenticationRepository,
							maxAttemps = 3
						)
					}

					"2"  -> runIfAuthenticated(dependencies.authenticationRepository) {
						EnrollmentCmd.run(
							credentialDao = dependencies.credentialDao,
							enrollmentProgramDao = dependencies.enrollmentProgramDao,
							authenticationRepository = dependencies.authenticationRepository
						)
					}

					"3"  -> {
						println("Goodbye")
						exit = true
					}

					else -> println("Invalid command")
				}
			}
			if (lock) {
				println("System locked. Try again later.")
			}
		}
	}
}
