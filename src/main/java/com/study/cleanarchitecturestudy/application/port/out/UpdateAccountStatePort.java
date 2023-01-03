package com.study.cleanarchitecturestudy.application.port.out;

import com.study.cleanarchitecturestudy.domain.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);
}
