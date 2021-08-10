DROP TABLE IF EXISTS advertisement;
DROP TABLE IF EXISTS j_user;
DROP TABLE IF EXISTS history_owner;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS model;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS engine;
DROP TABLE IF EXISTS body_type;
DROP TABLE IF EXISTS transmission;

CREATE TABLE j_user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(2000) NOT NULL,
    password VARCHAR(2000) NOT NULL,
    name     VARCHAR(2000)
);

CREATE TABLE brand
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE model
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR NOT NULL,
    brand_id INT     NOT NULL REFERENCES brand (id)
);

CREATE TABLE engine
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR NOT NULL,
    power INT     NOT NULL,
    size  INT     NOT NULL
);

CREATE TABLE body_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE transmission
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE car
(
    id              SERIAL PRIMARY KEY,
    year            INT NOT NULL,
    mileage         INT NOT NULL,
    brand_id        INT NOT NULL UNIQUE REFERENCES brand (id),
    model_id        INT NOT NULL UNIQUE REFERENCES model (id),
    engine_id       INT NOT NULL UNIQUE REFERENCES engine (id),
    body_type_id    INT NOT NULL UNIQUE REFERENCES body_type (id),
    transmission_id INT NOT NULL UNIQUE REFERENCES transmission (id)
);

CREATE TABLE driver
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE history_owner
(
    id        SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver (id),
    car_id    INT NOT NULL REFERENCES car (id)
);

CREATE TABLE advertisement
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    user_id INT       NOT NULL REFERENCES j_user (id),
    car_id  INT       NOT NULL REFERENCES car (id),
    sold    BOOLEAN   NOT NULL DEFAULT FALSE
);



