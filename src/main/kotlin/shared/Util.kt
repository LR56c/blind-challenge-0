package shared


fun runIfAuthenticated(block: () -> Unit) =
	Context.currentAccount?.let { block() }
		?: println("Please authenticate first")
