CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS cryptocurrency (
    uuid UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    symbol VARCHAR(10) NOT NULL,
    name VARCHAR(60) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    total_supply DECIMAL NOT NULL CHECK (total_supply >= 1),
    current_price DECIMAL NOT NULL CHECK (current_price >= 0),
    logo VARCHAR(255) NOT NULL
);