
drop table if exists accounts;
drop table if exists clients;



CREATE TABLE clients (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR (50) NOT NULL,
	last_name VARCHAR (50) NOT NULL,
	age INTEGER not NULL CHECK (age >= 18),
	UNIQUE(first_name, last_name, age)
	
);

CREATE TABLE accounts (
	id SERIAL PRIMARY key,
	account_type VARCHAR(10) NOT NULL,
	balance DECIMAL( 12, 2) NOT NULL DEFAULT 0 ,
	client_id INTEGER NOT NULL,
	account_number INTEGER NOT NULL DEFAULT 1
	
	CONSTRAINT chk_type CHECK (account_type IN ('Checking', 'Savings',  'MMA', 'CD')),
	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE
	
);

INSERT INTO clients (first_name, last_name, age)
VALUES
('Scrooge', 'McDuck', 89),
('Ragnar', 'Meozer', 45),
('Lulu', 'Meozer', 42),
('Peter', 'Parker', 21),
('Natasha', 'Romanoff', 32),
('Bruce', 'Banner', 43),
('Miles', 'Morales', 18),
('Gwen', 'Stacy', 18),
('Miguel', 'O''Hara', 46),
('Wade', 'Wilson', 99);

INSERT INTO accounts (account_type, balance, client_id, account_number)
VALUES
('Savings', 109533.12, 1, 1),
('Savings', 102365.46, 1, 2),
('Checking', 2564.81, 1, 3),
('CD', 75532.13, 1, 4),
('MMA', 145678.54, 1, 5),
('Checking', 999.99, 2, 1),
('Checking', 123.12, 2, 2),
('Savings', 423.94, 2, 3),
('Checking', 765.98, 3, 1),
('Savings', 7892.56, 3, 2),
('Savings', 8765.43, 3, 3),
('MMA', 567.89, 3, 4),
('Checking', 10784.32, 4, 1),
('Checking', 7865.98, 4, 2),
('MMA', 5978.65, 4, 3),
('CD', 67892.56, 4, 4),
('Savings', 10876.87, 4, 5),
('Checking', 89576.85, 5, 1),
('Checking', 7654.38, 5, 2),
('Savings', 7869.65, 5, 3),
('Savings', 6895.58, 5, 4),
('CD', 6948.56, 5, 5),
('CD', 35928.65, 5, 6),
('MMA', 125678.34, 5, 7),
('Savings', 103343.12, 6, 1),
('Savings', 15235.46, 6, 2),
('Checking', 5325.81, 6, 3),
('CD', 9426.76, 6, 4),
('MMA', 145678.54, 6, 5),
('Checking', 999.99, 7, 1),
('Checking', 123.12, 7, 2),
('Savings', 423.94, 7, 3),
('Checking', 765.98, 7, 4),
('Savings', 7892.56, 8, 1),
('Savings', 8765.43, 8, 2),
('MMA', 567.89, 8, 3),
('Checking', 10784.32, 9, 1),
('Checking', 7865.98, 9, 2),
('MMA', 5978.65, 9, 3),
('CD', 67892.56, 9, 4),
('Savings', 10876.87, 9, 5),
('Checking', 89576.85, 10, 1),
('Checking', 7654.38, 10, 2),
('Savings', 7869.65, 10, 3),
('Savings', 6895.58, 10, 4),
('CD', 6948.56, 10, 5),
('CD', 35928.65, 10, 6),
('MMA', 125678.34, 10, 7);


select *
from clients; 

select *
from accounts;

