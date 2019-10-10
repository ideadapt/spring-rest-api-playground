INSERT INTO users(name,password,enabled) VALUES ('hans','meier', true);
INSERT INTO users(name,password,enabled) VALUES ('admin','admin', true);

INSERT INTO user_roles (name, role) VALUES ('hans', 'ROLE_USER');
INSERT INTO user_roles (name, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (name, role) VALUES ('admin', 'ROLE_ADMIN');
