# use CLEAN_DB=1 whenever you a) want this data to be redeployed b) changed this file

INSERT INTO users(name,password,enabled) VALUES ('test','test', true);
INSERT INTO users(name,password,enabled) VALUES ('admin','admin', true);

INSERT INTO user_roles (name, role) VALUES ('test', 'ROLE_USER');
INSERT INTO user_roles (name, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (name, role) VALUES ('admin', 'ROLE_ADMIN');
