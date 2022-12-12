--Insert Reader
--Password: password
INSERT INTO tbl_reader (id, name, surname ,email, password, birth_date, balance)
VALUES (2L, 'Giovanni','Bianchi', 'g.bianchi@gmail.com', '$2a$10$T1YkmQfIfNXHrJTdsj7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG', '1980-10-18', 180);
--Insert Reader
--Password: password
INSERT INTO tbl_reader (id,name, surname ,email, password, birth_date, balance)
VALUES (1L, 'Mattia','Iannone', 'm.iannone@gmail.com', '$2a$10$ZR.w.y9LVfFEUHpRU6sVkOYJcmHXncfEIlBU5uAEa6Vwr/XHLfcV6', '1998-05-29', 180);
--Insert Reader
--Password: password
INSERT INTO tbl_reader (id,name, surname ,email, password, birth_date, balance)
VALUES (3L, 'Marco','Di Donato','m.didonato@gmail.com', '$2a$10$ZR.s.y9LVfFEUHpRU6sVkOYJcmHXncfEIlBU5uAEa6Vwr/XHLfcV6', '1998-05-29', 180);



--Insert Author
--Password: password
INSERT INTO tbl_author (id, name, surname ,email, password, birth_date, bio, category, alias, subscription_price)
VALUES (10L, 'Michele', 'Vitantonio', 'm.vitantonio@gmail.com', '$2a$10$T1YkmQfIfsXHrJTds7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG', '1980-10-18', 'Bio di Michele', 'Sport', 'Michelino', 20);
--Insert Author
--Password: password
INSERT INTO tbl_author (id, name, surname ,email, password, birth_date, bio, category, alias, subscription_price)
VALUES (11L, 'Davide', 'Russo', 'd.russo@gmail.com', '$2a$10$T1YkmQfIfsXHrJTds7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG', '1980-10-18', 'Bio di Davide', 'Sport', 'davide', 10);

--Insert Author
--Password: password
INSERT INTO tbl_author (id, name, surname ,email, password, birth_date, bio, category, alias, subscription_price)
VALUES (12L, 'Leonardo', 'Rossi', 'l.rossi@gmail.com', '$2a$10$T1YkmQfIfsXHrJTds7UYO1QAdvFvWb6ryvEhJ.X50n0MZfLIfvOG', '1980-10-18', 'Bio di Leonardo', 'Sport', 'leo', 40);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title1', 'category1', 'description1', '2022-12-06 15:59:07.209000',10);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title1.1', 'category1.1', 'description1.1', '2022-12-06 15:59:07.209000',10);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title1.2', 'category1.2', 'description1.1', '2022-12-06 15:59:07.209000',10);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title2', 'category2', 'description2', '2022-12-06 15:59:07.209000',11);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title2.2', 'category2', 'description2.2', '2022-12-06 15:59:07.209000',11);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title3', 'category3', 'description3', '2022-12-06 15:59:07.209000',12);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title2.1', 'category2.1', 'description2.1', '2022-12-06 15:59:07.209000',11);

--Insert News
--Password: password
INSERT INTO tbl_news (title, category, description, date, author_id)
VALUES ( 'title3.1', 'category3.1', 'description3.1', '2022-12-06 15:59:07.209000',12);


--Insert Subscription
--Password: password
INSERT INTO tbl_subscription (reader_id, author_id, start_date, end_date, state_subscription)
VALUES (2,11,'2022-12-07 09:54:07.644000','2023-01-06 09:54:07.635000',true);

--Insert Subscription
--Password: password
INSERT INTO tbl_subscription (reader_id, author_id, start_date, end_date, state_subscription)
VALUES (2,12,'2022-12-07 09:54:07.644000','2023-01-06 09:54:07.635000',true);

--Insert Subscription
--Password: password
INSERT INTO tbl_subscription (reader_id, author_id, start_date, end_date, state_subscription)
VALUES (1,10,'2022-12-07 09:54:07.644000','2023-01-06 09:54:07.635000',true);

--Insert Subscription
--Password: password
INSERT INTO tbl_subscription (reader_id, author_id, start_date, end_date, state_subscription)
VALUES (2,10,'2022-12-07 09:54:07.644000','2023-01-06 09:54:07.635000',true);

--Insert Subscription
--Password: password
INSERT INTO tbl_subscription (reader_id, author_id, start_date, end_date, state_subscription)
VALUES (3,10,'2022-12-07 09:54:07.644000','2023-01-06 09:54:07.635000',true);


