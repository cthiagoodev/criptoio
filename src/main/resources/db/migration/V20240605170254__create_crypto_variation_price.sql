CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS crypto_variation_price (
    uuid UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    price DECIMAL NOT NULL CHECK (price >= 0),
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cryptocurrency_id VARCHAR(255),
    FOREIGN KEY (cryptocurrency_id) REFERENCES cryptocurrency(id)
);