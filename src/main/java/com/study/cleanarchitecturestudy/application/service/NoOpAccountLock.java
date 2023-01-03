package com.study.cleanarchitecturestudy.application.service;

import org.springframework.stereotype.Component;

import com.study.cleanarchitecturestudy.application.port.out.AccountLock;
import com.study.cleanarchitecturestudy.domain.Account.AccountId;

;

@Component
class NoOpAccountLock implements AccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}
}
