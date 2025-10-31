-- Типы
INSERT INTO accident_types (id, name)
VALUES (1, 'Две машины'),
       (2, 'Машина и человек'),
       (3, 'Машина и велосипед');

-- Статьи
INSERT INTO rules (id, name)
VALUES (1, 'Статья. 1'),
       (2, 'Статья. 2'),
       (3, 'Статья. 3');

-- Инциденты
INSERT INTO accidents (id, name, text, address, type_id)
VALUES (1, 'ДТП на перекрёстке', 'Столкновение', 'ул. Ленина, 10', 1),
       (2, 'Наезд на пешехода', 'Пешеход в больнице', 'пр. Мира, 25', 2);

-- Связи
INSERT INTO accident_rule (accident_id, rule_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

SELECT setval('accidents_id_seq', (SELECT MAX(id) FROM accidents));
SELECT setval('accident_types_id_seq', (SELECT MAX(id) FROM accident_types));
SELECT setval('rules_id_seq', (SELECT MAX(id) FROM rules));