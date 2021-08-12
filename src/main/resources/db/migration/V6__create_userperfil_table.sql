create table videos.usuarios_perfis (
    id bigserial primary key,
    id_usuario bigint not null,
    id_perfil bigint not null
);

alter table videos.usuarios_perfis
    add constraint fk_usuarios_perfis_perfil
    foreign key (id_perfil)
    references videos.perfis(id);

alter table videos.usuarios_perfis
    add constraint fk_usuarios_perfis_usuario
    foreign key (id_usuario)
    references videos.usuario(id);
