create table if not exists t_user
(
    id          integer primary key not null /*autoincrement needs PK*/,
    username    varchar(24) UNIQUE  not null,
    password    char(32)            not null,
    create_time datetime            not null,
    update_time datetime
);

create table if not exists t_nav
(
    id          integer primary key not null /*autoincrement needs PK*/,
    name        varchar(24) UNIQUE  not null,
    url         varchar(50)         not null,
    description text,
    tags        VARCHAR(255),
    category    integer,
    create_time datetime            not null,
    update_time datetime
);

create table if not exists t_category
(
    id          integer primary key not null /*autoincrement needs PK*/,
    name        varchar(24) UNIQUE  not null,
    create_time datetime            not null,
    update_time datetime
);

create table if not exists t_tag
(
    id          integer primary key not null /*autoincrement needs PK*/,
    name        varchar(24) UNIQUE  not null,
    create_time datetime            not null,
    update_time datetime
)