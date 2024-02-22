package university_enrollment.domain.entities

import shared.domain.value_objects.Username

data class Credential(val firstName : Name, val lastName: Name, val username: Username){}
