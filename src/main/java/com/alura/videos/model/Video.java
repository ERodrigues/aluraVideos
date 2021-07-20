package com.alura.videos.model;

import com.alura.videos.dto.VideoDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="biblioteca")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String descricao;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public static Video convert(VideoDto videoDto) {
        Video video = new Video();
        video.setId(videoDto.getId());
        video.setDescricao(videoDto.getDescricao());
        video.setTitulo(videoDto.getTitulo());
        video.setUrl(videoDto.getUrl());
        return video;
    }
}
