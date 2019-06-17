package com.infotech.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.infotech.app.dao.BankDao;
import com.infotech.app.dao.exception.InsufficientAccountBalanceException;
import com.infotech.app.dao.mapper.AccountRowMapper;
import com.infotech.app.model.Account;

@Repository
public class BankDaoImpl implements BankDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void deposit(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException {

		Account accountFromDb = getAccountFromDb(fromAccount.getAccountNumber());
		
		Double accountBalance = accountFromDb.getAccountBalance() - amount;
		if(accountFromDb.getAccountBalance() >= amount)
		{
			String SQL = "UPDATE icici_bank set account_balance1=? WHERE account_no=?";
			int update = getJdbcTemplate().update(SQL, accountBalance, fromAccount.getAccountNumber());

			if(update > 0)
			{
				System.out.println("Amount Rs:"+ amount 
						+ " is transferred from Account No:"
						+ fromAccount.getAccountNumber() + " to Account No:"
						+ toAccount.getAccountNumber());
			}
			else
			{
				throw new InsufficientAccountBalanceException("Insufficient Account Balance");
			}
		}
	}
	
	@Override
	public void withdraw(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException {

		Account accountFromDb = getAccountFromDb(toAccount.getAccountNumber());
		Double accountBalance = accountFromDb.getAccountBalance() + amount;
		String SQL = "UPDATE icici_bank set account_balance=? WHERE account_no=?";
		int update = getJdbcTemplate().update(SQL, accountBalance, toAccount.getAccountNumber());
		if(update > 0)
		{
			System.out.println("Amount Rs:"+ amount  +" is deposited in Account No:" + toAccount.getAccountNumber());
		}
	}
	
	private Account getAccountFromDb(Long accountNumber)
	{
		String SQL = "SELECT * FROM icici_bank WHERE account_no=?";
		Account account =  getJdbcTemplate().queryForObject(SQL, new AccountRowMapper(),accountNumber);
		return account;
	}

}
