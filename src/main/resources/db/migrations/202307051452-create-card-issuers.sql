-- liquibase formatted sql
-- changeset lefteris.laskaridis@gmail.com:202307051452

create table card_issuers (
    id uuid                       not null,
    version                       bigint not null,
    created_at                    timestamp not null,
    last_modified_at              timestamp not null,
    issuer_identification_number  varchar(6) not null,
    bank_country_code             varchar(2) not null,
    clearing_cost_id              uuid not null,
    primary key (id)
);

create unique index unique_issuer_identification_number
    on card_issuers (issuer_identification_number);

alter table card_issuers add constraint fk_card_issuers_clearing_cost
    foreign key (clearing_cost_id) references clearing_costs(id)
    on delete cascade;

-- rollback: drop table card_issuers;