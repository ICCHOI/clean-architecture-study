package com.study.cleanarchitecturestudy.application.service;

import com.study.cleanarchitecturestudy.domain.Money;

public class MoneyTransferProperties {

	private Money maximumTransferThreshold = Money.of(1_000_000L);

	public MoneyTransferProperties(Money maximumTransferThreshold) {
		this.maximumTransferThreshold = maximumTransferThreshold;
	}

	public Money getMaximumTransferThreshold() {
		return maximumTransferThreshold;
	}

}
