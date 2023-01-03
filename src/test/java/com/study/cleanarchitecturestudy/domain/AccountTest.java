package com.study.cleanarchitecturestudy.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

	Account account;
	Account.AccountId account2;

	@BeforeEach
	void setUp() {
		//given
		LocalDateTime startDate = LocalDateTime.of(2022, 12, 24, 0, 0);
		LocalDateTime inBetweenDate = LocalDateTime.of(2022, 12, 25, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2022, 12, 26, 0, 0);
		Account.AccountId account1 = new Account.AccountId(1L);
		account2 = new Account.AccountId(2L);

		List<Activity> activities = new ArrayList<>(Arrays.asList(
			new Activity(account1, account1, account2, startDate, Money.of(1000L)),
			new Activity(account1, account2, account1, inBetweenDate, Money.of(500L)),
			new Activity(account1, account1, account2, endDate, Money.of(2000L))
		));

		ActivityWindow window = new ActivityWindow(activities);

		account = Account.withId(account1, Money.of(3500L), window);
	}

	@Test
	@DisplayName("현재 잔액 계산")
	void calculateBalance() {
		Money balance = account.calculateBalance();

		assertThat(balance).isEqualTo(Money.of(1000L));
	}

	@Test
	@DisplayName("출금 성공 테스트")
	void withdraw() {
		boolean success = account.withdraw(Money.of(1000L), account2);

		assertThat(success).isTrue();
		assertThat(account.getActivityWindow().getActivities()).hasSize(4);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(0L));
	}

	@Test
	@DisplayName("출금 실패 테스트")
	void withdraw_fail() {
		boolean fail = account.withdraw(Money.of(1001L), account2);

		assertThat(fail).isFalse();
		assertThat(account.getActivityWindow().getActivities()).hasSize(3);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L));
	}

	@Test
	@DisplayName("입금 성공 테스트")
	void deposit() {
		boolean success = account.deposit(Money.of(1000L), account2);

		assertThat(success).isTrue();
		assertThat(account.getActivityWindow().getActivities()).hasSize(4);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(2000L));
	}
}