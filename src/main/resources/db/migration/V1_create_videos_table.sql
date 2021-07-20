create schema if not exists videos;

create table videos.biblioteca (
    id bigserial primary key,
    titulo varchar(50),
    descricao varchar(100),
    url varchar(100)
);