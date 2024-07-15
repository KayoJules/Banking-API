DROP TABLE users;
DROP TABLE accounts;
DROP TABLE transactions;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT null,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(40) NOT NULL
);

CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    balance NUMERIC NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAWAL');

CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    action_type transaction_type NOT NULL,
    amount NUMERIC NOT NULL,
    timestamp TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

insert into users(username, pass, first_name, last_name) values ('jdoe', 'password', 'John', 'Doe');
select * from users;
insert into accounts(user_id, balance) values (1, 1098.84);
select * from accounts;
insert into transactions(account_id, action_type, amount) values (1, 'DEPOSIT', 10.93);
select * from transactions;
