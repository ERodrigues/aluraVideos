create table if not exists usuario (
    id bigserial primary key,
    nome varchar(100) not null,
    email varchar(50) not null,
    senha varchar(50) not null
);