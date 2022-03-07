CREATE TABLE clients (
	id SERIAL PRIMARY KEY,
	firstName VARCHAR (50) NOT NULL,
	lastName VARCHAR (50) NOT NULL
);

CREATE TABLE accounts (
	id SERIAL PRIMARY KEY
	name VARCHAR (200),
	type VARCHAR (50) NOT NULL,
	balance DOUBLE( 12, 2) NOT NULL DEFAULT 0 ,
	client_id INTEGER NOT NULL
	
	CONSTRAINT chk_type CHECK (type IN ('Checking', 'Savings',  'MMA', 'CD'),
	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE
	
);

INSERT INTO clients (firstName, lastName)
VALUES
('Caitlin', 'Talerico'),
('Jane', 'Doe'),
('John', 'Smith'),
('Ragnar', 'Meozer'),
('Lulu', 'Meozer');

INSERT INTO accounts (name, type, balance, client_id)
VALUES
('Personal Savings', 'Savings', 10953.12, 1),
('Vacation', 'Savings', 1023.46, 1),
('Main', 'Checking', 2564.81, 1),
('')

