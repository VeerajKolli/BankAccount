create table Customer
(
    customerId   BIGINT auto_increment,
    name CHARACTER VARYING(1000000000),
    address  CHARACTER VARYING(1000000000),
    bsn           CHARACTER VARYING(1000000000),
    constraint "customer_pk"
        primary key (customerId)
);
create table Account
(
    account_number BIGINT auto_increment,
    account_type   CHARACTER VARYING(1000000000),
    iban           CHARACTER VARYING(1000000000),
    customer_Id    BIGINT,
    balance        DOUBLE PRECISION,
    constraint "Account_pk"
        primary key (account_number),
    constraint "ACCOUNT_CUSTOMER_ID_fk"
        foreign key (customer_Id) references Customer (customerId)
);
create table Card
(
    cardId             BIGINT auto_increment,
    account_number BIGINT,
    card_number  CHARACTER VARYING(1000000000),
    card_type    CHARACTER VARYING(1000000000),
    constraint "card_pk"
        primary key (cardId),
    constraint "ACCOUNT_NUMBER_fk"
        foreign key (account_number) references Account (account_number)
);
create table Transaction
(
    transactionId             BIGINT auto_increment,
    account_number BIGINT,
    customer_id  CHARACTER VARYING(1000000000),
    date    DATE,
    type    CHARACTER VARYING(1000000000),
    amount  DOUBLE PRECISION,
    balance DOUBLE PRECISION,
    constraint "transaction_pk"
        primary key (transactionId),
    constraint "ACCOUNT_TRX_NUMBER_fk"
        foreign key (account_number) references Account (account_number),
    constraint "CUSTOMER_ID_fk"
        foreign key (customer_id) references Customer (customerId)
);

