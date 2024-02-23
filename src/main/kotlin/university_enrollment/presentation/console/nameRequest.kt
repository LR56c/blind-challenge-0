package university_enrollment.presentation.console

import arrow.core.Either
import shared.domain.value_objects.Name

fun nameRequest(titleMessage: String, errorMessage : String, default : String? = null): Name {

	val defaultText = if (default != null) "(default: $default)" else ""
	println("$titleMessage $defaultText")
	var name: Name? = null

	while (name == null) {
		val nameLine = readLine()

		if (nameLine == null && default != null) {
			when (val nameCheck = Name.create(default)) {
				is Either.Left  -> {}
				is Either.Right -> name = nameCheck.value
			}
		}

		val nameCheck = Name.create(nameLine ?: "")
		when (nameCheck) {
			is Either.Left  -> println(errorMessage)
			is Either.Right -> name = nameCheck.value
		}
	}
	return name
}


