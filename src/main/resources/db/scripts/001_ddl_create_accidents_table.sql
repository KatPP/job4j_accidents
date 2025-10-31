-- Типы инцидентов
CREATE TABLE accident_types
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

-- Статьи нарушений
CREATE TABLE rules
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

-- Инциденты
CREATE TABLE accidents
(
    id      SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    text    TEXT,
    address TEXT,
    type_id INT REFERENCES accident_types (id)
);

-- Связь: инцидент ↔ статьи (многие-ко-многим)
CREATE TABLE accident_rule
(
    accident_id INT REFERENCES accidents (id) ON DELETE CASCADE,
    rule_id     INT REFERENCES rules (id) ON DELETE CASCADE,
    PRIMARY KEY (accident_id, rule_id)
);