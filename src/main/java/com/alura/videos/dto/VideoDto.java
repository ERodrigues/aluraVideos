package com.alura.videos.dto;

import com.alura.videos.model.Video;

import javax.validation.constraints.NotBlank;

public class VideoDto {
    private long id;
    @NotBlank (message = "{videoDto.titulo.not.blank}")
    private String titulo;
    @NotBlank (message = "{videoDto.descricao.not.blank}")
    private String descricao;
    @NotBlank (message = "{videoDto.utl.not.blank}")
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

    public static VideoDto convert(Video video) {
        VideoDto videoDto = new VideoDto();
        videoDto.setId(video.getId());
        videoDto.setDescricao(video.getDescricao());
        videoDto.setTitulo(video.getTitulo());
        videoDto.setUrl(video.getUrl());
        return videoDto;
    }
}
