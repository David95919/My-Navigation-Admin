CREATE TABLE IF NOT EXISTS t_user
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username    VARCHAR(24) UNIQUE             NOT NULL,
    password    CHAR(32)                       NOT NULL,
    create_time DATETIME                       NOT NULL,
    update_time DATETIME
) charset utf8
  collate utf8_general_ci;

CREATE TABLE IF NOT EXISTS t_nav
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(24) UNIQUE             NOT NULL,
    url         VARCHAR(50)                    NOT NULL,
    description TEXT,
    category    INT,
    create_time DATETIME                       NOT NULL,
    update_time DATETIME
) charset utf8
  collate utf8_general_ci;

CREATE TABLE IF NOT EXISTS t_category
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(24) UNIQUE             NOT NULL,
    create_time DATETIME                       NOT NULL,
    update_time DATETIME
) charset utf8
  collate utf8_general_ci;

CREATE TABLE IF NOT EXISTS t_tag
(
    id          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(24) UNIQUE             NOT NULL,
    create_time DATETIME                       NOT NULL,
    update_time DATETIME
) charset utf8
  collate utf8_general_ci;

CREATE TABLE IF NOT EXISTS t_nav_tag
(
    id     INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nav_id INT                            NOT NULL,
    tag_id INT                            NOT NULL
) charset utf8
  collate utf8_general_ci;
