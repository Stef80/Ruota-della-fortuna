create table users
(
    id       varchar(36) primary key,
    tipo     numeric(1)  not null,
    name     varchar(50) not null,
    surname  varchar(50) not null,
    nickname varchar(50) not null unique,
    email    varchar(50) not null unique,
    password varchar     not null
);

create table matches
(
    id   varchar(36) primary key,
    date varchar(10) not null,
    time varchar(10) not null
);

create table phrases
(
    phrase varchar(60) primary key,
    theme  varchar(60) not null
);

create table manches
(
    number numeric(1)                              not null,
    id     varchar(36) references matches (id)     not null,
    phrase varchar(60) references phrases (phrase) not null,
    primary key (number, id)
);

create table moves
(
    moveid   varchar(36) primary key,
    id       varchar(36) references users (id)      not null,
    movetype varchar(10)                            not null,
    outcome  numeric(1)                             not null,
    idmanche varchar(36) references manches (id)    not null,
    number   numeric(1) references manches (number) not null
);

create table matchwinners
(
    idmatch  varchar(36) references matches (id) not null,
    idplayer varchar(36) references users (id)   not null,
    amount   numeric(6)                          not null,
    primary key (idmatch, idplayer)
);

create table manchewinners
(
    id       varchar(36) references manches (id),
    number   numeric(1) references manches (number),
    idplayer varchar(36) references users (id),
    amount   numeric(6) not null,
    primary key (id, number, idplayer)
);

create table manchejoiners
(
    id       varchar(36) references manches (id),
    number   numeric(1) references manches (number),
    idplayer varchar(36) references users (id),
    oberser  boolean not null,
    primary key (id, number, idplayer)
);