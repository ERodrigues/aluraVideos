package com.alura.videos.model;

import com.alura.videos.dto.CategoriaDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String cor;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    public static Categoria convert(CategoriaDto categoriaDto){
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDto.getId());
        categoria.setTitulo(categoriaDto.getTitulo());
        categoria.setCor(categoriaDto.getCor());

        return categoria;
    }
}