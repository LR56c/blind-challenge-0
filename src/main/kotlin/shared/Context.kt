package shared

import features.account.domain.entities.Account

class Context {
			companion object {
				var currentAccount: Account? = null
		}
}
