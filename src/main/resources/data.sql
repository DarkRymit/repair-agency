insert into app_currencies (code)
values ('USD');
insert into app_currencies (code)
values ('UAH');

insert into app_locales(lang)
values ('en');
insert into app_locales(lang)
values ('uk');

insert into roles (name)
values ('UNVERIFIED');
insert into roles (name)
values ('BLOCKED');
insert into roles (name)
values ('CUSTOMER');
insert into roles (name)
values ('MASTER');
insert into roles (name)
values ('MANAGER');
insert into roles (name)
values ('ADMIN');

insert into receipt_statuses (name)
values ('CREATED');
insert into receipt_statuses (name)
values ('WAIT_FOR_PAYMENT');
insert into receipt_statuses (name)
values ('PAID');
insert into receipt_statuses (name)
values ('IN_WORK');
insert into receipt_statuses (name)
values ('DONE');
insert into receipt_statuses (name)
values ('CANCELED');


insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 6, 3);

insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 4, 4);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 5, 4);

insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 2, 5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 3, 5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 6, 5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 6, 5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 6, 5);

insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 2, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 3, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 4, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 5, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (1, 6, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 1, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 3, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 4, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 5, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (2, 6, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 1, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 2, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 4, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 5, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (3, 6, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 1, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 2, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 3, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 5, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (4, 6, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (5, 1, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (5, 2, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (5, 3, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (5, 4, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (5, 6, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (6, 1, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (6, 2, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (6, 3, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (6, 4, 6);
insert into receipt_status_flows (from_status_id, to_status_id, role_id)
values (6, 5, 6);


insert into repair_categories (key_name)
values ('notebook');
insert into repair_categories (key_name)
values ('pc');
insert into repair_categories (key_name)
values ('mobile');

insert into repair_category_local_parts (name, language_id, category_id)
values ('Notebook', 1, 1);
insert into repair_category_local_parts (name, language_id, category_id)
values ('Ноутбук', 2, 1);
insert into repair_category_local_parts (name, language_id, category_id)
values ('PC', 1, 2);
insert into repair_category_local_parts (name, language_id, category_id)
values ('ПК', 2, 2);
insert into repair_category_local_parts (name, language_id, category_id)
values ('Mobile', 1, 3);
insert into repair_category_local_parts (name, language_id, category_id)
values ('Телефон', 2, 3);

insert into repair_works (key_name, category_id)
values ('battery-replace', 1);
insert into repair_works (key_name, category_id)
values ('screen-replace', 1);
insert into repair_works (key_name, category_id)
values ('data-recovery', 1);
insert into repair_works (key_name, category_id)
values ('data-recovery', 2);
insert into repair_works (key_name, category_id)
values ('battery-replace', 3);

insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (null, 20.40, 1, 1);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (52.40, 52.40, 1, 2);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (12.10, 22.10, 1, 3);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (10.50, null, 1, 4);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (22.40, 22.40, 1, 5);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (null, 816.00, 2, 1);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (2096.00, 2096.00, 2, 2);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (484.00, 884, 2, 3);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (420.00, null, 2, 4);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id)
values (896.00, 896.00, 2, 5);

insert into repair_work_local_parts (name, language_id, work_id)
values ('Battery replacement', 1, 1);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Заміна батареї', 2, 1);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Screen replace', 1, 2);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Заміна екрану', 2, 2);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Data recovery', 1, 3);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Відновлення данних', 2, 3);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Data recovery', 1, 4);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Відновлення данних', 2, 4);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Battery replacement', 1, 5);
insert into repair_work_local_parts (name, language_id, work_id)
values ('Заміна батареї', 2, 5);


insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('master2strike@gmail.com', 'Master2', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 63 108 9154',
        'Master2Striker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (1, 4);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('masterstrike@gmail.com', 'Master', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 63 108 9165',
        'MasterStriker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (2, 4);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('manager2strike@gmail.com', 'Manager2', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 63 158 9154',
        'Manager2Striker', '2020-01-10 14:23:22', '2020-02-10 14:53:22');
insert into user_has_role (user_id, role_id)
values (3, 5);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('managerstrike@gmail.com', 'Manager', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 93 108 9165',
        'ManagerStriker', '2020-01-10 14:23:22', '2020-02-10 14:53:22');
insert into user_has_role (user_id, role_id)
values (4, 5);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('adminstrike@gmail.com', 'Admin', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 93 108 9165',
        'AdminStriker', '2020-01-10 14:23:22', '2020-02-10 14:53:22');
insert into user_has_role (user_id, role_id)
values (5, 6);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('redstrike@gmail.com', 'Red', 'Strike',
        'ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f', '+380 63 108 7165',
        'RedStriker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (6, 3);
insert into user_has_role (user_id, role_id)
values (6, 1);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('darkstrike@gmail.com', 'Dark', 'Strike',
        'f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8', '+380 63 108 7165',
        'DarkStriker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (7, 3);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Default', 7);
insert into wallets (money_amount, currency_id, name, user_id)
values (400.0, 2, 'Гривневий', 7);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('customerstrike@gmail.com', 'Customer', 'Strike',
        'ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f', '+380 63 108 9165',
        'CustomerStriker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (8, 3);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Default', 8);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('secondstrike@gmail.com', 'Second', 'Strike',
        'ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f', '+380 63 108 9185',
        'SecondStriker', '2021-01-10 14:23:22', '2021-02-10 14:23:22');
insert into user_has_role (user_id, role_id)
values (9, 3);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Default1', 9);
insert into wallets (money_amount, currency_id, name, user_id)
values (643.23, 2, 'Default2', 9);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Special', 9);
insert into wallets (money_amount, currency_id, name, user_id)
values (400.21, 1, 'Second', 9);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('dayana.keebl@hotmail.com', 'William ', 'Ropp',
        'ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f', '+360 63 108 9185',
        'Unpleasant', '2021-07-11 14:23:22', '2021-07-11 14:23:22');
insert into user_has_role (user_id, role_id)
values (10, 3);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Default1', 10);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 2, 'Default2', 10);
insert into wallets (money_amount, currency_id, name, user_id)
values (100.0, 1, 'Special', 10);
insert into wallets (money_amount, currency_id, name, user_id)
values (120.0, 2, 'Default3', 10);
insert into wallets (money_amount, currency_id, name, user_id)
values (20.0, 1, 'Default4', 10);
insert into wallets (money_amount, currency_id, name, user_id)
values (200.0, 1, 'Second', 10);

insert into users (email, first_name, last_name, password, phone, username, creation_date, last_modified_date)
values ('joycelassiter@rhyta.com', 'Joyce ', 'Lassiter',
        'ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f', '+360 77 108 9185',
        'Unpleasant23', '2021-07-11 14:23:22', '2021-07-11 14:23:22');
insert into user_has_role (user_id, role_id)
values (11, 3);
insert into wallets (money_amount, currency_id, name, user_id)
values (500.0, 1, 'Second', 11);


insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 3, 1, 1, 84.9, 1, null, '2022-01-10 14:23:22','DarkStriker', 'DarkStriker', '2022-01-10 14:23:22');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (1, 1, 20.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (1, 2, 52.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (1, 3, 12.10);
insert into receipt_deliveries (receipt_id, city, country, local_address)
values (1, 'Kyiv', 'Ukraine', 'some street2');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (8, 2, 1, 2, 40.3, 1, 'Ostrożnie na schodach', '2021-12-14 16:00:40','CustomerStriker', 'ManagerStriker', '2021-12-14 16:00:40');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (2, 1, 11.10);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (2, 2, 14.10);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (2, 3, 15.10);
insert into receipt_deliveries (receipt_id, city, country, local_address)
values (2, 'Warszawa', 'Polska', 'Street Aleja Jana Pawła II');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 6, 1, 1, 40.3, 1, 'Typical note', '2021-10-14 17:00:40','DarkStriker', 'ManagerStriker', '2021-10-14 17:00:40');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (3, 2, 11.10);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (3, 3, 14.10);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (3, 3, 15.10);
insert into receipt_deliveries (receipt_id, city, country, local_address)
values (3, 'Kyiv', 'Ukraine', 'some street3');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 4, 1, 2, 78.9, 1, 'Typical again', '2022-02-10 17:34:31','DarkStriker', 'MasterStriker', '2022-02-10 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (4, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (4, 2, 50.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (4, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (4, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (10, 5, 1, 1, 2934.43, 2, 'Typical again', '2022-08-2 17:34:36','Unpleasant', 'Master2Striker', '2022-08-2 17:34:38');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (5, 1, 680.8);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (5, 2, 1877.4);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (5, 3, 376.23);
insert into receipt_deliveries (receipt_id, state, city, country, local_address, postal_code)
values (5, 'Tennessee', 'Knoxville', 'United States', '3688 Wiseman Street', '37929');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (11, 5, 1, 1, 1057.03, 2, 'Typical again', '2022-08-10 17:34:31','Unpleasant23', 'Master2Striker', '2022-08-10 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (6, 1, 680.8);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (6, 3, 376.23);
insert into receipt_deliveries (receipt_id, state, city, country, local_address, postal_code)
values (6, 'West Virginia', 'Ripley', 'United States', '302 Froe Street', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 60.5, 1, 'Не дзвоніть по вівторкам', '2022-02-15 17:34:31','DarkStriker', 'MasterStriker', '2022-02-15 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (7, 2, 50.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (7, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (7, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 28.5, 1, 'Typical again 2', '2022-02-22 17:34:31','DarkStriker', 'MasterStriker', '2022-02-22 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (8, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (8, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (8, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 28.5, 1, null, '2022-02-23 17:34:31','DarkStriker', 'MasterStriker', '2022-02-23 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (9, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (9, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (9, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 28.5, 1, null, '2022-03-24 17:34:31','DarkStriker', 'MasterStriker', '2022-02-24 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (10, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (10, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (10, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 28.5, 1, null, '2022-02-25 17:34:31','DarkStriker', 'MasterStriker', '2022-02-25 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (11, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (11, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (11, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

insert into receipts (user_id, receipt_status_id, category_id, master_id, total_price, currency_id, note, creation_date,created_by,
                      last_modified_by, last_modified_date)
values (7, 5, 1, 2, 28.5, 1, null, '2022-03-29 17:34:31','DarkStriker', 'MasterStriker', '2022-03-29 17:34:31');
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (12, 1, 18.40);
insert into receipt_items (receipt_id, repair_work_id, price_amount)
values (12, 3, 10.10);
insert into receipt_deliveries (receipt_id, city, country, local_address, postal_code)
values (12, 'Київ', 'Україна', 'вулиця Семена Скляренка, 17', '04073');

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (3, 'Not so good as i think but still normal',5);
INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (5, 'Я приємно здивована сервісом',6);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (5, 'Здивований сервісом',7);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (5, 'Damn so fast',8);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (4, 'Непогано',9);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (3, 'Непогано але швидше б',10);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (5, 'я і не вірив що зроблять',11);

INSERT INTO master_responses
    (rating, text, receipt_id)
VALUES (5, null,12);



