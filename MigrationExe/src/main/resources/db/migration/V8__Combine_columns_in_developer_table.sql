ALTER TABLE developers
    ADD COLUMN full_name VARCHAR(100);

UPDATE developers
SET full_name = CONCAT(first_name, ' ', last_name);

ALTER TABLE developers
    MODIFY COLUMN full_name VARCHAR(100) NOT NULL;

ALTER TABLE developers DROP COLUMN first_name;
ALTER TABLE developers DROP COLUMN last_name;