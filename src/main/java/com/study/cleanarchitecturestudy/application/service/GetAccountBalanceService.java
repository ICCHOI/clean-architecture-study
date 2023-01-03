package com.study.cleanarchitecturestudy.application.service;

import java.time.LocalDateTime;

import com.study.cleanarchitecturestudy.application.port.in.GetAccountBalanceQuery;
import com.study.cleanarchitecturestudy.application.port.out.LoadAccountPort;
import com.study.cleanarchitecturestudy.domain.Account.AccountId;
import com.study.cleanarchitecturestudy.domain.Money;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(AccountId accountId) {
		return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
			.calculateBalance();
	}
}
