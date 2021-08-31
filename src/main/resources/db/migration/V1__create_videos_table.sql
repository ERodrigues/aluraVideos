
create table if not exists biblioteca (
    id bigserial primary key,
    titulo varchar(50),
    descricao varchar(100),
    url varchar(100)
);