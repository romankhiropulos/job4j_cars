DELETE FROM model;
DELETE FROM brand;
DELETE FROM engine;
DELETE FROM body_type;
DELETE FROM transmission;

INSERT INTO transmission (name)
VALUES ('Автомат'),
       ('Вариатор'),
       ('Механика'),
       ('Робот');

INSERT INTO body_type (name)
VALUES ('Седан'),
       ('Хетчбэк'),
       ('Универсал'),
       ('Кабриолет'),
       ('Купе'),
       ('Внедорожник'),
       ('Фургон'),
       ('Минивэн'),
       ('Пикап'),
       ('Микроавтобус');

INSERT INTO engine (type)
VALUES ('Бензин'),
       ('Газ'),
       ('Электро'),
       ('Дизель'),
       ('Гибрид');

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


