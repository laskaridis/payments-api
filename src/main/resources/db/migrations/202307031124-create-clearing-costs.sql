-- liquibase formatted sql
-- changeset lefteris.laskaridis@gmail.com:202307-31124

create table clearing_costs (
    id uuid                not null,
    version                bigint not null,
    created_at             timestamp not null,
    last_modified_at       timestamp not null,
    card_issuing_country   varchar(3) not null,
    clearing_cost_amount   numeric not null,
    clearing_cost_currency varchar(3) not null default 'USD',
    primary key (id)
);

create unique index unique_card_issuing_country on clearing_costs (card_issuing_country);

-- rollback: drop table clearing_costs;