INSERT INTO exchange_rates (base_currency, target_currency, rate)
VALUES
  ('HUF', 'EUR', 0.0025),
  ('HUF', 'USD', 0.0027),
  ('HUF', 'GBP', 0.0021),
  ('HUF', 'CHF', 0.0023),
  ('HUF', 'AUD', 0.0040),
  ('HUF', 'JPY', 0.41),
  ('EUR', 'USD', 1.10),
  ('EUR', 'GBP', 0.84),
  ('EUR', 'CHF', 0.94),
  ('EUR', 'AUD', 1.61),
  ('EUR', 'JPY', 163.25),
  ('EUR', 'HUF', 401.48),
  ('USD', 'EUR', 0.91),
  ('USD', 'GBP', 0.76),
  ('USD', 'CHF', 0.86),
  ('USD', 'AUD', 1.47),
  ('USD', 'JPY', 148.72),
  ('USD', 'HUF', 365.80),
  ('GBP', 'EUR', 1.19),
  ('GBP', 'USD', 1.31),
  ('GBP', 'CHF', 1.13),
  ('GBP', 'AUD', 1.93),
  ('GBP', 'JPY', 195.19),
  ('GBP', 'HUF', 479.98),
  ('CHF', 'EUR', 1.06),
  ('CHF', 'USD', 1.17),
  ('CHF', 'GBP', 0.89),
  ('CHF', 'AUD', 1.72),
  ('CHF', 'JPY', 173.44),
  ('CHF', 'HUF', 426.58),
  ('AUD', 'EUR', 0.62),
  ('AUD', 'USD', 0.68),
  ('AUD', 'GBP', 0.52),
  ('AUD', 'CHF', 0.59),
  ('AUD', 'JPY', 101.49),
  ('AUD', 'HUF', 248.50),
  ('JPY', 'EUR', 0.0061),
  ('JPY', 'USD', 0.0067),
  ('JPY', 'GBP', 0.0051),
  ('JPY', 'CHF', 0.0058),
  ('JPY', 'AUD', 0.0099),
  ('JPY', 'HUF', 2.46)
   --data is from 2024.10.05. 10:57
;
ALTER TABLE exchange_rates ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM exchange_rates) + 1;

INSERT INTO partner_banks (id, name, swift_code, transfer_fee_ineur)
VALUES
    (1,'Partner Bank A', 'SWIFT1234', 10.0),
    (2, 'Partner Bank B', 'SWIFT5678', 8.5),
    (3, 'Partner Bank C', 'SWIFT9012', 12.0);
ALTER TABLE partner_banks ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM partner_banks) + 1;

INSERT INTO IBAN (iban, country)
VALUES
    ('HU123456789012345678901234', 'Hungary'),
    ('HU123456789012345678901235', 'Hungary'),
    ('HU123456789012345678901535', 'Hungary'),
    ('HU123456789012345678901236', 'Hungary'),
    ('HU123456789012345678901237', 'Hungary');

INSERT INTO customer (id, username, password, name, birth_place, birth_date, mothers_name, id_card_numb, tax_numb, email, status, points)
VALUES
  (1, 'nagys', 'ns-secret', 'Nagy Sándor', 'Nyíregyháza', '1985-05-15', 'Nagy Mária', 'HU1234567890', 23456789, 'nagysandor@example.com', 'ACTIVE', 50),
  (2, 'kovacsa', 'ka-secret', 'Kovács Anna', 'Miskolc', '1990-12-20', 'Kovács Éva', 'HU9876543210', 45678901, 'kovacsanna@example.com', 'BLOCKED', 0);
ALTER TABLE customer ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM customer) + 1;


INSERT INTO card (id, customer_name, expiry_date, pin_code, status, type, customer_id)
VALUES
 (10001, 'Nagy Sándor', '2023-02-15', 1234, 'ACTIVE', 'DEBIT',1),
 (10002, 'Nagy Sándor', '2024-04-01', 5678, 'ACTIVE', 'CREDIT',1),
 (10003, 'Kovács Anna', '2023-11-10', 9012, 'BLOCKED', 'DEBIT', 2);
ALTER TABLE card ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM card) + 1;

INSERT INTO account (id, balance, currency, opening_date, status, type, card_id, iban_iban, customer_id)
VALUES
  (1001, 5000.00, 'EUR', '2023-01-01', 'ACTIVE', 'SAVING', 10002, 'HU123456789012345678901234',1),
  (1002, 1000.00, 'USD', '2024-03-15', 'ACTIVE', 'LOAN',10001, 'HU123456789012345678901235',1),
  (1005, 1000.00, 'USD', '2024-03-15', 'ACTIVE', 'CURRENT',10001, 'HU123456789012345678901535',1),
  (1003, 2000.00, 'HUF', '2022-10-05', 'TERMINATED','CURRENT', NULL, 'HU123456789012345678901236',2),
  (1004, 0.00, 'EUR', '2024-07-20', 'TERMINATED','LOAN', 10003, 'HU123456789012345678901237',2);
ALTER TABLE account ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM account) + 1;


INSERT INTO transactions (id, amount, currency, creation_date, booking_date, partner_name, partner_account_numb, description, status, customer_id, account_id)
VALUES
  (10004, 100.00, 'EUR', '2023-02-15', '2023-02-20', 'Grocery Store', 1234567890, 'Groceries', 'BOOKED', 1, 1001),
  (10005, 50.00, 'USD', '2024-04-01', '2024-04-05', 'Restaurant', 9876543210, 'Dinner', 'PROCESSED', 1, 1002),
  (10006, 200.00, 'HUF', '2023-11-10', NULL, 'Salary', '0000000000', 'Income', 'FAILED', 2, 1004);
ALTER TABLE transactions ALTER COLUMN id RESTART WITH (SELECT MAX(ID) FROM transactions) + 1;
