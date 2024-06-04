ALTER TABLE cryptocurrency
ADD CONSTRAINT unique_symbol UNIQUE (symbol);