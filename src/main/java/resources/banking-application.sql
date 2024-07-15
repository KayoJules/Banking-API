DROP TABLE users;
DROP TABLE accounts;
DROP TABLE transactions;

TRUNCATE TABLE users;
TRUNCATE TABLE accounts;
TRUNCATE TABLE transactions;


CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT null,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(40) NOT NULL
);

CREATE TYPE acc_type AS ENUM ('CHECKING', 'SAVINGS');

CREATE TABLE IF NOT EXISTS accounts (
    account_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    account_type acc_type NOT NULL,
    balance NUMERIC NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TYPE action_type AS ENUM ('DEPOSIT', 'WITHDRAWAL');

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type action_type  NOT NULL,
    amount NUMERIC NOT NULL,
    timestamp TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

INSERT INTO users(username, pass, first_name, last_name) VALUES
('jdoe', 'password', 'John', 'Doe'),
('kianak', 'rg2789', 'Kiana', 'Kaslana'),
('rinas', 'ej28r2', 'Alexandria', 'Sebastiane'),
('lisam', 'he98sf', 'Lisa', 'Minci');
select * from users;


INSERT INTO accounts(user_id, account_type, balance) VALUES
(1, 'CHECKING', 1000.00),
(1, 'SAVINGS', 3375.50),
(2, 'CHECKING', 793.90),
(3, 'CHECKING', 840.19),
(4. 'SAVINGS', 9445.56),
(3, 'SAVINGS', 2480.43),
(4, 'CHECKING', 1480.97);

SELECT * FROM accounts;


INSERT INTO transactions(account_id, transaction_type, amount) VALUES
(1, 'DEPOSIT', 10.93),
()







;

select * from transactions;
