drop table if exists histories;
drop table if exists securities;

create table securities (
    id integer not null unique,
    secid varchar(30) not null primary key,
    regnumber varchar(30) not null,
    name varchar (100) not null,
    emitent_title text not null
);

create table histories (
    id serial not null primary key,
    secid varchar(30) not null,
    tradedate date not null,
    numtrades integer not null,
    open real not null,
    close real not null,
    foreign key (secid) references securities(secid)
);