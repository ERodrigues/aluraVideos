alter table biblioteca add id_categoria bigint;

alter table biblioteca
    add constraint fk_video_categoria
    foreign key (id_categoria)
    references categoria(id);