CREATE TABLE users
(
    name VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    enabled  TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (name)
);

CREATE TABLE user_roles
(
    user_role_id int(11)     NOT NULL AUTO_INCREMENT,
    name     varchar(20) NOT NULL,
    role         varchar(20) NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_username_role (role, name),
    KEY fk_username_idx (name),
    CONSTRAINT fk_username FOREIGN KEY (name) REFERENCES users (name)
);