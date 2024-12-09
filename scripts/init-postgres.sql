CREATE TABLE orders (
    id VARCHAR(255) PRIMARY KEY,
    side VARCHAR(1),
    custodycd VARCHAR(20),
    acctno VARCHAR(20),
    exchange VARCHAR(10),
    board VARCHAR(10),
    symbol VARCHAR(10),
    quote_price INTEGER,
    quote_qtty INTEGER,
    exec_amt NUMERIC -- BigDecimal equivalent
);

CREATE INDEX idx_acctno ON orders (acctno);