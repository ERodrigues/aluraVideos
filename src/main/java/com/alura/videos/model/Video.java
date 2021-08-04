package com.alura.videos.model;

import com.alura.videos.dto.VideoDto;

import javax.persistence.*;

@Entity(name="biblioteca")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String url;

    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public static Video convert(VideoDto videoDto) {
        Video video = new Video();
        video.setId(videoDto.getId());
        video.setDescricao(videoDto.getDescricao());
        video.setTitulo(videoDto.getTitulo());
        video.setUrl(videoDto.getUrl());

        if (videoDto.getCategoria() != null){
            video.setCategoria(Categoria.convert(videoDto.getCategoria()));
        }
        return video;
    }
}
