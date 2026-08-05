create sequence users_seq start with 1 increment by 1;

create table users
(
    ID             number(19, 0)      not null,
    CREATED_AT     timestamp(6)       not null,
    UPDATED_AT     timestamp(6),
    USERNAME       varchar2(60 char)  not null unique,
    PASSWORD       varchar2(255 char) not null,
    ENABLED        number(1, 0)       not null,
    LOCKED         number(1, 0)       not null,
    PHONE_NUMBER   varchar2(20 char)  not null unique,
    PHONE_VERIFIED number(1, 0)       not null,
    EMAIL          varchar2(100 char) unique,
    EMAIL_VERIFIED number(1, 0)       not null,
    ADDRESS        varchar2(255 char),
    primary key (id)
);
