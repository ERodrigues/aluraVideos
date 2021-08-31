package com.alura.videos.model;

import com.alura.videos.dto.CategoriaDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String cor;
    @OneToMany(mappedBy = "categoria")
    private List<Video> videos;

    public Categoria(){

    }

    public Categoria(String titulo, String cor) {
        this.titulo = titulo;
        this.cor = cor;
    }

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public static Categoria convert(CategoriaDto categoriaDto){
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDto.getId());
        categoria.setTitulo(categoriaDto.getTitulo());
        categoria.setCor(categoriaDto.getCor());

        return categoria;
    }
}
