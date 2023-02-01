DROP TABLE if EXISTS dictionary;
CREATE CACHED TABLE if not exists dictionary (
    id serial primary key,
    word text
);
