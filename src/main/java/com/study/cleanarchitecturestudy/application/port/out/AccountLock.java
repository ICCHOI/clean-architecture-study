package com.study.cleanarchitecturestudy.application.port.out;

import com.study.cleanarchitecturestudy.domain.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);
}
