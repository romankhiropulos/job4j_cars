DROP TABLE IF EXISTS history_owner;
DROP TABLE IF EXISTS engine;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS car;

CREATE TABLE driver
(
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE car
(
    id        SERIAL PRIMARY KEY,
    engine_id INT NOT NULL UNIQUE REFERENCES engine (id)
);

CREATE TABLE history_owner
(
    id        SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver (id),
    car_id    INT NOT NULL REFERENCES car (id)
);

CREATE TABLE engine
(
    id SERIAL PRIMARY KEY,
    name VARCHAR
);