DELETE FROM model;
DELETE FROM brand;
DELETE FROM engine;
DELETE FROM body_type;
DELETE FROM transmission;

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

INSERT INTO driver (id, name)
VALUES (1, 'M3'),
       (2, 'Z4'),
       (3, 'X5');

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
VALUES (1, 2020, 6000, 350, 2.6, 1, 2, 1, 1, 1),
       (2, 2018, 33000, 250, 2.1, 2, 3, 1, 1, 1),
       (3, 2015, 233000, 150, 1.8, 3, 7, 1, 1, 1);




