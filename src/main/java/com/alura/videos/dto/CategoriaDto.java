package com.alura.videos.dto;

import com.alura.videos.model.Categoria;

public class CategoriaDto {
    private long id;
    private String titulo;
    private String cor;

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public static CategoriaDto convert(Categoria categoria){
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setTitulo(categoria.getTitulo());
        categoriaDto.setCor(categoria.getCor());

        return categoriaDto;
    }
}
