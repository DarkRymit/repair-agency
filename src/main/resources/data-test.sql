
insert into users (email, first_name, last_name, password, phone, username) values ('redstrike@gmail.com','Red','Strike','$2a$10$jn4vz7Xh1u358Vi1K6.r5u7ipxbRP4Kj5sGw0Nl02NQ.IGRxRz/Ha','+380 63 108 7165','RedStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('darkstrike@gmail.com','Dark','Strike','$2a$10$JAYo8LApXbfu/93omHQ8Cu82AWzf7agoTkaLbazkqSpsFXwEGmGzm','+380 63 108 7165','DarkStriker');

insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Default',1);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Default',2);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Special',1);

insert into roles (name) values ('UNVERIFIED');
insert into roles (name) values ('BLOCKED');
insert into roles (name) values ('CUSTOMER');
insert into roles (name) values ('MASTER');
insert into roles (name) values ('MANAGER');
insert into roles (name) values ('ADMIN');

insert into user_has_role (user_id, role_id) values (1,1);
insert into user_has_role (user_id, role_id) values (1,3);
insert into user_has_role (user_id, role_id) values (2,4);

insert into user_addresses (user_id, city, country, local_address) values (1,'Kyiv','Ukraine','some street');

