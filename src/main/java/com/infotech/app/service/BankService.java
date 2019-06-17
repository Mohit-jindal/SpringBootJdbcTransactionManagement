package com.infotech.app.service;

import com.infotech.app.dao.exception.InsufficientAccountBalanceException;
import com.infotech.app.model.Account;

public interface BankService {

	public abstract void transferFund(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException;
}
