DROP TABLE IF EXISTS master_responses;
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS password_reset_tokens;
DROP TABLE IF EXISTS verification_tokens;
DROP TABLE IF EXISTS user_has_role;
DROP TABLE IF EXISTS repair_work_prices;
DROP TABLE IF EXISTS repair_work_local_parts;
DROP TABLE IF EXISTS receipt_items;
DROP TABLE IF EXISTS repair_works;
DROP TABLE IF EXISTS repair_category_local_parts;
DROP TABLE IF EXISTS receipt_status_flows;
DROP TABLE IF EXISTS receipt_deliveries;
DROP TABLE IF EXISTS receipts;
DROP TABLE IF EXISTS repair_categories;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS receipt_statuses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS app_currencies;
DROP TABLE IF EXISTS app_locales;

CREATE TABLE `app_currencies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE `app_locales` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lang` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_modified_by` varchar(20) DEFAULT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `password` varchar(256) NOT NULL,
  `phone` varchar(32) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
);



CREATE TABLE `receipt_statuses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `repair_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `receipts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(20) NOT NULL,
  `creation_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(20) NOT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `total_price` decimal(19,4) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `master_id` bigint DEFAULT NULL,
  `currency_id` bigint NOT NULL,
  `receipt_status_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47y5f7yhg7q5iq1u5ljqxao8r` (`category_id`),
  KEY `FKoawolcjgyo3rq7kyml6r4xol4` (`master_id`),
  KEY `FKelxe89s3qmt767ir2256rk99` (`currency_id`),
  KEY `FKclaaxuw7wgqa87vke8rc4nl6e` (`receipt_status_id`),
  KEY `FK7t0uo7yxjck29e967rny84ky4` (`user_id`),
  CONSTRAINT `FK47y5f7yhg7q5iq1u5ljqxao8r` FOREIGN KEY (`category_id`) REFERENCES `repair_categories` (`id`),
  CONSTRAINT `FK7t0uo7yxjck29e967rny84ky4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKclaaxuw7wgqa87vke8rc4nl6e` FOREIGN KEY (`receipt_status_id`) REFERENCES `receipt_statuses` (`id`),
  CONSTRAINT `FKelxe89s3qmt767ir2256rk99` FOREIGN KEY (`currency_id`) REFERENCES `app_currencies` (`id`),
  CONSTRAINT `FKoawolcjgyo3rq7kyml6r4xol4` FOREIGN KEY (`master_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `receipt_deliveries` (
  `receipt_id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) NOT NULL,
  `local_address` varchar(255) NOT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`receipt_id`),
  CONSTRAINT `FK16jflsg10faykxrdflkovyvc7` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`)
);

CREATE TABLE `receipt_status_flows` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `from_status_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `to_status_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsohmkhba4pqjbyq9ya05kyipu` (`from_status_id`),
  KEY `FKn65y0fqo3s4ih2er9coaqhs5n` (`role_id`),
  KEY `FKp6cf20fj6opgf8elontxx624p` (`to_status_id`),
  CONSTRAINT `FKn65y0fqo3s4ih2er9coaqhs5n` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKp6cf20fj6opgf8elontxx624p` FOREIGN KEY (`to_status_id`) REFERENCES `receipt_statuses` (`id`),
  CONSTRAINT `FKsohmkhba4pqjbyq9ya05kyipu` FOREIGN KEY (`from_status_id`) REFERENCES `receipt_statuses` (`id`)
);


CREATE TABLE `repair_category_local_parts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `language_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsc0h7qgw552b8u611f9u7a280` (`category_id`),
  KEY `FKib74npmed7jll5tm94ad5naue` (`language_id`),
  CONSTRAINT `FKib74npmed7jll5tm94ad5naue` FOREIGN KEY (`language_id`) REFERENCES `app_locales` (`id`),
  CONSTRAINT `FKsc0h7qgw552b8u611f9u7a280` FOREIGN KEY (`category_id`) REFERENCES `repair_categories` (`id`)
);

CREATE TABLE `repair_works` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi9utlvm3hy2itkcksehnfmqya` (`category_id`),
  CONSTRAINT `FKi9utlvm3hy2itkcksehnfmqya` FOREIGN KEY (`category_id`) REFERENCES `repair_categories` (`id`)
);



CREATE TABLE `repair_work_local_parts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `language_id` bigint NOT NULL,
  `work_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg67md3u7fr6av6rxukhibg6nw` (`language_id`),
  KEY `FKjg9503bhw7vncx59y01lhnpap` (`work_id`),
  CONSTRAINT `FKg67md3u7fr6av6rxukhibg6nw` FOREIGN KEY (`language_id`) REFERENCES `app_locales` (`id`),
  CONSTRAINT `FKjg9503bhw7vncx59y01lhnpap` FOREIGN KEY (`work_id`) REFERENCES `repair_works` (`id`)
);


CREATE TABLE `repair_work_prices` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lower_border` decimal(19,4) DEFAULT NULL,
  `upper_border` decimal(19,4) DEFAULT NULL,
  `currency_id` bigint NOT NULL,
  `work_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11r3w395lu710rgbgphnm7366` (`currency_id`),
  KEY `FKqd656ogcbu7noh78f1o7atqag` (`work_id`),
  CONSTRAINT `FK11r3w395lu710rgbgphnm7366` FOREIGN KEY (`currency_id`) REFERENCES `app_currencies` (`id`),
  CONSTRAINT `FKqd656ogcbu7noh78f1o7atqag` FOREIGN KEY (`work_id`) REFERENCES `repair_works` (`id`)
);

CREATE TABLE `receipt_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price_amount` decimal(19,4) DEFAULT NULL,
  `receipt_id` bigint DEFAULT NULL,
  `repair_work_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmyfhuc1y0sjqe2geey8jx9nc2` (`receipt_id`),
  KEY `FK2tef3gecgapi4o66lupnkmh72` (`repair_work_id`),
  CONSTRAINT `FK2tef3gecgapi4o66lupnkmh72` FOREIGN KEY (`repair_work_id`) REFERENCES `repair_works` (`id`),
  CONSTRAINT `FKmyfhuc1y0sjqe2geey8jx9nc2` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`)
);



CREATE TABLE `user_has_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKsvvq61v3koh04fycopbjx72hj` (`role_id`),
  CONSTRAINT `FK2dl1ftxlkldulcp934i3125qo` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsvvq61v3koh04fycopbjx72hj` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);



CREATE TABLE `master_responses` (
  `rating` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `receipt_id` bigint NOT NULL,
  PRIMARY KEY (`receipt_id`),
  CONSTRAINT `FKq8yhqrk4qm5kd1ux8acrid0dr` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`id`)
);


CREATE TABLE `password_reset_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk3ndxg5xp6v7wd4gjyusp15gq` (`user_id`),
  CONSTRAINT `FKk3ndxg5xp6v7wd4gjyusp15gq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);


CREATE TABLE `verification_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54y8mqsnq1rtyf581sfmrbp4f` (`user_id`),
  CONSTRAINT `FK54y8mqsnq1rtyf581sfmrbp4f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);



CREATE TABLE `wallets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `money_amount` decimal(19,4) NOT NULL,
  `name` varchar(20) NOT NULL,
  `currency_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq08vlhx40d0ftsfqlgxw0xoqe` (`currency_id`),
  KEY `FKc1foyisidw7wqqrkamafuwn4e` (`user_id`),
  CONSTRAINT `FKc1foyisidw7wqqrkamafuwn4e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKq08vlhx40d0ftsfqlgxw0xoqe` FOREIGN KEY (`currency_id`) REFERENCES `app_currencies` (`id`)
);
