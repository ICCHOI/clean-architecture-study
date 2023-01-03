package com.study.cleanarchitecturestudy.application.port.out;

import java.time.LocalDateTime;

import com.study.cleanarchitecturestudy.domain.Account;

public interface LoadAccountPort {

	Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);
}
