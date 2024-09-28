INSERT INTO customer (id, username, password, name, birth_place, birth_date, mothers_name, id_card_numb, tax_numb, email, status)
VALUES
  (1, 'nagys', 'ns-secret', 'Nagy Sándor', 'Nyíregyháza', '1985-05-15', 'Nagy Mária', 'HU1234567890', 23456789, 'nagysandor@example.com', 'ACTIVE'),
  (2, 'kovacsa', 'ka-secret', 'Kovács Anna', 'Miskolc', '1990-12-20', 'Kovács Éva', 'HU9876543210', 45678901, 'kovacsanna@example.com', 'BLOCKED');
ALTER TABLE customer ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM customer) + 1;


INSERT INTO card (id, customer_name, expiry_date, pin_code, status, type, customer_id)
VALUES
 (10001, 'Nagy Sándor', '2023-02-15', 1234, 'ACTIVE', 'DEBIT', 1),
 (10002, 'Nagy Sándor', '2024-04-01', 5678, 'ACTIVE', 'CREDIT', 1),
 (10003, 'Kovács Anna', '2023-11-10', 9012, 'BLOCKED', 'DEBIT', 2);
ALTER TABLE card ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM card) + 1;

INSERT INTO ACCOUNT (id, balance, currency, opening_date, status, card_id, customer_id)
VALUES
  (1001, 5000.00, 'EUR', '2023-01-01', 'ACTIVE', 10002, 1),
  (1002, 1000.00, 'USD', '2024-03-15', 'ACTIVE', 10001, 1),
  (1003, 2000.00, 'HUF', '2022-10-05', 'TERMINATED', NULL, 2),
  (1004, 0.00, 'EUR', '2024-07-20', 'TERMINATED', 10003, 2);
ALTER TABLE account ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM account) + 1;


INSERT INTO transactions (id, amount, currency, creation_date, booking_date, partner_name, partner_account_numb, description, status, customer_id, account_id)
VALUES
  (10004, 100.00, 'EUR', '2023-02-15', '2023-02-20', 'Grocery Store', 1234567890, 'Groceries', 'BOOKED', 1, 1001),
  (10005, 50.00, 'USD', '2024-04-01', '2024-04-05', 'Restaurant', 9876543210, 'Dinner', 'PROCESSED', 1, 1002),
  (10006, 200.00, 'HUF', '2023-11-10', '2023-11-15', 'Salary', '0000000000', 'Income', 'FAILED', 2, 1004);
ALTER TABLE transactions ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM transactions) + 1;
