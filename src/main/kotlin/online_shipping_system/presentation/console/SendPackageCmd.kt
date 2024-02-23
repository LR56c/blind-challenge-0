package online_shipping_system.presentation.console

import banking_system.domain.value_objects.Money
import online_shipping_system.domain.entities.Package
import online_shipping_system.domain.entities.RecipientDetails
import online_shipping_system.domain.entities.Sender
import shared.domain.value_objects.ULID
import shared.presentation.confirmRequest
import shared.presentation.moneyRequest
import university_enrollment.presentation.console.nameRequest

class SendPackageCmd {
	companion object {
		fun run(): Boolean {
			println("Send package")
			val categoryName = nameRequest(
				titleMessage = "Enter the category name of the recipient:",
				errorMessage = "Invalid category name",
				default = "Unspecified"
			)
			val recipientDetails = RecipientDetails(categoryName)

			val senderName = nameRequest(
				titleMessage = "Enter the name of the sender:",
				errorMessage = "Invalid sender name"
			)
			val senderAddressName = nameRequest(
				titleMessage = "Enter the address of the sender:",
				errorMessage = "Invalid sender address"
			)
			val sender = Sender(senderName, senderAddressName)

			val weight = moneyRequest("Enter the weight of the package:")

			// podria hacerlo un servicio. extra: que vengan los datos y solo marcar peso
			val money = Money.create((2 * weight.amount).toString())

			money.onLeft {
				println("Invalid amount. Please enter a valid amount. Try again.")
				return true
			}

			println("The amount to pay is: $${money.getOrNull()!!.amount}")
			val confirmPay =
				confirmRequest("Do you want to pay this amount? (Yes/No)")

			if (!confirmPay) {
				println("Returning to main menu")
				return false
			}

			val pkg = Package(
				code = ULID.create(),
				recipientDetails = recipientDetails,
				price = money.getOrNull()!!,
				weight = weight,
				sender = sender
			)
			println("Package sent successfully. Package information: $pkg")

			val confirm =
				confirmRequest("Do you want perform another operation? (Yes/No)")
			if (confirm) {
				println("Returning to main menu")
				return false
			}
			return true
		}
	}
}

