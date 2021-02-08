CREATE TABLE client(
    id VARCHAR(255) not null primary key,
    name VARCHAR(255),
    secname VARCHAR(255),
    patronymic VARCHAR(255),
    phone_number VARCHAR(255),
    email VARCHAR(255),
    pasport_number VARCHAR(255)
    )


CREATE TABLE credit(
    id VARCHAR(255) not null primary key,
    credit_name VARCHAR(255),
    credit_limit BIGINT not null,
    percent INTEGER)

CREATE TABLE credit_offer(
    id VARCHAR(255) not null primary key,
    credit_sum BIGINT,
    months_of_credit BIGINT,
    client_id VARCHAR(255) not null,
    credit_id VARCHAR(255) not null,
    FOREIGN KEY(client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY(credit_id) REFERENCES credit(id) ON DELETE CASCADE)

CREATE TABLE payment_graphic(
    id VARCHAR(255) not null primary key,
    credit_body BIGINT,
    credit_percents BIGINT,
    date Date,
    ostatok BIGINT,
    payment_sum BIGINT,
    credit_offer_id VARCHAR(255) not null,
    FOREIGN KEY(credit_offer_id) REFERENCES credit_offer(id)) ON DELETE CASCADE