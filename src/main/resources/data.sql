create table if not exists t_user
(
    id          integer primary key not null,
    username    VARCHAR(24) UNIQUE  not null,
    password    VARCHAR(24)         not null,
    create_time datetime            not null,
    update_time datetime
);

create table if not exists t_nav
(
    id          integer primary key not null /*autoincrement needs PK*/,
    name        char(24) UNIQUE     not null,
    url         char(50)            not null,
    description text,
    tags        VARCHAR(255),
    create_time datetime            not null,
    update_time datetime
);