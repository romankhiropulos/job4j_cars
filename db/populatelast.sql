DELETE
FROM advertisement;
DELETE
FROM j_user;
DELETE
FROM history_owner;
DELETE
FROM driver;
DELETE
FROM car;
DELETE
FROM model;
DELETE
FROM brand;
DELETE
FROM engine;
DELETE
FROM body_type;
DELETE
FROM transmission;
DELETE
FROM city;

INSERT INTO transmission (id, name)
VALUES (1, 'Автомат'),
       (2, 'Вариатор'),
       (3, 'Механика'),
       (4, 'Робот');

INSERT INTO body_type (id, name)
VALUES (1, 'Седан'),
       (2, 'Хетчбэк'),
       (3, 'Универсал'),
       (4, 'Кабриолет'),
       (5, 'Купе'),
       (6, 'Внедорожник'),
       (7, 'Фургон'),
       (8, 'Минивэн'),
       (9, 'Пикап'),
       (10, 'Микроавтобус');

INSERT INTO engine (id, type)
VALUES (1, 'Бензин'),
       (2, 'Газ'),
       (3, 'Электро'),
       (4, 'Дизель'),
       (5, 'Гибрид');

INSERT INTO city (id, name)
VALUES (1, 'Moscow'),
       (2, 'Leningrad'),
       (3, 'Kazan'),
       (4, 'Novgorod'),
       (5, 'Belgorod'),
       (6, 'Vladivostok'),
       (7, 'Irkutsk');

INSERT INTO brand (id, name)
VALUES (1, 'Mercedes-Benz'),
       (2, 'BMW'),
       (3, 'Audi'),
       (4, 'Porsche'),
       (5, 'Volkswagen'),
       (6, 'Dodge');

INSERT INTO model (id, name, brand_id)
VALUES (1, 'M3', 2),
       (2, 'Z4', 2),
       (3, 'X5', 2),
       (4, 'CLA', 1),
       (5, 'S', 1),
       (6, 'G', 1),
       (7, 'Q3', 3),
       (8, 'A5', 3),
       (9, 'RS4', 3),
       (10, 'Boxster', 4),
       (11, '911', 4),
       (12, 'Cayman', 4),
       (13, 'Passat', 5),
       (14, 'Jetta', 5),
       (15, 'Polo', 5),
       (16, 'Challenger', 6),
       (17, 'Charger', 6);

INSERT INTO car (id,
                 year,
                 mileage,
                 power,
                 size,
                 brand_id,
                 model_id,
                 engine_id,
                 body_type_id,
                 transmission_id)
VALUES (1, 2020, 6000, 350, 2.6, 1, 4, 1, 1, 1),
       (2, 2018, 33000, 250, 2.1, 2, 3, 1, 1, 1),
       (3, 2012, 553000, 160, 1.4, 5, 13, 1, 1, 1);

INSERT INTO j_user (id, login, password, name)
VALUES (1, 'roman@local', 'password', 'roman'),
       (2, 'nata@local', 'password', 'natalie');

INSERT INTO advertisement (id, created, user_id, car_id, city_id, price, description, sold)
VALUES (1, MAKE_TIMESTAMP(2021, 2, 5, 6, 25, 21.2), 1, 1, 1, 5000000, 'Не бита не крашена!', TRUE),
       (2, MAKE_TIMESTAMP(2020, 4, 7, 8, 15, 32.1), 2, 2, 7, 3500000, 'Ласточка!', FALSE),
       (3, MAKE_TIMESTAMP(2021, 10, 19, 13, 01, 44.1), 2, 3, 7, 2450000, 'Третья машина!', FALSE);


