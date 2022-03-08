
drop table if exists accounts;
drop table if exists clients;



CREATE TABLE clients (
	id SERIAL PRIMARY KEY,
	firstName VARCHAR (50) NOT NULL,
	lastName VARCHAR (50) NOT NULL,
	age INTEGER not NULL CHECK (age >= 18)
);

CREATE TABLE accounts (
	id SERIAL PRIMARY key,
	account_type VARCHAR(10) NOT NULL,
	balance DECIMAL( 12, 2) NOT NULL DEFAULT 0 ,
	client_id INTEGER NOT NULL
	
	CONSTRAINT chk_type CHECK (account_type IN ('Checking', 'Savings',  'MMA', 'CD')),
	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE
	
);

INSERT INTO clients (firstName, lastName, age)
VALUES
('Scrooge', 'McDuck', 89),
('Jane', 'Doe', 27),
('John', 'Smith', 32),
('Ragnar', 'Meozer', 45),
('Lulu', 'Meozer', 42);

INSERT INTO accounts (account_type, balance, client_id)
VALUES
('Savings', 109533.12, 1),
('Savings', 102365.46, 1),
('Checking', 2564.81, 1),
('CD', 75532.13, 1),
('MMA', 145678.54, 1),
('Checking', 999.99, 2),
('Checking', 123.12, 2),
('Savings', 423.94, 2),
('Checking', 765.98, 3),
('Savings', 7892.56, 3),
('Savings', 8765.43, 3),
('MMA', 567.89, 3),
('Checking', 10784.32, 4),
('Checking', 7865.98, 4),
('MMA', 5978.65, 4),
('CD', 67892.56, 4),
('Savings', 10876.87, 4),
('Checking', 89576.85, 5),
('Checking', 7654.38, 5),
('Savings', 7869.65, 5),
('Savings', 6895.58, 5),
('CD', 6948.56, 5),
('CD', 35928.65, 5),
('MMA', 125678.34, 5);

select *
from clients; 

select *
from accounts; 
