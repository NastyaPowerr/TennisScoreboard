create table players
(
    id   integer primary key auto_increment,
    name varchar not null unique
);

create table matches
(
    id      integer primary key auto_increment,
    player1 integer not null,
    player2 integer not null,
    winner  integer,
    foreign key (player1) REFERENCES players (id),
    foreign key (player2) REFERENCES players (id),
    foreign key (winner) REFERENCES players (id)
);
