package com.study.cleanarchitecturestudy.application.port.in;

import com.study.cleanarchitecturestudy.domain.Account;
import com.study.cleanarchitecturestudy.domain.Money;

import jakarta.validation.constraints.NotNull;

public class MoneyCommand {

	public record SendMoney(
		@NotNull(message = "id not be null")
		Account.AccountId sourceAccountId,

		@NotNull(message = "id not be null")
		Account.AccountId targetAccountId,

		@NotNull(message = "money not be null")
		Money money
	) {}

}