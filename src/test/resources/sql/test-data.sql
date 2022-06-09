#Users
insert into users (email, first_name, last_name, password, phone, username) values ('redstrike@gmail.com','Red','Strike','pass1','+380 63 108 7165','RedStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('darkstrike@gmail.com','Dark','Strike','pass2','+380 63 108 7165','DarkStriker');

#Wallets
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Defaut',1);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Defaut',2);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Special',1);

#Roles
insert into roles (name) values ('ROLE_UNVERIFIED');
insert into roles (name) values ('ROLE_BLOCKED');
insert into roles (name) values ('ROLE_CUSTOMER');
insert into roles (name) values ('ROLE_MASTER');
insert into roles (name) values ('ROLE_MANAGER');
insert into roles (name) values ('ROLE_ADMIN');

#User_has_role
insert into user_has_role (user_id, role_id) values (1,1);
insert into user_has_role (user_id, role_id) values (1,3);
insert into user_has_role (user_id, role_id) values (2,4);

#User addresses
insert into user_addresses (user_id, city, country, local_address) values (1,'Kyiv','Ukraine','some street');

