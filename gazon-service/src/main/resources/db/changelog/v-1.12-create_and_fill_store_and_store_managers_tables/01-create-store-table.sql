CREATE TABLE store (
   id                BIGSERIAL PRIMARY KEY,
   products          VARCHAR(255),
   managers          VARCHAR(255),
   owner_id          BIGINT NOT NULL,
   FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);