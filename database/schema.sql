create database second_hand;

use second_hand;

create table postings (
    posting_id varchar(64) PRIMARY KEY not null,
    posting_date varchar(64) not null,
    name varchar(128) not null, 
    email varchar(64) PRIMARY KEY not null,
    phone varchar(64) not null,
    title varchar(128) not null,
    image varchar(256) not null,

    -- primary key(posting_id);
);