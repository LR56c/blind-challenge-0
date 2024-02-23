package online_shipping_system.presentation.console

import shared.presentation.confirmRequest

class SendPackageCmd {
	companion object {
		//		To send a package, sender and recipient details are required.
		//		The system assigns a random package number to each sent package.
		//		The system calculates the shipping price. $2 per kg.
		//		The user must input the total weight of their package, and the system should display the amount to pay.
		fun run(): Boolean {
			val confirm = confirmRequest("Do you want perform another operation? (Yes/No)")
			if (confirm) {
				println("Returning to main menu")
				return false
			}
			return true
		}
	}
}
