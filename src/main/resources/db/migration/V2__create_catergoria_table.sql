create table if not exists categoria (
    id bigserial primary key,
    titulo varchar(100) not null,
    cor varchar(50) not null
);