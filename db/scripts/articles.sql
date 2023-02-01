DROP TABLE if EXISTS articles;
CREATE CACHED TABLE if not exists articles (
    id serial primary key,
    text text
);
