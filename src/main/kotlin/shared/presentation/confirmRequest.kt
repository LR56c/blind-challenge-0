package shared.presentation

fun confirmRequest(titleMessage: String): Boolean {
	var code: Boolean? = null
	while (code == null) {
		println(titleMessage)
		val input = readLine()
		code = when (input) {
			"yes" -> true
			"y"   -> true
			"no"  -> false
			"n"   -> false
			else  -> null
		}
		if (code == null) {
			println("Invalid option")
		}
	}
	return code
}

