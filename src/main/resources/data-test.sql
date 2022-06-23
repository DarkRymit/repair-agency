
insert into users (email, first_name, last_name, password, phone, username) values ('redstrike@gmail.com','Red','Strike','$2a$10$jn4vz7Xh1u358Vi1K6.r5u7ipxbRP4Kj5sGw0Nl02NQ.IGRxRz/Ha','+380 63 108 7165','RedStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('darkstrike@gmail.com','Dark','Strike','$2a$10$JAYo8LApXbfu/93omHQ8Cu82AWzf7agoTkaLbazkqSpsFXwEGmGzm','+380 63 108 7165','DarkStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('masterstrike@gmail.com','Master','Strike','$2a$10$JAYo8LApXbfu/93omHQ8Cu82AWzf7agoTkaLbazkqSpsFXwEGmGzm','+380 63 108 9165','MasterStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('customerstrike@gmail.com','Customer','Strike','$2a$10$jn4vz7Xh1u358Vi1K6.r5u7ipxbRP4Kj5sGw0Nl02NQ.IGRxRz/Ha','+380 63 108 9165','CustomerStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('secondstrike@gmail.com','Second','Strike','$2a$10$jn4vz7Xh1u358Vi1K6.r5u7ipxbRP4Kj5sGw0Nl02NQ.IGRxRz/Ha','+380 63 108 9185','SecondStriker');
insert into users (email, first_name, last_name, password, phone, username) values ('master2strike@gmail.com','Master2','Strike','$2a$10$JAYo8LApXbfu/93omHQ8Cu82AWzf7agoTkaLbazkqSpsFXwEGmGzm','+380 63 108 9154','Master2Striker');

insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Default',1);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Default',2);
insert into wallets (money_amount, money_currency, name, user_id) values (100.0,'USD','Special',1);
insert into wallets (money_amount, money_currency, name, user_id) values (120.0,'USD','Default',4);
insert into wallets (money_amount, money_currency, name, user_id) values (20.0,'USD','Default',5);

insert into roles (name) values ('UNVERIFIED');
insert into roles (name) values ('BLOCKED');
insert into roles (name) values ('CUSTOMER');
insert into roles (name) values ('MASTER');
insert into roles (name) values ('MANAGER');
insert into roles (name) values ('ADMIN');

insert into user_has_role (user_id, role_id) values (1,1);
insert into user_has_role (user_id, role_id) values (1,3);
insert into user_has_role (user_id, role_id) values (2,4);
insert into user_has_role (user_id, role_id) values (3,4);
insert into user_has_role (user_id, role_id) values (3,5);

insert into user_addresses (user_id, city, country, local_address) values (1,'Kyiv','Ukraine','some street');

insert into repair_categories (name) values ('NOTEBOOK');
insert into repair_categories (name) values ('PC');
insert into repair_categories (name) values ('MOBILE');

insert into repair_works (name, price_amount, price_currency,category_id) values ('BATTERY_REPLACE',20.40,'USD',1);
insert into repair_works (name, price_amount, price_currency,category_id) values ('SCREEN_REPLACE',52.40,'USD',1);
insert into repair_works (name, price_amount, price_currency,category_id) values ('DATA_RECOVERY',12.10,'USD',1);
insert into repair_works (name, price_amount, price_currency,category_id) values ('DATA_RECOVERY',10.50,'USD',2);
insert into repair_works (name, price_amount, price_currency,category_id) values ('BATTERY_REPLACE',22.40,'USD',3);

insert into repair_work_has_status (repair_work_id, statuses) values (1,'VIP');
insert into repair_work_has_status (repair_work_id, statuses) values (2,'VIP');
insert into repair_work_has_status (repair_work_id, statuses) values (2,'SUSPENDED');

insert into receipt_statuses (name) values ('WAIT_FOR_PAYMENT');
insert into receipt_statuses (name) values ('PAID');
insert into receipt_statuses (name) values ('IN_WORK');
insert into receipt_statuses (name) values ('DONE');
insert into receipt_statuses (name) values ('CANCELED');

insert into receipts (user_id,receipt_status_id,category_id,master_id,price_amount, price_currency,note,creation_time) values (4,2,1,3,84.9,'USD','Typical note','2022-01-10 14:23:22');
insert into receipts (user_id,receipt_status_id,category_id,master_id,price_amount, price_currency,note,creation_time) values (5,1,1,3,40.3,'USD','Typical note','2022-12-14 16:00:40');
insert into receipts (user_id,receipt_status_id,category_id,master_id,price_amount, price_currency,note,creation_time) values (5,5,1,6,40.3,'USD','Typical note','2022-10-14 17:00:40');
insert into receipts (user_id,receipt_status_id,category_id,master_id,price_amount, price_currency,note,creation_time) values (4,4,1,6,78.9,'USD','Typical note','2022-02-10 17:34:31');

insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,1,20.40,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,2,52.40,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,3,12.10,'USD');

insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (2,1,11.10,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (2,2,14.10,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (2,3,15.10,'USD');

insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (3,2,11.10,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (3,3,14.10,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (3,3,15.10,'USD');

insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,1,18.40,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,2,50.40,'USD');
insert into receipt_items (receipt_id,repair_work_id, price_amount, price_currency) values (1,3,10.10,'USD');

insert into receipt_deliveries (receipt_id, city, country, local_address) values (1,'Kyiv','Ukraine','some street2');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (2,'Kyiv','Poland','some street3');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (3,'Kyiv','Ukraine','some street3');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (4,'Kyiv','Ukraine','some street3');