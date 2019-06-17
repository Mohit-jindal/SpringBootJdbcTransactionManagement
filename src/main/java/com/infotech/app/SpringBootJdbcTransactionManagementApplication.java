package com.infotech.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.infotech.app.dao.exception.InsufficientAccountBalanceException;
import com.infotech.app.model.Account;
import com.infotech.app.service.BankService;
import com.infotech.app.service.impl.BankServiceImpl;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true) //this is optional for @Transaction annotation is service impl
public class SpringBootJdbcTransactionManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcTransactionManagementApplication.class, args);
		
		BankService bankService = context.getBean("bankServiceImpl", BankServiceImpl.class);
		Account fromAccount = new Account();
		fromAccount.setAccountNumber(1122330056L);
		
		Account toAccount = new Account();
		toAccount.setAccountNumber(3355330099L);
		
		try
		{
			bankService.transferFund(fromAccount, toAccount, 1000.00);
		}
		catch (InsufficientAccountBalanceException e) {
			e.printStackTrace();
		}
		context.close();
	}

}
