package finance_management_application.domain.entities

import banking_system.domain.value_objects.Money
import shared.domain.value_objects.Username

data class User(
	val username: Username,
	val income: Money? = null,
	val expenses: MutableMap<String, MutableList<Money>>
)
