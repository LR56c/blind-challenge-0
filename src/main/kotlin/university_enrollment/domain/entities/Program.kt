package university_enrollment.domain.entities

import shared.domain.value_objects.Name
import shared.domain.value_objects.Username

data class Program(
	val name: Name,
	val slotCapacity: Int,
	val students: MutableMap<Username, Credential>
) {}
