ALTER TABLE users
    ADD COLUMN passport_id BIGINT REFERENCES passport (id);


