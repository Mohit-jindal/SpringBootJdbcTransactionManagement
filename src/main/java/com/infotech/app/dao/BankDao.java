package com.infotech.app.dao;

import com.infotech.app.dao.exception.InsufficientAccountBalanceException;
import com.infotech.app.model.Account;

public interface BankDao {

	public abstract void deposit(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException;
	public abstract void withdraw(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException;
}
