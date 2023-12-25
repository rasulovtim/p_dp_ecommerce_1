CREATE TABLE store_managers (
        store_id BIGINT,
        managers_id BIGINT,

        FOREIGN KEY (store_id) REFERENCES store(id) ON DELETE CASCADE,
        FOREIGN KEY (managers_id) REFERENCES users(id) ON DELETE CASCADE
);