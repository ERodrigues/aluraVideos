package com.alura.videos.dto;

import com.alura.videos.model.Categoria;

import javax.validation.constraints.NotBlank;

public class CategoriaDto {
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String cor;

    public CategoriaDto(){}

    public CategoriaDto(long id, String titulo, String cor){
        this.id = id;
        this.titulo = titulo;
        this.cor = cor;
    }

    public long getId() {
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
