DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    side        VARCHAR(1)               NOT NULL, -- B or S
    custodycd   VARCHAR(20)              NOT NULL, -- 001C??????
    acctno      VARCHAR(20)              NOT NULL, -- 0001??????
    exchange    VARCHAR(10)              NOT NULL, -- HSX or HNX
    board       VARCHAR(10)              NOT NULL, -- HSX or HNX or UPCOM
    symbol      VARCHAR(10)              NOT NULL, -- VCB, BID, CTG, VIC, MSN, ...
    quote_price NUMERIC                  NOT NULL, -- BigDecimal equivalent
    quote_qtty  BIGINT                   NOT NULL, -- 8-byte integer
    exec_amt    NUMERIC                  NOT NULL DEFAULT 0,
    exec_qtty   BIGINT                   NOT NULL DEFAULT 0,
    remain_qtty BIGINT                   NOT NULL, -- default is quote_qtty
    cancel_qtty BIGINT                   NOT NULL DEFAULT 0,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  DATE
);

CREATE INDEX idx_acctno ON orders (acctno);
CREATE INDEX idx_symbol ON orders (acctno);


-- Step 1: Static mapping of symbols to exchanges/boards
CREATE TEMP TABLE symbol_map
(
    symbol   TEXT,
    exchange TEXT,
    board    TEXT
);
INSERT INTO symbol_map(symbol, exchange, board)
VALUES
-- HSX
('VCB', 'HSX', 'HSX'), ('BID', 'HSX', 'HSX'), ('CTG', 'HSX', 'HSX'),
('VNM', 'HSX', 'HSX'), ('FPT', 'HSX', 'HSX'), ('VIC', 'HSX', 'HSX'),
('SHB', 'HSX', 'HSX'), ('ACB', 'HSX', 'HSX'), ('MSB', 'HSX', 'HSX'),
('VIB', 'HSX', 'HSX'),
-- HNX
('SHN', 'HNX', 'HNX'), ('HUT', 'HNX', 'HNX'), ('PVS', 'HNX', 'HNX'),
-- UPCOM
('BSP', 'HNX', 'UPCOM'), ('QNS', 'HNX', 'UPCOM'), ('LTG', 'HNX', 'UPCOM');

CREATE TEMP TABLE random_symbols AS
SELECT (ARRAY [
    'VCB', 'BID', 'CTG', 'VNM', 'FPT', 'VIC', 'SHB', 'ACB', 'MSB', 'SHN', 'HUT', 'PVS', 'BSP', 'QNS', 'LTG'
    ])[FLOOR(RANDOM() * 15 + 1)] AS symbol
FROM generate_series(1, 10000);

-- Step 2: Insert 10k rows, joining with symbol_map for valid exchange/board
INSERT INTO orders (side, custodycd, acctno, exchange, board, symbol, quote_price, quote_qtty, remain_qtty)
SELECT CASE WHEN RANDOM() < 0.5 THEN 'B' ELSE 'S' END,
       '001C' || LPAD((trunc(random() * 1000000)::int)::text, 6, '0'),
       '0001' || LPAD((trunc(random() * 1000000)::int)::text, 6, '0'),
       sm.exchange,
       sm.board,
       sm.symbol,
       ROUND((RANDOM() * 100000)::numeric, 2),
       (RANDOM() * 1000)::bigint + 1,
       (RANDOM() * 1000)::bigint + 1
FROM random_symbols rs JOIN symbol_map sm ON rs.symbol = sm.symbol;