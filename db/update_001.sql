CREATE TABLE orders
(
    id          SERIAL,
    name        VARCHAR(50),
    description VARCHAR(50),
    created     TIMESTAMP,
    PRIMARY KEY (id)
);