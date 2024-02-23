package online_shipping_system.domain.entities

import banking_system.domain.value_objects.Money
import shared.domain.value_objects.ULID

data class Package(
	val code: ULID,
	val recipientDetails: RecipientDetails,
	val price: Money,
	val weight: Money,
	val sender: Sender
) {}
