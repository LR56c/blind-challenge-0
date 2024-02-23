package online_shipping_system

import online_shipping_system.di.vanillaDI
import online_shipping_system.presentation.console.OnlineShippingSystem

fun main() {
	OnlineShippingSystem.run(vanillaDI())
}
