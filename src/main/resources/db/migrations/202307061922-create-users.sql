-- liquibase formatted sql
-- changeset lefteris.laskaridis@gmail.com:202307061922

create table users (
    id uuid          not null,
    version          bigint not null,
    created_at       timestamp not null,
    last_modified_at timestamp not null,
    full_name        varchar(100) not null,
    email            varchar(100) not null,
    password         varchar(100) not null,
    primary key (id)
);

create unique index unique_email
    on users (email);

-- rollback: drop table users;