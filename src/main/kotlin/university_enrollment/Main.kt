package university_enrollment

import university_enrollment.di.vanillaDI
import university_enrollment.presentation.console.UniversityEnrollmentSystem

fun main() {
	UniversityEnrollmentSystem.run(vanillaDI())
}
