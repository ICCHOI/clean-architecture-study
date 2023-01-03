package com.study.cleanarchitecturestudy.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivityWindowTest {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private ActivityWindow window;
	private Account.AccountId account1;
	private Account.AccountId account2;


	@BeforeEach
	void setUp() {
		//given
		startDate = LocalDateTime.of(2022, 12, 24, 0, 0);
		LocalDateTime inBetweenDate = LocalDateTime.of(2022, 12, 25, 0, 0);
		endDate = LocalDateTime.of(2022, 12, 26, 0, 0);
		account1 = new Account.AccountId(1L);
		account2 = new Account.AccountId(2L);


		List<Activity> activities = List.of(
			new Activity(account1, account1, account2, startDate, Money.of(1000L)),
			new Activity(account1, account2, account1, inBetweenDate, Money.of(500L)),
			new Activity(account1, account1, account2, endDate, Money.of(2000L))
		);

		window = new ActivityWindow(activities);
	}

	@Test
	@DisplayName("Activity timestamp 오름차순 정렬 테스트")
	void getStartTimestamp() {
		//when
		LocalDateTime startTimestamp = window.getStartTimestamp();

		//then
		assertThat(startTimestamp).isEqualTo(startDate);
	}

	@Test
	@DisplayName("Activity timestamp 내림차순 정렬 테스트")
	void getEndTimestamp() {
		//when
		LocalDateTime startTimestamp = window.getEndTimestamp();

		//then
		assertThat(startTimestamp).isEqualTo(endDate);
	}

	@Test
	@DisplayName("잔액 계산 테스트")
	void calculateBalance() {
		//when
		Money moneyOfAccount1 = window.calculateBalance(account1);
		Money moneyOfAccount2 = window.calculateBalance(account2);

		//then
		assertThat(moneyOfAccount1).isEqualTo(Money.of(-2500L));
		assertThat(moneyOfAccount2).isEqualTo(Money.of(2500L));
	}

}