package university_enrollment.presentation.console

import arrow.core.Either
import university_enrollment.domain.entities.Name

fun nameRequest(titleMessage: String, errorMessage : String): Name {

	println(titleMessage)
	var name: Name? = null
	while (name == null) {
		val nameLine = readLine()
		val nameCheck = Name.create(nameLine ?: "")
		when (nameCheck) {
			is Either.Left  -> println(errorMessage)
			is Either.Right -> name = nameCheck.value
		}
	}
	return name
}


