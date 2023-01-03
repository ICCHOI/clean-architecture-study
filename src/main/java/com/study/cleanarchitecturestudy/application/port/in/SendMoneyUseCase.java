package com.study.cleanarchitecturestudy.application.port.in;

public interface SendMoneyUseCase {

	boolean sendMoney(MoneyCommand.SendMoney command);
}
