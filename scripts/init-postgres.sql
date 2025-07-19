DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    side        VARCHAR(1)               NOT NULL,
    custodycd   VARCHAR(20)              NOT NULL,
    acctno      VARCHAR(20)              NOT NULL,
    exchange    VARCHAR(10)              NOT NULL,
    board       VARCHAR(10)              NOT NULL,
    symbol      VARCHAR(10)              NOT NULL,
    quote_price NUMERIC                  NOT NULL, -- BigDecimal equivalent
    quote_qtty  BIGINT                   NOT NULL, -- 8-byte integer
    exec_amt    NUMERIC                  NOT NULL DEFAULT 0,
    exec_qtty   BIGINT                   NOT NULL DEFAULT 0,
    remain_qtty BIGINT                   NOT NULL,
    cancel_qtty BIGINT                   NOT NULL DEFAULT 0,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  DATE
);

CREATE INDEX idx_acctno ON orders (acctno);