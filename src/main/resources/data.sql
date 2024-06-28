create table if not exists t_user
(
    `id`          int primary key not null,
    `username`    char(50)        not null,
    `password`    char(50)        not null,
    `create_time` datetime        not null,
    `update_time` datetime
);

create table if not exists t_nav
(
    id          integer  not null /*autoincrement needs PK*/,
    name        char(24) not null,
    url         char(50) not null,
    description text,
    create_time datetime not null,
    update_time datetime
);