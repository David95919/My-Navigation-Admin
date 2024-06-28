create table if not exists t_user
(
    `id`          int primary key not null,
    `username`    char(50)        not null,
    `password`    char(50)        not null,
    `create_time` datetime        not null,
    `update_time` datetime
);