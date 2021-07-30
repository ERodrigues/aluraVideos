alter table videos.biblioteca add id_categoria bigint;

alter table videos.biblioteca
    add constraint fk_video_categoria
    foreign key (id_categoria)
    references videos.categoria(id);