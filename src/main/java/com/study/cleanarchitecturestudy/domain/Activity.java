package com.study.cleanarchitecturestudy.domain;

import java.time.LocalDateTime;

public class Activity {

	private final ActivityId id;

	private final Account.AccountId ownerAccountId;

	private final Account.AccountId sourceAccountId;

	private final Account.AccountId targetAccountId;

	private final LocalDateTime timestamp;

	private final Money money;

	public ActivityId getId() {
		return id;
	}

	public Account.AccountId getOwnerAccountId() {
		return ownerAccountId;
	}

	public Account.AccountId getSourceAccountId() {
		return sourceAccountId;
	}

	public Account.AccountId getTargetAccountId() {
		return targetAccountId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Money getMoney() {
		return money;
	}

	public Activity(
		Account.AccountId ownerAccountId,
		Account.AccountId sourceAccountId,
		Account.AccountId targetAccountId,
		LocalDateTime timestamp,
		Money money) {
		this.id = null;
		this.ownerAccountId = ownerAccountId;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.timestamp = timestamp;
		this.money = money;
	}

	public record ActivityId(Long value) {
	}
}
