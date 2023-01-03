package com.study.cleanarchitecturestudy.application.port.in;

import com.study.cleanarchitecturestudy.domain.Account.AccountId;
import com.study.cleanarchitecturestudy.domain.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(AccountId accountId);
}
