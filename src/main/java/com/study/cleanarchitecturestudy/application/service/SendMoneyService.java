package com.study.cleanarchitecturestudy.application.service;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.study.cleanarchitecturestudy.application.port.in.MoneyCommand;
import com.study.cleanarchitecturestudy.application.port.in.SendMoneyUseCase;
import com.study.cleanarchitecturestudy.application.port.out.AccountLock;
import com.study.cleanarchitecturestudy.application.port.out.LoadAccountPort;
import com.study.cleanarchitecturestudy.application.port.out.UpdateAccountStatePort;
import com.study.cleanarchitecturestudy.domain.Account;
import com.study.cleanarchitecturestudy.domain.Account.AccountId;
import com.study.cleanarchitecturestudy.domain.Money;

import common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

	private final LoadAccountPort loadAccountPort;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final AccountLock accountLock;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean sendMoney(MoneyCommand.SendMoney command) {

		checkThreshold(command);

		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

		Account sourceAccount = loadAccountPort.loadAccount(
			command.sourceAccountId(),
			baselineDate);

		Account targetAccount = loadAccountPort.loadAccount(
			command.targetAccountId(),
			baselineDate);

		AccountId sourceAccountId = sourceAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.money(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(MoneyCommand.SendMoney command) {
		Money money = command.money();
		Money threshold = moneyTransferProperties.getMaximumTransferThreshold();

		if (money.isGreaterThan(threshold)) {
			throw new ThresholdExceededException(threshold, money);
		}
	}
}
