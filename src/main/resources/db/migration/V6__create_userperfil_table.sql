create table videos.usuario_perfis (
    id bigserial primary key,
    usuario_id bigint not null,
    perfis_id bigint not null
);

alter table videos.usuario_perfis
    add constraint fk_usuarios_perfis_perfil
    foreign key (perfis_id)
    references videos.perfis(id);

alter table videos.usuario_perfis
    add constraint fk_usuarios_perfis_usuario
    foreign key (usuario_id)
    references videos.usuario(id);