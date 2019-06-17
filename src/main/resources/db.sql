
create table icici_bank(
		account_no double not null,
		account_type varchar(255) NOT NULL,
		account_balance double DEFAULT NULL,
		account_holder_name varchar(255) NOT NULL,
		primary key(account_no)
);