DELETE
FROM books_users;

DELETE
FROM library_cards;

DELETE
FROM tbl_author;

ALTER TABLE tbl_author ALTER COLUMN id RESTART WITH 1;

DELETE
FROM tbl_reader;

ALTER TABLE tbl_reader ALTER COLUMN id RESTART WITH 1;

