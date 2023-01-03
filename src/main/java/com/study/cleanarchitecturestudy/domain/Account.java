package com.study.cleanarchitecturestudy.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Account는 실제 계좌의 현재 스냅샷을 제공합니다.
 * 계좌에 대한 모든 입금과 출금은 Activity로 관리되며,
 * Account는 ActivityWindow에서 일정 기간에 해당하는 활동을 볼 수 있습니다.
 */
public class Account {

	private final AccountId id;

	/**
	 * 계좌의 기준 잔고입니다. activityWindow의 첫번째 활동 바로 전의 잔고를 표현합니다.
	 * 현재 총 잔고는 기준 잔고 + 모든 활동들의 잔고를 합친 값 입니다.
	 */
	private final Money baselineBalance;
	private final ActivityWindow activityWindow;

	private Account(AccountId id, Money baselineBalance, ActivityWindow activityWindow) {
		this.id = id;
		this.baselineBalance = baselineBalance;
		this.activityWindow = activityWindow;
	}

	public static Account withoutId(
		Money baselineBalance,
		ActivityWindow activityWindow
	) {
		return new Account(null, baselineBalance, activityWindow);
	}

	public static Account withId(
		AccountId accountId,
		Money baselineBalance,
		ActivityWindow activityWindow
	) {
		return new Account(accountId, baselineBalance, activityWindow);
	}

	public Optional<AccountId> getId() {
		return Optional.ofNullable(this.id);
	}

	public ActivityWindow getActivityWindow() {
		return activityWindow;
	}

	public Money calculateBalance() {
		return Money.add(
			this.baselineBalance,
			this.activityWindow.calculateBalance(this.id)
		);
	}

	public boolean withdraw(Money money, AccountId targetAccountId) {

		if (!mayWithDraw(money)) {
			return false;
		}

		Activity withdrawal = new Activity(
			this.id,
			this.id,
			targetAccountId,
			LocalDateTime.now(),
			money
		);
		this.activityWindow.addActivity(withdrawal);

		return true;
	}

	private boolean mayWithDraw(Money money) {
		return Money.add(
			this.calculateBalance(),
			money.negate()
		).isPositiveOrZero();
	}

	public boolean deposit(Money money, AccountId sourceAccountId) {
		Activity deposit = new Activity(
			this.id,
			sourceAccountId,
			this.id,
			LocalDateTime.now(),
			money
		);
		this.activityWindow.addActivity(deposit);

		return true;
	}

	public record AccountId(Long value) {
	}
}
